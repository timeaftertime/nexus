package cn.milai.nexus.handler.paramresolve;

import org.springframework.core.MethodParameter;

import cn.milai.nexus.handler.msg.Msg;
import io.netty.channel.ChannelHandlerContext;

/**
 * 解析设置好的指定异常的 {@link ParamResolver}
 * @author milai
 * @date 2021.05.03
 */
public class ExceptionParamResolver extends AbstractParamResolver {

	private Exception e;

	public ExceptionParamResolver(Exception throwable) {
		this.e = throwable;
	}

	@Override
	public boolean supports(MethodParameter param) {
		return Throwable.class.isAssignableFrom(param.getParameterType());
	}

	@Override
	protected Object doResolve(MethodParameter param, ChannelHandlerContext ctx, Msg msg)
		throws UnresolveableParamException {
		return e;
	}

}
