package pers.codewld.imall.util;

import pers.codewld.imall.model.entity.*;
import pers.codewld.imall.model.param.*;
import pers.codewld.imall.model.vo.RouterVO;
import pers.codewld.imall.model.vo.UmsAdminVO;
import pers.codewld.imall.model.vo.UmsMenuMarkVO;
import pers.codewld.imall.model.vo.UmsRoleMarkVO;
import pers.codewld.imall.security.MD5PasswordEncoder;

import java.util.stream.Collectors;

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
        umsAdminVO.setNickName(umsAdmin.getNickName());
        umsAdminVO.setEmail(umsAdmin.getEmail());
        umsAdminVO.setStatus(umsAdmin.getStatus());
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
        umsAdmin.setNickName(UmsAdminParam.getNickName());
        umsAdmin.setEmail(UmsAdminParam.getEmail());
        umsAdmin.setStatus(UmsAdminParam.getStatus());
        umsAdmin.setNote(UmsAdminParam.getNote());
        return umsAdmin;
    }

    public static UmsRole transform(UmsRoleParam umsRoleParam) {
        UmsRole umsRole = new UmsRole();
        umsRole.setCode(umsRoleParam.getCode());
        umsRole.setName(umsRoleParam.getName());
        umsRole.setNote(umsRoleParam.getNote());
        return umsRole;
    }

    public static UmsRoleMarkVO transform(UmsRole umsRole) {
        UmsRoleMarkVO umsRoleMarkVO = new UmsRoleMarkVO();
        umsRoleMarkVO.setId(umsRole.getId());
        umsRoleMarkVO.setCode(umsRole.getCode());
        umsRoleMarkVO.setName(umsRole.getName());
        return umsRoleMarkVO;
    }

    public static UmsMenu transform(UmsMenuParam umsMenuParam) {
        UmsMenu umsMenu = new UmsMenu();
        umsMenu.setParentId(umsMenuParam.getParentId());
        umsMenu.setCode(umsMenuParam.getCode());
        umsMenu.setName(umsMenuParam.getName());
        umsMenu.setSort(umsMenuParam.getSort());
        umsMenu.setNonLeaf(umsMenuParam.getNonLeaf());
        umsMenu.setComponent(umsMenuParam.getComponent());
        umsMenu.setPath(umsMenuParam.getPath());
        umsMenu.setNote(umsMenuParam.getNote());
        return umsMenu;
    }

    public static UmsMenuMarkVO transform2Mark(UmsMenu umsMenu) {
        UmsMenuMarkVO umsMenuMarkVO = new UmsMenuMarkVO();
        umsMenuMarkVO.setId(umsMenu.getId());
        umsMenuMarkVO.setName(umsMenu.getName());
        if (umsMenu.getChildren() != null) {
            umsMenuMarkVO.setChildren(
                    umsMenu.getChildren()
                            .stream()
                            .map(TransformUtil::transform2Mark)
                            .collect(Collectors.toList()));
        }
        return umsMenuMarkVO;
    }

    public static RouterVO transform2Router(UmsMenu umsMenu) {
        RouterVO routerVO = new RouterVO();
        routerVO.setCode(umsMenu.getCode());
        routerVO.setName(umsMenu.getName());
        routerVO.setComponent(umsMenu.getComponent());
        routerVO.setPath(umsMenu.getPath());
        if (umsMenu.getChildren() != null) {
            routerVO.setChildren(
                    umsMenu.getChildren()
                            .stream()
                            .map(TransformUtil::transform2Router)
                            .collect(Collectors.toList()));
        }
        return routerVO;
    }

    public static SmsDict transform(SmsDictParam smsDictParam) {
        SmsDict smsDict = new SmsDict();
        smsDict.setCode(smsDictParam.getCode());
        smsDict.setName(smsDictParam.getName());
        smsDict.setNote(smsDictParam.getNote());
        return smsDict;
    }

    public static SmsDictDetail transform(SmsDictDetailParam smsDictDetailParam) {
        SmsDictDetail smsDictDetail = new SmsDictDetail();
        smsDictDetail.setDictId(smsDictDetailParam.getDictId());
        smsDictDetail.setValue(smsDictDetailParam.getValue());
        smsDictDetail.setLabel(smsDictDetailParam.getLabel());
        return smsDictDetail;
    }
}
