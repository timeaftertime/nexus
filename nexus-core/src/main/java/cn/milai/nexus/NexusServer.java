package cn.milai.nexus;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Nexus 服务端
 * @author milai
 * @date 2021.01.14
 */
public class NexusServer {

	private ServerBootstrap bootstrap;
	private ChannelFuture bindFuture;
	private EventLoopGroup bossGroup;
	private EventLoopGroup workerGroup;

	public NexusServer(int port, HandlerFactory factory) {
		bossGroup = new NioEventLoopGroup();
		workerGroup = new NioEventLoopGroup();
		bootstrap = new ServerBootstrap();
		bootstrap.group(bossGroup, workerGroup)
			.channel(NioServerSocketChannel.class)
			.childOption(ChannelOption.SO_KEEPALIVE, true)
			.childHandler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(factory.getChannelHandlers());
				}
			});
		bindFuture = bootstrap.bind(port);
	}

	/**
	 * 获取绑定的 Netty {@link ServerBootstrap} 对象
	 * @return
	 */
	public ServerBootstrap getServerBootstrap() {
		return bootstrap;
	}

	/**
	 * 关闭服务端，返回 channel 关闭的 {@link ChannelFuture}
	 * @return
	 */
	public ChannelFuture shutdown() {
		bossGroup.shutdownGracefully();
		workerGroup.shutdownGracefully();
		return bindFuture.channel().close();
	}

}
