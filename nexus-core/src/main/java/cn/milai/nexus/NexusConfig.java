package cn.milai.nexus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import cn.milai.nexus.codec.MsgCarrierCodec;
import cn.milai.nexus.codec.MsgToCarrierCodec;
import cn.milai.nexus.conf.NexusConf;
import cn.milai.nexus.handler.BaseExceptionHandler;
import cn.milai.nexus.handler.DispatcherHandler;
import cn.milai.nexus.handler.ExceptionHandler;
import cn.milai.nexus.handler.OnlineHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;

@Configuration
@ComponentScan
public class NexusConfig {

	@Autowired
	private NexusConf conf;

	@Autowired
	private DispatcherHandler dispatcher;

	@Bean
	@Lazy(true)
	@ConditionalOnProperty(prefix = NexusConf.PREFIX, name = "mode", havingValue = NexusConf.MODE_CLIENT)
	public NexusClient nexusClient() {
		return new NexusClient(conf.getHost(), conf.getPort(), this::handlers);
	}

	@Bean
	@ConditionalOnProperty(prefix = NexusConf.PREFIX, name = "mode", havingValue = NexusConf.MODE_SERVER)
	public NexusServer nexusServer() {
		return new NexusServer(conf.getPort(), this::handlers);
	}

	private ChannelHandler[] handlers() {
		return new ChannelHandler[] { new MsgCarrierCodec(), new MsgToCarrierCodec(), dispatcher };
	}

	@Bean
	@ConditionalOnMissingBean(OnlineHandler.class)
	public OnlineHandler defaultOnlineHandler() {
		return new OnlineHandler() {

			@Override
			public void disconnect(ChannelHandlerContext ctx) {
			}

			@Override
			public void connect(ChannelHandlerContext ctx) {
			}
		};
	}

	@Bean
	@ConditionalOnMissingBean(ExceptionHandler.class)
	public ExceptionHandler baseExceptionHandler() {
		return new BaseExceptionHandler();
	}

}
