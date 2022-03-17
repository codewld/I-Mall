package pers.codewld.imall.log.service;

import pers.codewld.imall.common.model.vo.PageVO;
import pers.codewld.imall.log.model.entity.ControllerLog;

/**
 * <p>
 * 接口日志 服务类
 * </p>
 *
 * @author codewld
 * @since 2022-03-16
 */
public interface ControllerLogService {

    /**
     * 添加
     */
    boolean add(ControllerLog controllerLog);

    /**
     * 分页查询，可搜索
     *
     * @param pageNum  页数
     * @param pageSize 每页条数
     * @param summary  接口概述
     * @param username 用户名
     * @param status   执行状态
     */
    PageVO<ControllerLog> page(Integer pageNum, Integer pageSize, String summary, String username, Boolean status);

}
