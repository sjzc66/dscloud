package com.jzfq.fms.service.impl;

import com.jzfq.AbstractTest;
import com.jzfq.fms.domain.Product;
import com.jzfq.fms.service.IProductService;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by zhishuo on 11/15/16.
 */

public class ProductServiceImplTest extends AbstractTest {
    
    @Autowired
    private IProductService productService;
    
    @Test
    public void getProducts() throws Exception {
        List<Product> products = productService.getProducts();
        System.out.println(products);
    }

}