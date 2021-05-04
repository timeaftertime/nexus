package cn.milai.nexus.handler.paramresolve;

import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import cn.milai.nexus.handler.Msg;
import io.netty.channel.ChannelHandlerContext;

/**
 * 解析 {@link ChannelHandlerContext} 的参数解析器
 * @author milai
 * @date 2021.01.17
 */
@Order(3)
@Component
public class ChannelHandlerContextParamResolver extends AbstractParamResolver {

	@Override
	public boolean supports(MethodParameter param) {
		return ChannelHandlerContext.class.isAssignableFrom(param.getParameterType());
	}

	@Override
	protected Object doResolve(MethodParameter param, ChannelHandlerContext ctx, Msg msg)
		throws UnresolveableParamException {
		return ctx;
	}

}
