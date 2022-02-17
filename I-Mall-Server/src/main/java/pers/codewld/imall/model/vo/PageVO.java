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
     * 当前页数
     */
    private Long pageNum;

    /**
     * 每页条数
     */
    private Long pageSize;

    /**
     * 总条数
     */
    private Long total;

    /**
     * 数据列表
     */
    private List<T> list;

    public PageVO(Page<T> page) {
        this.pageNum = page.getCurrent();
        this.pageSize = page.getSize();
        this.total = page.getTotal();
        this.list = page.getRecords();
    }

}
