package pers.codewld.imall.chat.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pers.codewld.imall.chat.model.enums.SystemCode;

/**
 * <p>
 * 用户 实体类
 * </p>
 *
 * @author codewld
 * @since 2022-03-21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    /**
     * 所属系统
     */
    private SystemCode system;

    /**
     * ID
     */
    private Long id;

}
