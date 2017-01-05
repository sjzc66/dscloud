package com.jzfq.fms.service.impl;

import com.jzfq.fms.common.common.PageVo;
import com.jzfq.fms.common.util.ServiceValidate;
import com.jzfq.fms.dao.ProductMapper;
import com.jzfq.fms.domain.Product;
import com.jzfq.fms.interceptor.PageList;
import com.jzfq.fms.service.IProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

import static com.jzfq.fms.common.util.JdbcUtil.isSuccess;

/**
 * Created by zhishuo on 11/15/16.
 */
@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<Product> getProducts(){
        return productMapper.getProducts();
    }

    @Override
    public PageList<Product> getMonthProductsList(PageVo vo) {
        return productMapper.getMonthProductList(vo,vo.getPageable());
    }

    @Override
    public boolean updateProduct(Product product) {
        //如果id为空 或者对象为空
        if (StringUtils.isEmpty(product) || StringUtils.isEmpty(product.getId())){
            ServiceValidate.isTrue(Boolean.FALSE,"产品信息不能为空");
        }
        product.setUpdateTime((new Date(System.currentTimeMillis())));
        int result = productMapper.updateByPrimaryKeySelective(product);
        boolean state = isSuccess(result);
        ServiceValidate.isTrue(state,"更新失败");
        return state;

    }

    @Override
    public Product addProduct(Product product) {
        ServiceValidate.isTrue(!StringUtils.isEmpty(product),"产品信息为空");
        product.setCreateTime(new Date(System.currentTimeMillis()));
        product.setIsDeleted(0);
        //if (product.getParentName().equals("随手借"))
        switch (product.getParentName()){
            case "随手借":
                product.setParentId(1);
                product.setApplyProduct(1);
                break;
            case "白条":
                product.setParentId(2);
                product.setApplyProduct(2);
                break;
            case "商城":
                product.setParentId(3);
                product.setApplyProduct(3);
                break;
        }
        productMapper.insertSelective(product);
        return product;
    }

    @Override
    public boolean delProduct(int productId) {
        int result = productMapper.deleteByPrimaryKey(productId);
        return result == 1;
    }

    @Override
    public Product getProductById(int id) {
        return productMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageList<Product> getDayProductsList(PageVo vo) {
        return productMapper.getDayProductList(vo,vo.getPageable());
    }
}
