package cn.milai.nexus.handler.msg;

import com.fasterxml.jackson.databind.JsonNode;

import cn.milai.common.api.data.JSON;

/**
 * MsgMap 默认实现
 * @author milai
 * @date 2021.05.28
 */
public class BaseMsgMap implements MsgMap {

	private JsonNode json;

	public BaseMsgMap(JsonNode json) {
		this.json = json;
	}

	public BaseMsgMap(String jsonString) {
		this.json = JSON.readTree(jsonString);
	}

	@Override
	public MsgMap asMap(String key) {
		JsonNode node = json.get(key);
		if (node.isMissingNode() || !node.isObject()) {
			return null;
		}
		return new BaseMsgMap(node);
	}

	@Override
	public MsgList asList(String key) {
		JsonNode node = json.get(key);
		if (node.isMissingNode() || !node.isArray()) {
			return null;
		}
		return new BaseMsgList(node);
	}

	@Override
	public boolean asBool(String key) {
		return json.get(key).asBoolean();
	}

	@Override
	public int asInt(String key) {
		return json.get(key).asInt();
	}

	@Override
	public long asLong(String key) {
		return json.get(key).asLong();
	}
	
	@Override
	public double asDouble(String key) {
		return json.get(key).asDouble();
	}

	@Override
	public String asString(String key) {
		JsonNode node = json.get(key);
		if (node == null || node.isNull()) {
			return "";
		}
		return node.asText();
	}

	@Override
	public <T> T as(String key, Class<T> clazz) {
		return JSON.read(json.get(key), clazz);
	}

	@Override
	public String toString() {
		return json.toString();
	}
}
