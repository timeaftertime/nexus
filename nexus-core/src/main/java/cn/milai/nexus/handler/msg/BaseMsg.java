package cn.milai.nexus.handler.msg;

import com.alibaba.fastjson.JSONObject;

/**
 * {@link Msg} 默认实现
 * @author milai
 * @date 2021.05.28
 */
public class BaseMsg implements Msg {

	private String id;

	private int code;

	private long time;

	private MsgMap data;

	/**
	 * 以指定 id 和参数创建一个 {@link BaseMsg}
	 * @param id 指定生成的消息 id
	 * @param code
	 * @param time
	 * @param data 消息体的 map JSON 字符串
	 */
	public BaseMsg(String id, int code, long time, String data) {
		this.id = id;
		this.code = code;
		this.time = time;
		this.data = new BaseMsgMap(data);
	}

	/**
	 * 以自动生成 id 和时间、指定参数创建一个 {@link BaseMsg}
	 * @param code
	 * @param time
	 * @param data
	 */
	public BaseMsg(int code, String data) {
		this(Msg.generateId(), code, System.currentTimeMillis() / 1000, data);
	}

	/**
	 * 以自动生成 id 和时间、指定对象的 JSON 字符串创建一个 {@link BaseMsg}
	 * @param code
	 * @param data
	 */
	public BaseMsg(int code, Object data) {
		this(code, JSONObject.toJSONString(data));
	}

	/**
	 * 以自动生成 id 和时间、空参数创建一个 {@link BaseMsg}
	 * @param code
	 */
	public BaseMsg(int code) {
		this(code, "{}");
	}

	@Override
	public String getId() { return id; }

	@Override
	public int getCode() { return code; }

	@Override
	public long getTime() { return time; }

	@Override
	public MsgMap getData() { return data; }

	@Override
	public String toString() {
		return "BaseMsg [id=" + id + ", code=" + code + ", time=" + time + ", data=" + data + "]";
	}

}
