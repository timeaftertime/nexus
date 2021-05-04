package cn.milai.nexus.handler.paramresolve;

import org.springframework.core.MethodParameter;

import cn.milai.nexus.handler.Msg;
import cn.milai.nexus.util.Methods;
import io.netty.channel.ChannelHandlerContext;

/**
 * 处理器参数解析器，由于可能需要并发解析参数，必须实现为无状态的
 * @author milai
 * @date 2021.01.02
 */
public interface ParamResolver {

	/**
	 * 是否支持对应参数类型解析
	 * @param param
	 * @return
	 */
	boolean supports(MethodParameter param);

	/**
	 * 解析 param 参数，返回解析结果，若不是支持的解析类型则抛出异常
	 * @param param
	 * @param ctx
	 * @param msg
	 * @return
	 */
	Object resolve(MethodParameter param, ChannelHandlerContext ctx, Msg msg) throws UnresolveableParamException;

	/**
	 * 获取 {@link MethodParameter} 所代表参数的完整签名
	 * @param param
	 * @return
	 */
	static String paramSignature(MethodParameter param) {
		return String.format("%s(%s)", Methods.fullSignature(param.getMethod()), param.getParameter().getName());
	}

}
