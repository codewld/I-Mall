package pers.codewld.imall.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.codewld.imall.admin.model.entity.SmsDict;
import pers.codewld.imall.admin.model.param.SmsDictDetailParam;
import pers.codewld.imall.admin.model.param.SmsDictParam;
import pers.codewld.imall.admin.model.entity.SmsDictDetail;
import pers.codewld.imall.common.model.vo.PageVO;

import java.util.List;

/**
 * <p>
 * 字典 服务类
 * </p>
 *
 * @author codewld
 * @since 2022-03-09
 */
public interface SmsDictService extends IService<SmsDict> {

    /**
     * 添加字典
     */
    boolean add(SmsDictParam smsDictParam);

    /**
     * 删除字典
     */
    boolean del(Long id);

    /**
     * 修改字典
     */
    boolean update(Long id, SmsDictParam smsDictParam);

    /**
     * 分页查询，可搜索
     *
     * @param pageNum  页数
     * @param pageSize 每页条数
     * @param code     编码
     * @param name     名称
     */
    PageVO<SmsDict> page(Integer pageNum, Integer pageSize, String code, String name);

    /**
     * 添加字典细节
     */
    boolean addDetail(SmsDictDetailParam smsDictDetailParam);

    /**
     * 删除字典细节
     */
    boolean delDetail(Long detailId);

    /**
     * 修改字典细节
     */
    boolean updateDetail(Long detailId, SmsDictDetailParam smsDictDetailParam);

    /**
     * 批量查询 [字典细节]
     */
    List<SmsDictDetail> listDetail(Long id);

}
