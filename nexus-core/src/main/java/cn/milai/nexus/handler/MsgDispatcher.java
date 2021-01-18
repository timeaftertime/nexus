package cn.milai.nexus.handler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.stereotype.Component;

import cn.milai.nexus.NexusException;
import cn.milai.nexus.annotation.MsgController;
import cn.milai.nexus.annotation.MsgMapping;
import cn.milai.nexus.handler.resolver.ParamResolver;
import io.netty.channel.ChannelHandlerContext;

/**
 * 消息分发器
 * @author milai
 * @date 2020.12.29
 */
@Component
public class MsgDispatcher implements ApplicationContextAware {

	private static final Logger LOG = LoggerFactory.getLogger(MsgDispatcher.class);

	private static final String CODE = "code";

	/**
	 * 消息 code 及对应的处理方法的映射
	 */
	private Map<Integer, MsgHandler> handlerMapping = new HashMap<>();

	@Autowired
	private ParamResolver paramResolver;

	/**
	 * 分发消息到对应的处理器处理
	 * @param msg
	 */
	void dispatch(ChannelHandlerContext ctx, Msg msg) throws NexusException {
		LOG.info("接收到消息, msg = {}", msg);
		MsgHandler handler = handlerMapping.get(msg.getCode());
		if (handler == null) {
			LOG.warn("没有找到对应的处理器， code = {}, handlers = {}", msg.getCode(), handlerMapping.keySet());
			return;
		}
		Object[] params = new Object[handler.getHandleMethod().getParameterCount()];
		for (int i = 0; i < params.length; i++) {
			params[i] = paramResolver.resolve(new MethodParameter(handler.getHandleMethod(), i), ctx, msg);
		}
		try {
			handler.invoke(params);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			LOG.error("调用消息处理器失败, msg = {}, e = {}", msg, ExceptionUtils.getStackFrames(e));
			throw new NexusException(e);
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws MsgRedeclaredException {
		Map<String, Object> msgHandlers = applicationContext.getBeansWithAnnotation(MsgController.class);
		for (String name : msgHandlers.keySet()) {
			Object msgHandler = msgHandlers.get(name);
			for (Method method : msgHandler.getClass().getMethods()) {
				MergedAnnotations annotations = MergedAnnotations.from(method);
				MergedAnnotation<MsgMapping> handleMsg = annotations.get(MsgMapping.class);
				if (!handleMsg.isPresent()) {
					continue;
				}
				method.getParameters();
				int[] codes = handleMsg.getIntArray(CODE);
				for (int code : codes) {
					MsgHandler pre = handlerMapping.get(code);
					if (pre != null) {
						throw new MsgRedeclaredException(code, pre.getHandleMethod(), method);
					}
					handlerMapping.put(code, new MsgHandler(msgHandler, method));
				}
			}
		}
	}

}
