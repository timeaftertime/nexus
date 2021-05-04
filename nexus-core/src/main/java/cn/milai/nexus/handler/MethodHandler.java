package cn.milai.nexus.handler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 持有单个方法的处理器
 * @author milai
 * @date 2021.01.03
 */
public class MethodHandler {

	/**
	 * 方法持有对象
	 */
	private Object handler;

	/**
	 * 处理器方法
	 */
	private Method handleMethod;

	public MethodHandler(Object handler, Method handleMethod) {
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

	public Object getHandler() { return handler; }

	public Method getHandleMethod() { return handleMethod; }

}
