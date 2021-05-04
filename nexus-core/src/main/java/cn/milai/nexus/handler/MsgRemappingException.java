package cn.milai.nexus.handler;

import java.lang.reflect.Method;

/**
 * 重复定义消息处理器的异常
 * @author milai
 * @date 2021.05.04
 */
public class MsgRemappingException extends HandlerRedeclareException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MsgRemappingException(int code, Method pre, Method now) {
		super("消息处理器[code = " + code + "]", pre, now);
	}

}
