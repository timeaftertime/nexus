package cn.milai.nexus.handler.interceptor;

import java.util.Set;

import cn.milai.nexus.handler.msg.Msg;

/**
 * 只拦截 {@link Msg#getCode()} 为指定值的 {@link Interceptor}
 * @author milai
 * @date 2021.05.29
 */
public class DisallowMsgCodeInterceptor extends ConditionInterceptor {

	public DisallowMsgCodeInterceptor(Set<Integer> disallowCodes) {
		super(m -> disallowCodes.contains(m.getCode()));
	}

}
