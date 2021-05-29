package cn.milai.nexus.handler.msg;

import com.alibaba.fastjson.JSONObject;

/**
 * 只带一个消息体参数的 {@link Msg}
 * @author milai
 * @date 2021.05.28
 */
public class SingleMsg extends BaseMsg {

	public SingleMsg(int code, String key, Object value) {
		super(code, singleJSON(key, value).toJSONString());
	}

	private static JSONObject singleJSON(String key, Object value) {
		JSONObject data = new JSONObject();
		data.put(key, value);
		return data;
	}

}
