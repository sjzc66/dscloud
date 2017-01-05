package com.jzfq.fms.rest;

import com.jzfq.fms.common.common.JsonResult;
import com.jzfq.fms.domain.PresentOrder;
import com.jzfq.fms.service.IPresentOrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;


/**
 * Created by zhishuo on 11/9/16.
 */
@RestController
@RequestMapping("/api/presentOrder")
public class PresentOrderApi extends BaseApi{

    @Autowired
    private IPresentOrderService presentOrderService;

    /**
     * 插入提现记录信息
     * @param presentOrder
     * @return
     */
    @RequestMapping(value = "/insert",method = {RequestMethod.POST})
    public JsonResult insertPersentOrder(@RequestBody PresentOrder presentOrder){
        presentOrder.setOrderTime(new Date(System.currentTimeMillis()));
        presentOrder.setRepayDate(new Date(System.currentTimeMillis()));
        presentOrderService.insertPresentOrder(presentOrder);
        return returnSuccess(presentOrder.getOrderId(),"此笔提现已录入成功");
    }


}
