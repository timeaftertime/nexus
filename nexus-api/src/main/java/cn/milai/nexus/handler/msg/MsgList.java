package cn.milai.nexus.handler.msg;

/**
 * 消息 List 参数。get 值类型的不存在 index 或类型错误时将返回该类型的默认值。
 * @author milai
 * @date 2021.05.28
 */
public interface MsgList {

	/**
	 * 获取指定 index {@link MsgMap} 值。
	 * 若 index 不存在或不是对象类型，返回 <code>null</code>；
	 * @param index
	 * @return
	 */
	MsgMap asMap(int index);

	/**
	 * 获取指定 index {@link MsgList} 值。
	 * 若 index 不存在或不是数组类型，返回 <code>null</code>
	 * @param index
	 * @return
	 */
	MsgList asList(int index);

	/**
	 * 获取指定 index bool 值
	 * @param index
	 * @return
	 */
	boolean asBool(int index);

	/**
	 * 获取指定 index int 值
	 * @param index
	 * @return
	 */
	int asInt(int index);

	/**
	 * 获取指定 index long 值
	 * @param index
	 * @return
	 */
	long asLong(int index);

	/**
	 * 获取指定 index double 值
	 * @param index
	 * @return
	 */
	double asDouble(int index);

	/**
	 * 获取指定 index String 值，获取失败时返回空字符串
	 * @param index
	 * @return
	 */
	String asString(int index);

	/**
	 * 获取指定 index <T> 类型值
	 * @param <T>
	 * @param int
	 * @param clazz
	 * @return
	 */
	<T> T as(int index, Class<T> clazz);

	/**
	 * 获取当前 {@link MsgList} 的 JSON 字符串格式
	 * @return
	 */
	@Override
	String toString();

}
