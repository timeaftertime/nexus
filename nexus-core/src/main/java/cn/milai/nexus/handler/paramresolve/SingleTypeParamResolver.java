package cn.milai.nexus.handler.paramresolve;

import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import cn.milai.common.base.Classes;
import cn.milai.nexus.handler.msg.Msg;
import io.netty.channel.ChannelHandlerContext;

/**
 * 简单类型的 {@link ParamResolver} 实现
 * @author milai
 * @date 2021.01.02
 */
@Order(1)
@Component
public class SingleTypeParamResolver extends AbstractParamResolver {

	@Override
	public boolean supports(MethodParameter param) {
		return Classes.isSingle(param.getParameterType());
	}

	@Override
	public Object doResolve(MethodParameter param, ChannelHandlerContext ctx, Msg msg) {
		Class<?> c = param.getParameterType();
		String paramName = param.getParameter().getName();
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
