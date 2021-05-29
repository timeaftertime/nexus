package cn.milai.nexus.handler.paramresolve;

import org.springframework.core.MethodParameter;

import cn.milai.nexus.handler.msg.Msg;
import io.netty.channel.ChannelHandlerContext;

/**
 * {@link ParamResolver} 的抽象实现
 * @author milai
 * @date 2021.01.02
 */
public abstract class AbstractParamResolver implements ParamResolver {

	@Override
	public final Object resolve(MethodParameter param, ChannelHandlerContext ctx, Msg msg)
		throws UnresolveableParamException {
		if (!supports(param)) {
			throw new UnresolveableParamException(ParamResolver.paramSignature(param));
		}
		return doResolve(param, ctx, msg);
	}

	/**
	 * 实际解析参数并返回解析结果
	 * @param param
	 * @param ctx 
	 * @param msg
	 * @return
	 */
	protected abstract Object doResolve(MethodParameter param, ChannelHandlerContext ctx, Msg msg)
		throws UnresolveableParamException;

}
