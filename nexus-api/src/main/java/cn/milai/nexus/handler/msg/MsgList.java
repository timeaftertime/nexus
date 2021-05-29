package cn.milai.nexus.handler.msg;

/**
 * 消息 List 参数
 * @author milai
 * @date 2021.05.28
 */
public interface MsgList {

	/**
	 * 获取指定 index {@link MsgMap} 值
	 * @param index
	 * @return
	 */
	MsgMap getMap(int index);

	/**
	 * 获取指定 index {@link MsgList} 值
	 * @param index
	 * @return
	 */
	MsgList getList(int index);

	/**
	 * 获取指定 index {@link Boolean} 值
	 * @param index
	 * @return
	 */
	Boolean getBoolean(int index);

	/**
	 * 获取指定 index bool 值
	 * @param index
	 * @return
	 */
	boolean getBooleanValue(int index);

	/**
	 * 获取指定 index {@link Integer} 值
	 * @param index
	 * @return
	 */
	Integer getInteger(int index);

	/**
	 * 获取指定 index int 值
	 * @param index
	 * @return
	 */
	int getIntValue(int index);

	/**
	 * 获取指定 index {@link Long} 值
	 * @param index
	 * @return
	 */
	Long getLong(int index);

	/**
	 * 获取指定 index long 值
	 * @param index
	 * @return
	 */
	long getLongValue(int index);

	/**
	 * 获取指定 index {@link Float} 值
	 * @param index
	 * @return
	 */
	Float getFloat(int index);

	/**
	 * 获取指定 index float 值
	 * @param index
	 * @return
	 */
	float getFloatValue(int index);

	/**
	 * 获取指定 index {@link Double} 值
	 * @param index
	 * @return
	 */
	Double getDouble(int index);

	/**
	 * 获取指定 index double 值
	 * @param index
	 * @return
	 */
	double getDoubleValue(int index);

	/**
	 * 获取指定 index {@link String} 值
	 * @param index
	 * @return
	 */
	String getString(int index);

	/**
	 * 获取指定 index <T> 类型值
	 * @param <T>
	 * @param int
	 * @param clazz
	 * @return
	 */
	<T> T get(int index, Class<T> clazz);

	/**
	 * 获取当前 {@link MsgList} 的 JSON 字符串格式
	 * @return
	 */
	String toJSONString();

}
