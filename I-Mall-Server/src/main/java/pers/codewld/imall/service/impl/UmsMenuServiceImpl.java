package pers.codewld.imall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import pers.codewld.imall.mapper.UmsMenuMapper;
import pers.codewld.imall.model.entity.UmsMenu;
import pers.codewld.imall.model.param.UmsMenuParam;
import pers.codewld.imall.model.vo.UmsMenuMarkVO;
import pers.codewld.imall.service.UmsMenuService;
import pers.codewld.imall.util.TransformUtil;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 后台菜单表 服务实现类
 * </p>
 *
 * @author codewld
 * @since 2022-02-04
 */
@Service
public class UmsMenuServiceImpl extends ServiceImpl<UmsMenuMapper, UmsMenu> implements UmsMenuService {

    @Override
    public boolean add(UmsMenuParam umsMenuParam) {
        UmsMenu umsMenu = TransformUtil.transform(umsMenuParam);
        return this.save(umsMenu);
    }

    @Override
    public boolean del(Long id) {
        return this.removeById(id);
    }

    @Override
    public boolean update(Long id, UmsMenuParam umsMenuParam) {
        UmsMenu umsMenu = TransformUtil.transform(umsMenuParam);
        umsMenu.setId(id);
        return this.updateById(umsMenu);
    }

    @Override
    public List<UmsMenu> listRoot() {
        QueryWrapper<UmsMenu> queryWrapper = new QueryWrapper<UmsMenu>()
                .eq("parent_id", 0);
        return this.list(queryWrapper);
    }

    @Override
    public List<UmsMenu> listSon(Long id) {
        QueryWrapper<UmsMenu> queryWrapper = new QueryWrapper<UmsMenu>()
                .eq("parent_id", id);
        return this.list(queryWrapper);
    }

    @Override
    public List<UmsMenuMarkVO> listRootMark() {
        return this.listRoot().stream().map(TransformUtil::transform).collect(Collectors.toList());
    }

    @Override
    public List<UmsMenuMarkVO> listSonMark(Long id) {
        return this.listSon(id).stream().map(TransformUtil::transform).collect(Collectors.toList());
    }
}
