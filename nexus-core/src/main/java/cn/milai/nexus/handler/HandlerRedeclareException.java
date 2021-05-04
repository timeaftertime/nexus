package cn.milai.nexus.handler;

import java.lang.reflect.Method;

import cn.milai.nexus.NexusException;
import cn.milai.nexus.util.Methods;

/**
 * 重复定义处理器
 * @author milai
 * @date 2021.01.01
 */
public class HandlerRedeclareException extends NexusException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 构造一个表示 pre 和 now 两个处理器同时被声明用于处理 target 的异常
	 * @param target
	 * @param pre
	 * @param now
	 */
	public HandlerRedeclareException(String target, Method pre, Method now) {
		super("重复定义处理器: %s, pre = %s, now = %s", target, Methods.fullSignature(pre), Methods.fullSignature(now));
	}

}
