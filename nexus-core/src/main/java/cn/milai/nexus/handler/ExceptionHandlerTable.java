package cn.milai.nexus.handler;

import java.util.HashMap;
import java.util.Map;

/**
 * {@link Exception} 处理 {@link MethodHandler} 注册表
 * @author milai
 * @date 2021.05.26
 */
public class ExceptionHandlerTable {

	private ExceptionHandlerTable parent;

	private Map<Class<? extends Exception>, MethodHandler> handlers;

	public ExceptionHandlerTable(ExceptionHandlerTable parent) {
		this.parent = parent;
		handlers = new HashMap<>();
	}

	public ExceptionHandlerTable() {
		this(null);
	}

	public void register(Class<? extends Exception> e, MethodHandler handler) {
		MethodHandler pre = handlers.get(e);
		if (pre != null) {
			throw new ExceptionRemappingException(e, pre.getHandleMethod(), handler.getHandleMethod());
		}
		handlers.put(e, handler);
	}

	public MethodHandler findHandler(Exception e) {
		Class<?> clazz = e.getClass();
		while (clazz != Object.class) {
			if (handlers.containsKey(clazz)) {
				return handlers.get(clazz);
			}
			clazz = clazz.getSuperclass();
		}
		return parent == null ? null : parent.findHandler(e);
	}
}
