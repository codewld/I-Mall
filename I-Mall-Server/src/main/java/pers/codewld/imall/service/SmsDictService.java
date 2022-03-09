package pers.codewld.imall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.codewld.imall.model.entity.SmsDict;
import pers.codewld.imall.model.param.SmsDictParam;
import pers.codewld.imall.model.vo.PageVO;

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
     * @param code 编码
     * @param name 名称
     */
    PageVO<SmsDict> page(Integer pageNum, Integer pageSize, String code, String name);

}
