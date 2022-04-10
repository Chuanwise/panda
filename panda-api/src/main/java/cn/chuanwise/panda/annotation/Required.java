package cn.chuanwise.panda.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 让指令执行前检查权限的注解
 *
 * @author Chuanwise
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Required {
    
    /**
     * 执行指令所需的权限
     *
     * @return 执行指令所需的权限
     */
    String value();
    
    /**
     * 缺少权限时的提示
     *
     * @return 缺少权限时的提示
     */
    String message() default "";
}