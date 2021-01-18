package cn.milai.nexus.handler.resolver;

import cn.milai.nexus.NexusException;

/**
 * 参数无法解析的异常
 * @author milai
 * @date 2021.01.03
 */
public class UnresolveableParamException extends NexusException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UnresolveableParamException(String mp) {
		super("解析参数失败, param = %s", mp);
	}

}
