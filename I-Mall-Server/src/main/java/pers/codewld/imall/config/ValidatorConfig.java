package pers.codewld.imall.config;

import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.groups.Default;

/**
 * <p>
 * Validator 配置类
 * </p>
 *
 * @author codewld
 * @since 2022-02-15
 */
@Configuration
public class ValidatorConfig {

    @Bean
    public Validator validator() {
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
                .configure()
                // 开启快速失败模式 [有一项校验失败，便终止校验、抛出异常]
                .failFast(true)
                .buildValidatorFactory();
        return validatorFactory.getValidator();
    }

    /**
     * 校验分组
     */
    public interface Group extends Default {
        interface add extends Group {
        }

        interface update extends Group {
        }
    }

}
