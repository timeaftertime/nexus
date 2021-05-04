package cn.milai.nexus.handler;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

import cn.milai.nexus.annotation.MsgControllerAdvice;
import cn.milai.nexus.annotation.MsgExceptionHandler;
import io.netty.channel.ChannelHandlerContext;

/**
 * 所处理异常逐级继承的 {@link MsgControllerAdvice} 测试工具类
 * @author milai
 * @date 2021.05.04
 */
@MsgControllerAdvice
@Configuration
@ConditionalOnProperty(ExtendsExceptionAdvice.ENABLE_PROPERTY)
public class ExtendsExceptionAdvice {

	public static final String ENABLE_PROPERTY = "cn.milai.nexus.handler.extends-exception-advice.enable";

	public static final String ENABLE_ARG = "--" + ENABLE_PROPERTY;

	private AtomicInteger argumentCount = new AtomicInteger();
	private AtomicInteger runtimeCount = new AtomicInteger();
	private AtomicInteger exceptionCount = new AtomicInteger();

	@MsgExceptionHandler(IllegalArgumentException.class)
	public void handleArgumentException(ChannelHandlerContext ctx, IllegalArgumentException e) {
		argumentCount.incrementAndGet();
	}

	@MsgExceptionHandler(RuntimeException.class)
	public void handleRuntimeException(ChannelHandlerContext ctx, RuntimeException e) {
		runtimeCount.incrementAndGet();
	}

	@MsgExceptionHandler()
	public void handleException(ChannelHandlerContext ctx, Exception e) {
		exceptionCount.incrementAndGet();
	}

	public int getArgumentCount() { return argumentCount.get(); }

	public int getRuntimeCount() { return runtimeCount.get(); }

	public int getExceptionCount() { return exceptionCount.get(); }

}