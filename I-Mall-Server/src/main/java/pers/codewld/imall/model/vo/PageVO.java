package pers.codewld.imall.model.vo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import java.util.List;

/**
 * <p>
 * 分页数据VO类
 * </p>
 *
 * @author codewld
 * @since 2022-02-17
 */
@Data
public class PageVO<T> {

    /**
     * 总条数
     */
    private Long total;

    /**
     * 数据列表
     */
    private List<T> list;

    public PageVO(Page<T> page) {
        this.total = page.getTotal();
        this.list = page.getRecords();
    }

    public PageVO(Long total, List<T> list) {
        this.total = total;
        this.list = list;
    }

}
