package cn.milai.nexus.handler.catcher;

import io.netty.channel.ChannelHandlerContext;

/**
 * 异常处理器
 * @author milai
 * @date 2021.01.14
 */
public interface ExceptionCatcher {

	/**
	 * 处理给定 {@link ChannelHandlerContext} 发生的 {@link Throwable} 异常
	 * @param ctx
	 * @param cause
	 * @throws Exception
	 */
	void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception;
}
