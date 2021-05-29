package cn.milai.nexus.handler.interceptor;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

import cn.milai.nexus.handler.msg.Msg;
import io.netty.channel.ChannelHandlerContext;

/**
 * 拦截所有请求的拦截器
 * @author milai
 * @date 2021.05.29
 */
@Configuration
@ConditionalOnProperty(InterceptAllInterceptor.ENABLE_PROPERTY)
public class InterceptAllInterceptor implements Interceptor {

	public static final String ENABLE_PROPERTY = "cn.milai.nexus.handler.intercept-all-interceptor.enable";

	public static final String ENABLE_ARG = "--" + ENABLE_PROPERTY;

	@Override
	public boolean preHandle(ChannelHandlerContext ctx, Msg msg, Object handler) {
		return false;
	}
}
