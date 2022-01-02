package cn.milai.nexus.handler.msg;

import cn.milai.common.api.data.JSON;

/**
 * 只带一个消息体参数的 {@link Msg}
 * @author milai
 * @date 2021.05.28
 */
public class SingleMsg extends BaseMsg {

	public SingleMsg(int code, String key, Object value) {
		super(code, JSON.write(key, value));
	}

}
