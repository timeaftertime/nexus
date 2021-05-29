package cn.milai.nexus.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 声明一个方法用于处理范围内指定类型的异常
 * 声明在 {@link MsgController} 中时仅用于当前 {@link MsgController}
 * 声明在 {@link MsgControllerAdvice} 中时用于全局
 * @author milai
 * @date 2021.05.03
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExceptionHandler {

	/**
	 * 处理的异常类型
	 * @return
	 */
	Class<? extends Exception>[] value() default Exception.class;

}
