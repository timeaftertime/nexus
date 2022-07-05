package cn.milai.nexus.handler.msg;

import cn.milai.common.uniform.generator.TimeRandomIdGenerator;
import cn.milai.common.uniform.serialize.JSON;

/**
 * {@link Msg} 默认实现
 * @author milai
 * @date 2021.05.28
 */
public class BaseMsg implements Msg {

	private static final TimeRandomIdGenerator ID_GENERATOR = new TimeRandomIdGenerator();

	private String id;

	private int code;

	private MsgMap data;

	/**
	 * 以指定 id 和参数创建一个 {@link BaseMsg}
	 * @param id 指定生成的消息 id
	 * @param code
	 * @param data 消息体的 map JSON 字符串
	 */
	public BaseMsg(String id, int code, String data) {
		this.id = id;
		this.code = code;
		this.data = new BaseMsgMap(data);
	}

	/**
	 * 以自动生成 id 和时间、指定对象的 JSON 字符串创建一个 {@link BaseMsg}
	 * @param code
	 * @param data
	 */
	public BaseMsg(int code, Object data) {
		this(ID_GENERATOR.next(), code, JSON.write(data));
	}

	/**
	 * 以自动生成 id 、时间和指定  JSON 字符串创建一个 {@link BaseMsg}
	 * @param code
	 * @param jsonData
	 */
	public BaseMsg(int code, String jsonData) {
		this(ID_GENERATOR.next(), code, jsonData);
	}

	/**
	 * 以自动生成 id 和时间、空参数创建一个 {@link BaseMsg}
	 * @param code
	 */
	public BaseMsg(int code) {
		this(ID_GENERATOR.next(), code, "{}");
	}

	@Override
	public String getId() { return id; }

	@Override
	public int getCode() { return code; }

	@Override
	public long getTime() { return TimeRandomIdGenerator.getTime(id); }

	@Override
	public MsgMap getData() { return data; }

	@Override
	public String toString() {
		return "BaseMsg [id=" + id + ", code=" + code + ", data=" + data + ", time=" + getTime() + "]";
	}

}
