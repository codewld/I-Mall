package pers.codewld.imall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.codewld.imall.mapper.SmsDictDetailMapper;
import pers.codewld.imall.mapper.SmsDictMapper;
import pers.codewld.imall.model.entity.SmsDict;
import pers.codewld.imall.model.entity.SmsDictDetail;
import pers.codewld.imall.model.param.SmsDictDetailParam;
import pers.codewld.imall.model.param.SmsDictParam;
import pers.codewld.imall.model.vo.PageVO;
import pers.codewld.imall.service.SmsDictService;
import pers.codewld.imall.util.TransformUtil;

import java.util.List;

/**
 * <p>
 * 字典 服务实现类
 * </p>
 *
 * @author codewld
 * @since 2022-03-09
 */
@Service
public class SmsDictServiceImpl extends ServiceImpl<SmsDictMapper, SmsDict> implements SmsDictService {

    @Autowired
    SmsDictDetailMapper smsDictDetailMapper;

    @Override
    public boolean add(SmsDictParam smsDictParam) {
        SmsDict smsDict = TransformUtil.transform(smsDictParam);
        return this.save(smsDict);
    }

    @Override
    public boolean del(Long id) {
        return this.removeById(id);
    }

    @Override
    public boolean update(Long id, SmsDictParam smsDictParam) {
        SmsDict smsDict = TransformUtil.transform(smsDictParam);
        smsDict.setId(id);
        return this.updateById(smsDict);
    }

    @Override
    public PageVO<SmsDict> page(Integer pageNum, Integer pageSize, String code, String name) {
        QueryWrapper<SmsDict> queryWrapper = new QueryWrapper<SmsDict>()
                .like(code != null, "code", code)
                .like(name != null, "name", name);
        Page<SmsDict> page = this.page(new Page<>(pageNum, pageSize), queryWrapper);
        long total = page.getTotal();
        List<SmsDict> list = page.getRecords();
        return new PageVO<>(total, list);
    }

    @Override
    public boolean addDetail(SmsDictDetailParam smsDictDetailParam) {
        SmsDictDetail smsDictDetail = TransformUtil.transform(smsDictDetailParam);
        return smsDictDetailMapper.insert(smsDictDetail) >= 1;
    }

    @Override
    public boolean delDetail(Long detailId) {
        return smsDictDetailMapper.deleteById(detailId) >= 1;
    }

    @Override
    public boolean updateDetail(Long detailId, SmsDictDetailParam smsDictDetailParam) {
        SmsDictDetail smsDictDetail = TransformUtil.transform(smsDictDetailParam);
        smsDictDetail.setId(detailId);
        return smsDictDetailMapper.updateById(smsDictDetail) >= 1;
    }

    @Override
    public List<SmsDictDetail> listDetail(Long id) {
        QueryWrapper<SmsDictDetail> queryWrapper = new QueryWrapper<SmsDictDetail>()
                .eq("dict_id", id);
        return smsDictDetailMapper.selectList(queryWrapper);
    }
}
