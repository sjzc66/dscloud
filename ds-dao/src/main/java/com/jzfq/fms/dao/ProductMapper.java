package com.jzfq.fms.dao;

import com.jzfq.fms.common.common.PageVo;
import com.jzfq.fms.common.common.Pageable;
import com.jzfq.fms.domain.Product;

import com.jzfq.fms.interceptor.PageList;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    Product getProduct(@Param("applyProduct") int applyProduct,
                       @Param("period") int period);

    /**
     * 获取所有的产品列表
     *
     * @return
     */
    List<Product> getProducts();

    /**
     * 分页获取产品列表list
     *
     */
    PageList<Product> getMonthProductList(@Param("vo") PageVo vo, Pageable pageable);

    PageList<Product> getDayProductList(@Param("vo") PageVo vo, Pageable pageable);

}