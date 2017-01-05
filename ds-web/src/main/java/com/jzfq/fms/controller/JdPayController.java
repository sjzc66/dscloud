package com.jzfq.fms.controller;

import com.jzfq.fms.domain.BasePayOrderInfo;
import com.jzfq.fms.service.IJdPayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * Created by liu on 2016/12/19.
 */
@Controller
@RequestMapping("/jdPay")
public class JdPayController {

    private Logger logger = LoggerFactory.getLogger(JdPayController.class);

    @Autowired
    private IJdPayService IJdPayService;

    /**
     * 京东支付
     * @param
     * @return
     */
    @RequestMapping(value = "/gotojdpay")
    public ModelAndView jdPay(HttpServletRequest request){
        BasePayOrderInfo payOrderInfo=new BasePayOrderInfo();
        payOrderInfo.setAmount(request.getParameter("amount"));
        payOrderInfo.setUserId(request.getParameter("userId"));
        String tradeName= "";
        try {
            tradeName=new String(request.getParameter("tradeName").getBytes("iso-8859-1"),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            logger.error("订单名称转码异常！"+e);
        }
        payOrderInfo.setTradeName(tradeName);
        payOrderInfo.setSource(request.getParameter("source"));
        payOrderInfo.setOrderId(request.getParameter("orderId"));
        payOrderInfo.setPeriod(request.getParameter("period"));
        return IJdPayService.pay(payOrderInfo);
    }
}
