package cn.milai.nexus.util;

import java.lang.reflect.Method;

/**
 * Method 相关工具类
 * @author milai
 * @date 2021.01.01
 */
public class Methods {

	private Methods() {
	}

	/**
	 * 获取方法的完整签名字符串，即 完整类名.方法名
	 * @param m
	 * @return
	 */
	public static String fullSignature(Method m) {
		return m.getDeclaringClass().getName() + "." + m.getName();
	}
}
