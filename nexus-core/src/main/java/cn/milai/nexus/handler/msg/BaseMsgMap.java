package cn.milai.nexus.handler.msg;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * MsgMap 默认实现
 * @author milai
 * @date 2021.05.28
 */
public class BaseMsgMap implements MsgMap {

	private JSONObject json;

	public BaseMsgMap(JSONObject json) {
		this.json = json;
	}

	public BaseMsgMap(String jsonString) {
		this.json = JSON.parseObject(jsonString);
	}

	@Override
	public MsgMap getMap(String key) {
		return new BaseMsgMap(json.getJSONObject(key));
	}

	@Override
	public MsgList getList(String key) {
		return new BaseMsgList(json.getJSONArray(key));
	}

	@Override
	public Boolean getBoolean(String key) {
		return json.getBoolean(key);
	}

	@Override
	public boolean getBooleanValue(String key) {
		return json.getBooleanValue(key);
	}

	@Override
	public byte[] getBytes(String key) {
		return json.getBytes(key);
	}

	@Override
	public Integer getInteger(String key) {
		return json.getInteger(key);
	}

	@Override
	public int getIntValue(String key) {
		return json.getIntValue(key);
	}

	@Override
	public Long getLong(String key) {
		return json.getLong(key);
	}

	@Override
	public long getLongValue(String key) {
		return json.getLongValue(key);
	}

	@Override
	public Float getFloat(String key) {
		return json.getFloat(key);
	}

	@Override
	public float getFloatValue(String key) {
		return json.getFloatValue(key);
	}

	@Override
	public Double getDouble(String key) {
		return json.getDouble(key);
	}

	@Override
	public double getDoubleValue(String key) {
		return json.getDoubleValue(key);
	}

	@Override
	public String getString(String key) {
		return json.getString(key);
	}

	@Override
	public <T> T get(String key, Class<T> clazz) {
		return json.getObject(key, clazz);
	}

	@Override
	public String toJSONString() {
		return json.toJSONString();
	}

	@Override
	public String toString() {
		return toJSONString();
	}
}
