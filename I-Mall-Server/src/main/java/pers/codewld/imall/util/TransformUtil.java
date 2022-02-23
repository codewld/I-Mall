package pers.codewld.imall.util;

import pers.codewld.imall.model.entity.UmsAdmin;
import pers.codewld.imall.model.entity.UmsRole;
import pers.codewld.imall.model.param.UmsAdminParam;
import pers.codewld.imall.model.param.UmsRoleParam;
import pers.codewld.imall.model.vo.UmsAdminVO;
import pers.codewld.imall.model.vo.UmsRoleMarkVO;
import pers.codewld.imall.security.MD5PasswordEncoder;

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
        UmsAdminVO umsAdminVO = new UmsAdminVO();
        umsAdminVO.setId(umsAdmin.getId());
        umsAdminVO.setUsername(umsAdmin.getUsername());
        umsAdminVO.setStatus(umsAdmin.getStatus());
        umsAdminVO.setNickName(umsAdmin.getNickName());
        umsAdminVO.setEmail(umsAdmin.getEmail());
        umsAdminVO.setNote(umsAdmin.getNote());
        umsAdminVO.setCreateTime(umsAdmin.getCreateTime());
        umsAdminVO.setUpdateTime(umsAdmin.getUpdateTime());
        umsAdminVO.setLoginTime(umsAdmin.getLoginTime());
        return umsAdminVO;
    }

    public static UmsAdmin transform(UmsAdminParam UmsAdminParam) {
        String rawPassword = UmsAdminParam.getPassword();
        MD5PasswordEncoder md5PasswordEncoder = BeanUtil.getBean(MD5PasswordEncoder.class);
        String password = null;
        if (rawPassword != null) {
            password = md5PasswordEncoder.encode(rawPassword);
        }
        UmsAdmin umsAdmin = new UmsAdmin();
        umsAdmin.setUsername(UmsAdminParam.getUsername());
        umsAdmin.setPassword(password);
        umsAdmin.setStatus(UmsAdminParam.getStatus());
        umsAdmin.setNickName(UmsAdminParam.getNickName());
        umsAdmin.setEmail(UmsAdminParam.getEmail());
        umsAdmin.setNote(UmsAdminParam.getNote());
        return umsAdmin;
    }

    public static UmsRole transform(UmsRoleParam umsRoleParam) {
        UmsRole umsRole = new UmsRole();
        umsRole.setName(umsRoleParam.getName());
        umsRole.setNote(umsRoleParam.getNote());
        return umsRole;
    }

    public static UmsRoleMarkVO transform(UmsRole umsRole) {
        UmsRoleMarkVO umsRoleMarkVO = new UmsRoleMarkVO();
        umsRoleMarkVO.setId(umsRole.getId());
        umsRoleMarkVO.setName(umsRole.getName());
        return umsRoleMarkVO;
    }

}
