package cn.milai.nexus.handler;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;

/**
 * {@link ExceptionHandler} 默认实现
 * @author milai
 * @date 2021.01.16
 */
public class BaseExceptionHandler implements ExceptionHandler {

	private static final Logger LOG = LoggerFactory.getLogger(BaseExceptionHandler.class);

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		LOG.error(ExceptionUtils.getStackTrace(cause));
	}

}
