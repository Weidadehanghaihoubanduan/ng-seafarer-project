package com.bestpay.seafarer.common.page;

import lombok.Data;
import com.github.pagehelper.PageInfo;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: SeafarerJia
 * @Date: 2023/12/15 14:29
 */
@Data
@SuppressWarnings("ALL")
public class CPage<T> implements Serializable {

    private List<T> list;
    private Integer pageNum;
    private Integer pageSize;
    private Integer totalPage;
    private Long total;

    private static final long serialVersionUID = -2760995104227911189L;

    /**
     * @param list 分页对象
     * @param <T> 泛型T对象
     * @return 将PageHelper分页后的list转为分页信息
     */
    public static <T> CPage<T> restPage(List<T> list) {
        CPage<T> result = new CPage<T>();
        PageInfo<T> pageInfo = new PageInfo<T>(list);
        result.setTotalPage(pageInfo.getPages());
        result.setPageNum(pageInfo.getPageNum());
        result.setPageSize(pageInfo.getPageSize());
        result.setTotal(pageInfo.getTotal());
        result.setList(pageInfo.getList());
        return result;
    }

    /**
     * @param pageInfo 分页信息
     * @param <T> 泛型T对象
     * @return 将SpringData分页后的list转为分页信息
     */
    public static <T> CPage<T> restPage(Page<T> pageInfo) {
        CPage<T> result = new CPage<T>();
        result.setTotalPage(pageInfo.getTotalPages());
        result.setPageNum(pageInfo.getNumber());
        result.setPageSize(pageInfo.getSize());
        result.setTotal(pageInfo.getTotalElements());
        result.setList(pageInfo.getContent());
        return result;
    }
}
