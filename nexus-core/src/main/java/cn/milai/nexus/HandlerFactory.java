package cn.milai.nexus;

import io.netty.channel.ChannelHandler;

/**
 * 一个 Nexus 服务端或客户端处理器工厂
 * @author milai
 * @date 2021.01.16
 */
public interface HandlerFactory {

	/**
	 * 获取需要添加到每个连接的 {@code ChannelHandler} 数组
	 * @return
	 */
	ChannelHandler[] getChannelHandlers();
}
