package cn.milai.nexus.handler.resolver;

import org.springframework.core.MethodParameter;

import cn.milai.common.util.Classes;
import cn.milai.nexus.handler.Msg;
import io.netty.channel.ChannelHandlerContext;

/**
 * 简单类型的 {@link ParamResolver} 实现
 * @author milai
 * @date 2021.01.02
 */
public class SingleTypeParamResolver extends AbstractParamResolver {

	@Override
	public boolean supports(MethodParameter param) {
		return Classes.isSingle(param.getParameterType());
	}

	@Override
	public Object doResolve(MethodParameter param, ChannelHandlerContext ctx, Msg msg) {
		Class<?> c = param.getParameterType();
		String paramName = param.getParameterName();
		if (Classes.isInts(c)) {
			return msg.getData().getInteger(paramName);
		}
		if (Classes.isLong(c)) {
			return msg.getData().getLong(paramName);
		}
		if (Classes.isBools(c)) {
			return msg.getData().getBoolean(paramName);
		}
		if (Classes.isFloats(c)) {
			return msg.getData().getFloat(paramName);
		}
		if (Classes.isDoubles(c)) {
			return msg.getData().getDouble(paramName);
		}
		if (c == String.class) {
			return msg.getData().getString(paramName);
		}
		throw new UnresolveableParamException(ParamResolver.paramSignature(param));
	}

}
