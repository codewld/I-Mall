package pers.codewld.imall.chat.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 所属系统 枚举类
 * </p>
 *
 * @author codewld
 * @since 2022-03-21
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum SystemCode {

    ADMIN("ADMIN"),

    WEB("WEB");

    private String name;

}
