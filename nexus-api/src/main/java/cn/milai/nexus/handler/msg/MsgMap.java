package cn.milai.nexus.handler.msg;

/**
 * 消息 map 参数
 * @author milai
 * @date 2021.05.28
 */
public interface MsgMap {

	/**
	 * 获取指定 key {@link MsgMap} 值，若不存在，返回 null
	 * @param key
	 * @return
	 */
	MsgMap getMap(String key);

	/**
	 * 获取指定 key {@link MsgList} 值，若 key 不存在，返回 null
	 * @param key
	 * @return
	 */
	MsgList getList(String key);

	/**
	 * 获取指定 key {@link Boolean} 值，若不存在，返回 null
	 * @param key
	 * @return
	 */
	Boolean getBoolean(String key);

	/**
	 * 获取指定 key bool 值，若 key 不存在，返回 false
	 * @param key
	 * @return
	 */
	boolean getBooleanValue(String key);

	/**
	 * 获取指定 key byte[] 值，若 key 不存在，返回 null
	 * @param key
	 * @return
	 */
	byte[] getBytes(String key);

	/**
	 * 获取指定 key {@link Integer} 值，若 key 不存在，返回 null
	 * @param key
	 * @return
	 */
	Integer getInteger(String key);

	/**
	 * 获取指定 key int 值，若 key 不存在，返回 0
	 * @param key
	 * @return
	 */
	int getIntValue(String key);

	/**
	 * 获取指定 key {@link Long} 值，若 key 不存在，返回 null
	 * @param key
	 * @return
	 */
	Long getLong(String key);

	/**
	 * 获取指定 key long 值，若 key 不存在，返回 0 
	 * @param key
	 * @return
	 */
	long getLongValue(String key);

	/**
	 * 获取指定 key {@link Float} 值，若 key 不存在，返回 null
	 * @param key
	 * @return
	 */
	Float getFloat(String key);

	/**
	 * 获取指定 key float 值，若 key 不存在，返回 0.0f
	 * @param key
	 * @return
	 */
	float getFloatValue(String key);

	/**
	 * 获取指定 key {@link Double} 值，若 key 不存在，返回 null
	 * @param key
	 * @return
	 */
	Double getDouble(String key);

	/**
	 * 获取指定 key double 值，若 key 不存在，返回 0.0
	 * @param key
	 * @return
	 */
	double getDoubleValue(String key);

	/**
	 * 获取指定 key {@link String} 值，若 key 不存在，返回 null
	 * @param key
	 * @return
	 */
	String getString(String key);

	/**
	 * 获取指定 key <T> 类型值，若 key 不存在，返回 null
	 * @param <T>
	 * @param key
	 * @param clazz
	 * @return
	 */
	<T> T get(String key, Class<T> clazz);
	
	/**
	 * 获取当前 {@link MsgMap} 的 JSON 字符串格式
	 * @return
	 */
	String toJSONString();
}
