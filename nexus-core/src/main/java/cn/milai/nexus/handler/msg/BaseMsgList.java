package cn.milai.nexus.handler.msg;

import com.fasterxml.jackson.databind.JsonNode;

import cn.milai.common.api.data.JSON;

/**
 * {@link MsgList} 默认实现
 * @author milai
 * @date 2021.05.28
 */
public class BaseMsgList implements MsgList {

	private JsonNode json;

	public BaseMsgList(JsonNode json) {
		this.json = json;
	}

	@Override
	public MsgMap asMap(int index) {
		JsonNode node = json.get(index);
		if (node.isMissingNode() || !node.isObject()) {
			return null;
		}
		return new BaseMsgMap(node);
	}

	@Override
	public MsgList asList(int index) {
		JsonNode node = json.get(index);
		if (node.isMissingNode() || !node.isArray()) {
			return null;
		}
		return new BaseMsgList(node);
	}

	@Override
	public boolean asBool(int index) {
		return json.get(index).asBoolean();
	}

	@Override
	public int asInt(int index) {
		return json.get(index).asInt();
	}

	@Override
	public long asLong(int index) {
		return json.get(index).asLong();
	}

	@Override
	public double asDouble(int index) {
		return json.get(index).asDouble();
	}

	@Override
	public String asString(int index) {
		JsonNode node = json.get(index);
		if (node == null || node.isNull()) {
			return "";
		}
		return node.asText();
	}

	@Override
	public <T> T as(int index, Class<T> clazz) {
		return JSON.read(json.get(index), clazz);
	}

	@Override
	public String toString() {
		return json.toString();
	}

}
