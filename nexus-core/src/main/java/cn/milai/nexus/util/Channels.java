package cn.milai.nexus.util;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;

/**
 * {@link Channel} 相关工具类
 * @author milai
 * @date 2021.01.18
 */
public class Channels {

	/**
	 * 属性 [用户 id]
	 */
	public static final String ATTR_USER_ID = "userId";

	private Channels() {
	}

	/**
	 * 获取 {@link Channel} 指定 name 的 String 类型属性
	 * @param ch
	 * @param name
	 * @return
	 */
	public static String stringAttr(Channel ch, String name) {
		AttributeKey<String> key = AttributeKey.valueOf(ATTR_USER_ID);
		return ch.attr(key).get();
	}

	/**
	 * 获取 {@link ChannelHandlerContext} 对应 {@link Channel} 指定 name 的 String 类型属性
	 * @param ch
	 * @param name
	 * @return
	 */
	public static String stringAttr(ChannelHandlerContext ctx, String name) {
		AttributeKey<String> key = AttributeKey.valueOf(ATTR_USER_ID);
		return ctx.channel().attr(key).get();
	}

	/**
	 * 获取 {@link Channel} 指定 name 的 Long 类型属性
	 * @param ch
	 * @param name
	 * @return
	 */
	public static Long longAttr(Channel ch, String name) {
		AttributeKey<Long> key = AttributeKey.valueOf(name);
		return ch.attr(key).get();
	}

	/**
	 * 获取 {@link ChannelHandlerContext} 对应 {@link Channel} 指定 name 的 Long 类型属性
	 * @param ch
	 * @param name
	 * @return
	 */
	public static Long longAttr(ChannelHandlerContext ctx, String name) {
		AttributeKey<Long> key = AttributeKey.valueOf(name);
		return ctx.channel().attr(key).get();
	}

	/**
	 * 设置 {@link Channel} 指定 name 的属性为 value
	 * @param ch
	 * @param name
	 * @param value
	 */
	public static void setAttr(Channel ch, String name, Object value) {
		ch.attr(AttributeKey.valueOf(name)).set(value);
	}

	/**
	 * 设置 {@link ChannelHandlerContext } 对应 {@link Channel} 指定 name 的属性为 value
	 * @param ch
	 * @param ctx
	 * @param name
	 * @param value
	 */
	public static void setAttr(ChannelHandlerContext ctx, String name, Object value) {
		setAttr(ctx.channel(), name, value);
	}
}
