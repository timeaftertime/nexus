package cn.milai.nexus.handler.msg;

import com.alibaba.fastjson.JSONArray;

/**
 * {@link MsgList} 默认实现
 * @author milai
 * @date 2021.05.28
 */
public class BaseMsgList implements MsgList {

	private JSONArray json;

	public BaseMsgList(JSONArray json) {
		this.json = json;
	}

	@Override
	public MsgMap getMap(int index) {
		return new BaseMsgMap(json.getJSONObject(index));
	}

	@Override
	public MsgList getList(int index) {
		return new BaseMsgList(json.getJSONArray(index));
	}

	@Override
	public Boolean getBoolean(int index) {
		return json.getBoolean(index);
	}

	@Override
	public boolean getBooleanValue(int index) {
		return json.getBooleanValue(index);
	}

	@Override
	public Integer getInteger(int index) {
		return json.getInteger(index);
	}

	@Override
	public int getIntValue(int index) {
		return json.getIntValue(index);
	}

	@Override
	public Long getLong(int index) {
		return json.getLong(index);
	}

	@Override
	public long getLongValue(int index) {
		return json.getLongValue(index);
	}

	@Override
	public Float getFloat(int index) {
		return json.getFloat(index);
	}

	@Override
	public float getFloatValue(int index) {
		return json.getFloatValue(index);
	}

	@Override
	public Double getDouble(int index) {
		return json.getDouble(index);
	}

	@Override
	public double getDoubleValue(int index) {
		return json.getDoubleValue(index);
	}

	@Override
	public String getString(int index) {
		return json.getString(index);
	}

	@Override
	public <T> T get(int index, Class<T> clazz) {
		return json.getObject(index, clazz);
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
