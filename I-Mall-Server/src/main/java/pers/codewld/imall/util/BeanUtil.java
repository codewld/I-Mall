package pers.codewld.imall.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * <p>
 * Bean 工具类
 * </p>
 * <p>
 * 描述：帮助（未被Spring托管的对象）获取Spring容器中的Bean
 * </p>
 *
 * @author codewld
 * @since 2022-02-11
 */
@Component
public class BeanUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        BeanUtil.applicationContext = applicationContext;
    }

    public static <T> T getBean(Class<T> requiredType){
        return applicationContext.getBean(requiredType);
    }
}
