package cn.milai.nexus.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.milai.nexus.handler.catcher.ExceptionCatcher;
import cn.milai.nexus.handler.connection.OnlineHandler;
import cn.milai.nexus.handler.msg.Msg;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 服务端 Netty 消息分发器
 * @author milai
 */
@Sharable
@Component
public class DispatcherHandler extends SimpleChannelInboundHandler<Msg> {

	private static final Logger LOG = LoggerFactory.getLogger(DispatcherHandler.class);

	@Autowired
	private MsgDispatcher msgDispatcher;
	@Autowired
	private ExceptionCatcher exceptionHandler;
	@Autowired
	private OnlineHandler onlines;

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		onlines.connect(ctx);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		onlines.disconnect(ctx);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		exceptionHandler.exceptionCaught(ctx, cause);
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Msg msg) throws Exception {
		LOG.debug("读取到消息, ctx = {}, msg = {}", ctx, msg);
		msgDispatcher.dispatch(ctx, msg);
	}

}
