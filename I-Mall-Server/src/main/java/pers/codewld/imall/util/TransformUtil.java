package pers.codewld.imall.util;

import pers.codewld.imall.model.entity.UmsAdmin;
import pers.codewld.imall.model.param.UmsAdminParam;
import pers.codewld.imall.model.vo.UmsAdminVO;

import java.time.LocalDateTime;

/**
 * <p>
 * 结构体转换工具类
 * </p>
 * <p>
 * 描述：帮助结构体之间进行转换 [entity -> VO; param -> entity]
 * </p>
 *
 * @author codewld
 * @since 2022-02-21
 */
public class TransformUtil {

    public static UmsAdminVO transform(UmsAdmin umsAdmin) {
        return new UmsAdminVO(umsAdmin.getId(), umsAdmin.getUsername(), umsAdmin.getEmail(), umsAdmin.getNickName(), umsAdmin.getNote(), umsAdmin.getCreateTime(), umsAdmin.getUpdateTime(), umsAdmin.getLoginTime(), umsAdmin.getStatus());
    }

    public static UmsAdmin transform(UmsAdminParam UmsAdminParam) {
        return new UmsAdmin(UmsAdminParam.getUsername(), UmsAdminParam.getPassword(), UmsAdminParam.getEmail(), UmsAdminParam.getNickName(), UmsAdminParam.getNote(), UmsAdminParam.getStatus());
    }

}
