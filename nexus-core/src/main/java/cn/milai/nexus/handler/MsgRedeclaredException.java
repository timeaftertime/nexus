package cn.milai.nexus.handler;

import java.lang.reflect.Method;

import cn.milai.nexus.NexusException;
import cn.milai.nexus.util.Methods;

/**
 * 重复定义相同消息处理的异常
 * @author milai
 * @date 2021.01.01
 */
public class MsgRedeclaredException extends NexusException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 构造一个表示指定 code 出现 pre 和 now 两个消息处理器的异常
	 * @param code
	 * @param pre
	 * @param now
	 */
	public MsgRedeclaredException(int code, Method pre, Method now) {
		super("", code, Methods.fullSignature(pre), Methods.fullSignature(now));
	}

}
