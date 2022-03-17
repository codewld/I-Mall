package pers.codewld.imall.log.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * 禁用日志的数据库存储 注解
 * </p>
 *
 * @author codewld
 * @since 2022-03-17
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DisableLogDBStorage {

    /**
     * 发生错误时，不再禁用
     */
    boolean enableWhenError() default false;

}
