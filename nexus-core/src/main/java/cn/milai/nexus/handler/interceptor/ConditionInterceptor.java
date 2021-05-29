package cn.milai.nexus.handler.interceptor;

import java.util.function.Function;

import cn.milai.nexus.handler.msg.Msg;
import io.netty.channel.ChannelHandlerContext;

/**
 * 只拦截满足条件的消息的 {@link Interceptor}
 * @author milai
 * @date 2021.05.29
 */
public class ConditionInterceptor implements Interceptor {

	private Function<Msg, Boolean> condition;

	public ConditionInterceptor(Function<Msg, Boolean> condition) {
		this.condition = condition;
	}

	@Override
	public final boolean preHandle(ChannelHandlerContext ctx, Msg msg, Object handler) {
		if (!condition.apply(msg)) {
			return true;
		}
		return doPreHandle(ctx, msg, handler);
	}

	/**
	 * 实际处理拦截逻辑
	 * @param ctx
	 * @param msg
	 * @param handler
	 * @return
	 */
	protected boolean doPreHandle(ChannelHandlerContext ctx, Msg msg, Object handler) {
		return false;
	}

}
