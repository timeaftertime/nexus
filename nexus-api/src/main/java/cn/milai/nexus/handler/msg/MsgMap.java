package cn.milai.nexus.handler.msg;

/**
 * 消息 map 参数。get 值类型的不存在 key 或类型错误时将返回该类型的默认值。
 * @author milai
 * @date 2021.05.28
 */
public interface MsgMap {

	/**
	 * 获取指定 key {@link MsgMap} 值。
	 * 若 key 不存在或不是对象类型，返回 <code>null</code>；
	 * @param key
	 * @return
	 */
	MsgMap asMap(String key);

	/**
	 * 获取指定 key {@link MsgList} 值。
	 * 若 key 不存在或不是数组类型，返回 <code>null</code>
	 * @param key
	 * @return
	 */
	MsgList asList(String key);

	/**
	 * 获取指定 key bool 值，若 key 不存在，返回 false
	 * @param key
	 * @return
	 */
	boolean asBool(String key);

	/**
	 * 获取指定 key int 值，若 key 不存在，返回 0
	 * @param key
	 * @return
	 */
	int asInt(String key);

	/**
	 * 获取指定 key long 值，若 key 不存在，返回 0 
	 * @param key
	 * @return
	 */
	long asLong(String key);

	/**
	 * 获取指定 key double 值，若 key 不存在，返回 0.0
	 * @param key
	 * @return
	 */
	double asDouble(String key);

	/**
	 * 获取指定 key {@link String} 值，若 key 不存在，返回 null
	 * @param key
	 * @return
	 */
	String asString(String key);

	/**
	 * 获取指定 key <T> 类型值，若 key 不存在，返回 null
	 * @param <T>
	 * @param key
	 * @param clazz
	 * @return
	 */
	<T> T as(String key, Class<T> clazz);

	/**
	 * 获取当前 {@link MsgMap} 的 JSON 字符串格式
	 * @return
	 */
	@Override
	String toString();
}
