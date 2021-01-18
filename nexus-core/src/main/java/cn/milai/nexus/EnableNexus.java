package cn.milai.nexus;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

/**
 * 启动 Nexus 的 SpringBoot 开关
 * @author milai
 * @date 2021.01.14
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(NexusConfig.class)
public @interface EnableNexus {

}
