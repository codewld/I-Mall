package pers.codewld.imall.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

/**
 * <p>
 * MyBatis Plus 配置类
 * </p>
 *
 * @author codewld
 * @since 2022-02-05
 */
@Configuration
public class MyBatisPlusConfig {

    /**
     * 分页插件
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.H2));
        return interceptor;
    }

    /**
     * 字段填充处理器
     */
    @Bean
    MetaObjectHandler metaObjectHandler() {
        return new MetaObjectHandler() {
            @Override
            public void insertFill(MetaObject metaObject) {
                if (metaObject.hasSetter("createTime")) {
                    this.setFieldValByName("createTime", LocalDateTime.now(), metaObject);
                }
                if (metaObject.hasSetter("updateTime")) {
                    this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
                }
            }

            @Override
            public void updateFill(MetaObject metaObject) {
                if (metaObject.hasSetter("updateTime")) {
                    this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
                }
            }
        };
    }

}
