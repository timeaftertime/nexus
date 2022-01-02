package cn.milai.nexus.handler.msg;

/**
 * 消息
 * @author milai
 * @date 2021.05.28
 */
public interface Msg {

	/**
	 * 获取消息唯一标识
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

}
