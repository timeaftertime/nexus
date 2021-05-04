package cn.milai.nexus.handler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.stereotype.Component;

import cn.milai.nexus.annotation.MsgController;
import cn.milai.nexus.annotation.MsgControllerAdvice;
import cn.milai.nexus.annotation.MsgExceptionHandler;
import cn.milai.nexus.annotation.MsgMapping;
import cn.milai.nexus.handler.paramresolve.ParamResolver;
import cn.milai.nexus.handler.paramresolve.ParamResolverComposite;
import cn.milai.nexus.handler.paramresolve.ExceptionParamResolver;
import io.netty.channel.ChannelHandlerContext;

/**
 * 消息分发器
 * @author milai
 * @date 2020.12.29
 */
@Component
public class MsgDispatcher implements ApplicationContextAware {

	private static final Logger LOG = LoggerFactory.getLogger(MsgDispatcher.class);

	private ApplicationContext applicationContext;

	/**
	 * 消息 code 及对应的处理方法的映射
	 */
	private Map<Integer, MethodHandler> handlerMapping = new HashMap<>();

	/**
	 * {@link MsgController} 内定义的 {@link Throwable} 即其处理器 {@link MethodHandler}
	 */
	private Map<Object, Map<Class<Exception>, MethodHandler>> exceptionHandlers = new HashMap<>();

	private Map<Class<Exception>, MethodHandler> globalExceptionHandlers = new HashMap<>();

	/**
	 * 分发消息到对应的处理器处理
	 * @param msg
	 */
	void dispatch(ChannelHandlerContext ctx, Msg msg) throws Exception {
		LOG.info("接收到消息, msg = {}", msg);
		MethodHandler handler = handlerMapping.get(msg.getCode());
		if (handler == null) {
			LOG.warn("没有找到对应的处理器， code = {}, handlers = {}", msg.getCode(), handlerMapping.keySet());
			return;
		}
		ParamResolverComposite paramResolver = new ParamResolverComposite(
			applicationContext.getBeansOfType(ParamResolver.class).values()
		);
		Object[] params = paramResolver.resolveParams(handler.getHandleMethod(), ctx, msg);
		try {
			handler.invoke(params);
		} catch (InvocationTargetException ex) {
			Exception e = (Exception) ex.getTargetException();
			LOG.error("处理消息时发生异常: msg = {}, e = {}", msg, ExceptionUtils.getStackFrames(e));
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
		MethodHandler controllerExceptionHandler = findExceptionHandlerIn(
			exceptionHandlers.getOrDefault(
				handler.getHandler(), Collections.emptyMap()
			), handler, e
		);
		if (controllerExceptionHandler != null) {
			return controllerExceptionHandler;
		}
		return findExceptionHandlerIn(globalExceptionHandlers, controllerExceptionHandler, e);
	}

	private MethodHandler findExceptionHandlerIn(Map<Class<Exception>, MethodHandler> handlers, MethodHandler handler,
		Exception e) {
		Class<?> clazz = e.getClass();
		while (clazz != Object.class) {
			if (handlers.containsKey(clazz)) {
				return handlers.get(clazz);
			}
			clazz = clazz.getSuperclass();
		}
		return null;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws HandlerRedeclareException {
		this.applicationContext = applicationContext;
		registerControllers(applicationContext.getBeansWithAnnotation(MsgController.class).values());
		registerControllerAdvices(applicationContext.getBeansWithAnnotation(MsgControllerAdvice.class).values());
	}

	private void registerControllers(Collection<Object> controllers) throws ExceptionRemappingException {
		for (Object controller : controllers) {
			for (Method m : controller.getClass().getMethods()) {
				parseMsgMapping(MergedAnnotations.from(m).get(MsgMapping.class), controller, m);
				parseControllerExceptionHandler(
					MergedAnnotations.from(m).get(MsgExceptionHandler.class), controller, m
				);
			}
		}
	}

	private void registerControllerAdvices(Collection<Object> advices) {
		for (Object advice : advices) {
			for (Method m : advice.getClass().getMethods()) {
				parseGlobalExceptionHandler(MergedAnnotations.from(m).get(MsgExceptionHandler.class), advice, m);
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

	private void parseControllerExceptionHandler(MergedAnnotation<MsgExceptionHandler> annotation, Object h, Method m) {
		parseExceptionHandler(exceptionHandlers.get(h), annotation, h, m);
	}

	private void parseGlobalExceptionHandler(MergedAnnotation<MsgExceptionHandler> annotation, Object advice,
		Method m) {
		parseExceptionHandler(globalExceptionHandlers, annotation, advice, m);
	}

	@SuppressWarnings("unchecked")
	private void parseExceptionHandler(Map<Class<Exception>, MethodHandler> exceptionHandlers,
		MergedAnnotation<MsgExceptionHandler> annotation, Object advice, Method method) {
		if (!annotation.isPresent()) {
			return;
		}
		registerThrowableHandler(
			exceptionHandlers,
			(Class<Exception>[]) annotation.getClassArray(MergedAnnotation.VALUE),
			new MethodHandler(advice, method)
		);
	}

	private void registerThrowableHandler(Map<Class<Exception>, MethodHandler> pres, Class<Exception>[] ex,
		MethodHandler handler) {
		for (Class<Exception> e : ex) {
			MethodHandler pre = pres.get(e);
			if (pre != null) {
				throw new ExceptionRemappingException(e, pre.getHandleMethod(), handler.getHandleMethod());
			}
			pres.put(e, handler);
		}
	}

}
