package cn.milai.nexus.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;

/**
 * 消息处理器（方法）
 * @author milai
 * @date 2021.01.01
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MsgMapping {

	/**
	 * 消息处理方法所处理的 code 值
	 * @return
	 */
	int[] code() default {};

	/**
	 * 消息处理方法所处理的 code 值
	 * @return
	 */
	@AliasFor("code")
	int[] value() default {};
}
