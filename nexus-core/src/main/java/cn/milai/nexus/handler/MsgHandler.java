package cn.milai.nexus.handler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 单个方法的消息处理器
 * @author milai
 * @date 2021.01.03
 */
public class MsgHandler {

	/**
	 * 方法持有对象
	 */
	private Object handler;

	/**
	 * 处理器方法
	 */
	private Method handleMethod;

	public MsgHandler(Object handler, Method handleMethod) {
		this.handler = handler;
		this.handleMethod = handleMethod;
	}

	/**
	 * 反射调用对应的处理函数
	 * @param args
	 * @return
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public Object invoke(Object... args) throws IllegalAccessException, IllegalArgumentException,
		InvocationTargetException {
		return handleMethod.invoke(handler, args);
	}

	public Object getHandler() {
		return handler;
	}

	public Method getHandleMethod() {
		return handleMethod;
	}

}
