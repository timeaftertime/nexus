package cn.milai.nexus.handler;

import java.lang.reflect.Method;

/**
 * 重复定义异常处理器的异常
 * @author milai
 * @date 2021.05.04
 */
public class ExceptionRemappingException extends HandlerRedeclareException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExceptionRemappingException(Class<Exception> t, Method pre, Method now) {
		super("异常处理器[throwable = " + t.getName() + "]", pre, now);
	}

}
