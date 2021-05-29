package cn.milai.nexus.handler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.stereotype.Component;

import cn.milai.nexus.annotation.ExceptionHandler;
import cn.milai.nexus.annotation.MsgController;
import cn.milai.nexus.annotation.MsgControllerAdvice;
import cn.milai.nexus.annotation.MsgMapping;
import cn.milai.nexus.handler.interceptor.Interceptor;
import cn.milai.nexus.handler.msg.Msg;
import cn.milai.nexus.handler.paramresolve.ExceptionParamResolver;
import cn.milai.nexus.handler.paramresolve.ParamResolver;
import cn.milai.nexus.handler.paramresolve.ParamResolverComposite;
import io.netty.channel.ChannelHandlerContext;

/**
 * 消息分发器
 * @author milai
 * @date 2020.12.29
 */
@Component
public class MsgDispatcher implements ApplicationContextAware {

	private static final Logger LOG = LoggerFactory.getLogger(MsgDispatcher.class);

	private List<Interceptor> interceptors;

	private Collection<ParamResolver> paramResolvers;

	/**
	 * 消息 code 及对应的处理方法的映射
	 */
	private Map<Integer, MethodHandler> handlerMapping = new HashMap<>();

	/**
	 * {@link MsgController} 内定义的 {@link ExceptionHandlerTable}
	 */
	private Map<Object, ExceptionHandlerTable> exceptionHandlerTable = new HashMap<>();

	private ExceptionHandlerTable globalExceptionHandlerTable = new ExceptionHandlerTable();

	/**
	 * 分发消息到对应的处理器处理
	 * @param msg
	 */
	public void dispatch(ChannelHandlerContext ctx, Msg msg) throws Exception {
		LOG.info("接收到消息, msg = {}", msg);
		MethodHandler handler = handlerMapping.get(msg.getCode());
		if (handler == null) {
			LOG.warn("没有找到对应的处理器: code = {}, handlers = {}", msg.getCode(), handlerMapping.keySet());
			return;
		}
		for (Interceptor interceptor : interceptors) {
			if (!interceptor.preHandle(ctx, msg, handler)) {
				LOG.debug("消息被拦截: id = {}, interceptor = {}", msg.getId(), interceptor.getClass().getName());
				return;
			}
		}
		ParamResolverComposite paramResolver = new ParamResolverComposite(paramResolvers);
		Object[] params = paramResolver.resolveParams(handler.getHandleMethod(), ctx, msg);
		try {
			handler.invoke(params);
		} catch (InvocationTargetException ex) {
			Exception e = (Exception) ex.getTargetException();
			LOG.error("处理消息时发生异常: msg = {}, e = {}", msg, ExceptionUtils.getStackTrace(e));
			MethodHandler exceptionHandler = findExceptionHandler(handler, e);
			if (exceptionHandler == null) {
				LOG.info("未找到对应的异常处理器: e = {}", e.getClass().getName());
				throw e;
			}
			paramResolver.addParamResolver(new ExceptionParamResolver(e));
			exceptionHandler.invoke(paramResolver.resolveParams(exceptionHandler.getHandleMethod(), ctx, msg));
		}
	}

	private MethodHandler findExceptionHandler(MethodHandler handler, Exception e) {
		return exceptionHandlerTable.getOrDefault(handler.getHandler(), globalExceptionHandlerTable).findHandler(e);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws HandlerRedeclareException {
		interceptors = new ArrayList<>(applicationContext.getBeansOfType(Interceptor.class).values());
		AnnotationAwareOrderComparator.sort(interceptors);
		paramResolvers = applicationContext.getBeansOfType(ParamResolver.class).values();
		registerControllers(applicationContext.getBeansWithAnnotation(MsgController.class).values());
		registerControllerAdvices(applicationContext.getBeansWithAnnotation(MsgControllerAdvice.class).values());
	}

	private void registerControllers(Collection<Object> controllers) throws ExceptionRemappingException {
		for (Object controller : controllers) {
			for (Method m : controller.getClass().getMethods()) {
				parseMsgMapping(MergedAnnotations.from(m).get(MsgMapping.class), controller, m);
				parseExceptionHandler(MergedAnnotations.from(m).get(ExceptionHandler.class), controller, m);
			}
		}
	}

	private void registerControllerAdvices(Collection<Object> advices) {
		for (Object advice : advices) {
			for (Method m : advice.getClass().getMethods()) {
				parseGlobalExceptionHandler(MergedAnnotations.from(m).get(ExceptionHandler.class), advice, m);
			}
		}
	}

	private void parseMsgMapping(MergedAnnotation<MsgMapping> annotation, Object handler, Method method) {
		if (!annotation.isPresent()) {
			return;
		}
		method.getParameters();
		int[] codes = annotation.getIntArray(MergedAnnotation.VALUE);
		for (int code : codes) {
			MethodHandler pre = handlerMapping.get(code);
			if (pre != null) {
				throw new MsgRemappingException(code, pre.getHandleMethod(), method);
			}
			handlerMapping.put(code, new MethodHandler(handler, method));
		}
	}

	private void parseExceptionHandler(MergedAnnotation<ExceptionHandler> annotation, Object h, Method m) {
		registerExceptionTable(
			exceptionHandlerTable.computeIfAbsent(h, k -> new ExceptionHandlerTable(globalExceptionHandlerTable)),
			annotation, new MethodHandler(h, m)
		);
	}

	private void parseGlobalExceptionHandler(MergedAnnotation<ExceptionHandler> annotation, Object advice, Method m) {
		registerExceptionTable(globalExceptionHandlerTable, annotation, new MethodHandler(advice, m));
	}

	@SuppressWarnings("unchecked")
	private void registerExceptionTable(ExceptionHandlerTable table, MergedAnnotation<ExceptionHandler> annotation,
		MethodHandler handler) {
		if (!annotation.isPresent()) {
			return;
		}
		for (Class<Exception> e : (Class<Exception>[]) annotation.getClassArray(MergedAnnotation.VALUE)) {
			table.register(e, handler);
		}
	}

}
