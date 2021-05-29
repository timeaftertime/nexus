package cn.milai.nexus.handler.paramresolve;

import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import cn.milai.nexus.handler.msg.Msg;
import io.netty.channel.ChannelHandlerContext;

/**
 * 解析 {@link Msg} 的参数解析器
 * @author milai
 * @date 2021.05.29
 */
@Order(3)
@Component
public class MsgParamResolver extends TypeParamResolver {

	public MsgParamResolver() {
		super(Msg.class);
	}

	@Override
	protected Object doResolve(MethodParameter param, ChannelHandlerContext ctx, Msg msg)
		throws UnresolveableParamException {
		return msg;
	}

}
