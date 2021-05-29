package cn.milai.nexus.handler.interceptor;

import java.util.Set;

import cn.milai.nexus.handler.msg.Msg;

/**
 * 只放行 {@link Msg#getCode()} 为指定值的 {@link Interceptor}
 * @author milai
 * @date 2021.05.29
 */
public class AllowMsgCodeInterceptor extends ConditionInterceptor {

	public AllowMsgCodeInterceptor(Set<Integer> allowCodes) {
		super(m -> !allowCodes.contains(m.getCode()));
	}

}
