package com.jzfq.fms.service;

import com.jzfq.fms.common.common.PageVo;
import com.jzfq.fms.domain.Product;
import com.jzfq.fms.interceptor.PageList;

import java.util.List;

/**
 * Created by zhishuo on 11/15/16.
 */
public interface IProductService {

    List<Product> getProducts();

    /**
     * 分页查询月息产品列表
     * @param vo
     * @return
     */
    PageList<Product> getMonthProductsList(PageVo vo);

    /**
     * 更新产品记录
     * @param product
     * @return
     */
    boolean updateProduct(Product product);

    /**
     * 添加产品
     * @param product
     * @return
     */
    Product addProduct(Product product);

    /**
     * 删除产品
     * @param id
     * @return
     */
    boolean delProduct(int id);

    /**
     * 根据id查询产品信息
     * @param id
     * @return
     */
    Product getProductById(int id);

    /**
     * 分页查询日息产品列表
     * @param vo
     * @return
     */
    PageList<Product> getDayProductsList(PageVo vo);
}
