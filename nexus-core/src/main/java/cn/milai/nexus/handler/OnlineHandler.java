package cn.milai.nexus.handler;

import io.netty.channel.ChannelHandlerContext;

/**
 * 连接建立或关闭时的处理器
 * @author milai
 * @date 2021.01.14
 */
public interface OnlineHandler {

	/**
	 * 处理 {@link ChannelHandlerContext} 建立连接
	 * @param ctx
	 */
	public void connect(ChannelHandlerContext ctx);

	/**
	 * 处理 {@link ChannelHandlerContext} 关闭连接
	 * @param ctx
	 */
	public void disconnect(ChannelHandlerContext ctx);

}
