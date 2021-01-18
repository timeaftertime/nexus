package cn.milai.nexus;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Nexus 客户端
 * @author milai
 * @date 2021.01.14
 */
public class NexusClient {

	private Bootstrap bootstrap;
	private ChannelFuture connectFuture;
	private EventLoopGroup group;
	private String serverHost;
	private int serverPort;

	public NexusClient(String serverHost, int serverPort, HandlerFactory factory) {
		this.serverHost = serverHost;
		this.serverPort = serverPort;
		this.bootstrap = new Bootstrap();
		bootstrap.option(ChannelOption.SO_KEEPALIVE, true)
			.channel(NioSocketChannel.class)
			.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(factory.getChannelHandlers());
				}
			});
	}

	/**
	 * 获取绑定的 {@link Bootstrap}
	 * @return
	 */
	public Bootstrap getBootstrap() {
		return bootstrap;
	}

	/**
	 * 异步连接到服务端，返回 connect 操作的 {@link ChannelFuture}
	 * @return
	 */
	public ChannelFuture connect() {
		group = new NioEventLoopGroup();
		bootstrap.group(group);
		return connectFuture = bootstrap.connect(serverHost, serverPort);
	}

	/**
	 * 关闭客户端，关闭 channel 的 {@link ChannelFuture}
	 * 若不曾调用 {@link #connect()} ，则返回 null
	 * @return
	 */
	public ChannelFuture shutdown() {
		if (group != null) {
			group.shutdownGracefully();
		}
		return connectFuture == null ? null : connectFuture.channel().close();
	}

}
