package cn.milai.nexus.handler;

import cn.milai.nexus.handler.msg.Msg;
import io.netty.channel.ChannelHandlerContext;

/**
 * 拦截器	
 * @author milai
 * @date 2021.05.29
 */
public interface HandlerInterceptor {

	/**
	 * Handler 被执行前调用
	 * @param ctx
	 * @param msg
	 * @param handler
	 * @return 执行链是否需要执行下一个 {@link HandlerInterceptor} 或 handler
	 */
	boolean preHandle(ChannelHandlerContext ctx, Msg msg, Object handler);

}
