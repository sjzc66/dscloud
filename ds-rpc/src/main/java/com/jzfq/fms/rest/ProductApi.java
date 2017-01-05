package com.jzfq.fms.rest;


import com.jzfq.fms.common.common.JsonResult;
import com.jzfq.fms.service.IProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by zhishuo on 11/9/16.
 */
@RestController
@RequestMapping("/api/product")
public class ProductApi extends BaseApi {

    @Autowired
    private IProductService productService;

    /**
     * 获取产品列表
     * @return
     */
    @RequestMapping(value = "/getproducts")
    public JsonResult getProducts() {
        return returnSuccess(productService.getProducts());
    }


}
