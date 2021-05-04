package cn.milai.nexus.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

/**
 * 使当前类中声明的 {@link MsgExceptionHandler} 生效 
 * @author milai
 * @date 2021.05.03
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface MsgControllerAdvice {

}
