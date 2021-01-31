package cn.milai.nexus.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.milai.common.base.Digests;
import cn.milai.common.base.Randoms;
import io.netty.channel.ChannelHandlerContext;

/**
 * Handler 可以处理的消息实体类
 * @author milai
 * @date 2021.01.02
 */
public class Msg {

	private static final int ID_LENGTH = 32;

	/**
	 * 消息唯一标识
	 */
	private String id;

	/**
	 * 消息种类唯一标识
	 */
	private int code;

	/**
	 * 消息发送 unix 时间
	 */
	private long time;

	/**
	 * 消息数据
	 */
	private JSONObject data;

	public Msg(String id, int code, long time, JSONObject data) {
		this.id = id;
		this.code = code;
		this.time = time;
		this.data = data;
	}

	/**
	 * 构造指定 code 和 data 的 Msg 并写入指定 {@link ChannelHandlerContext}
	 * @param ctx
	 * @param code
	 * @param data
	 */
	public static void write(ChannelHandlerContext ctx, int code, Object data) {
		ctx.write(new Msg(generateId(), code, System.currentTimeMillis() / 1000, toJSONObject(data)));
	}

	/**
	 * 构造指定 code 和 data 的 Msg 并写入指定 {@link ChannelHandlerContext}，调用其 flush 方法
	 * @param ctx
	 * @param code
	 * @param data
	 */
	public static void writeAndFlush(ChannelHandlerContext ctx, int code, Object data) {
		write(ctx, code, data);
		ctx.flush();
	}

	private static JSONObject toJSONObject(Object data) {
		return JSON.parseObject(JSON.toJSONString(data));
	}

	private static String generateId() {
		return Digests.sha256(System.nanoTime() + Randoms.randLowerOrDigit(8)).substring(0, ID_LENGTH);
	}

	public String getId() {
		return id;
	}

	public int getCode() {
		return code;
	}

	public long getTime() {
		return time;
	}

	public JSONObject getData() {
		return data;
	}

	@Override
	public String toString() {
		return "Msg [code=" + code + ", time=" + time + ", data=" + data + "]";
	}

}
