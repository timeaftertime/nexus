package cn.milai.nexus.handler.msg;

import cn.milai.common.base.Digests;
import cn.milai.common.base.Randoms;

/**
 * 消息
 * @author milai
 * @date 2021.05.28
 */
public interface Msg {

	/**
	 * {@link #getId()} 长度
	 */
	int ID_LENGTH = 32;

	/**
	 * 获取消息唯一标识，长度为 {@link #ID_LENGTH} 的字符串
	 * @return
	 */
	String getId();

	/**
	 * 获取消息类型 code
	 * @return
	 */
	int getCode();

	/**
	 * 获取消息发送 unix 时间戳
	 * @return
	 */
	long getTime();

	/**
	 * 获取消息体
	 * @return
	 */
	MsgMap getData();

	/**
	 * 构造一个唯一 id
	 * @return
	 */
	static String generateId() {
		return Digests.sha256(System.nanoTime() + Randoms.fixedLowerDigit(8)).substring(0, ID_LENGTH);
	}
}
