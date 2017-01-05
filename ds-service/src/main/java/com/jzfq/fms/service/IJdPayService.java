package com.jzfq.fms.service;

import com.jzfq.fms.domain.BasePayOrderInfo;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by huyinglin on 2016/12/12.
 */
public interface IJdPayService{
    /**
     * 京东支付请求
     * @param payInfo 请求参数
     * @return
     */
    ModelAndView pay(BasePayOrderInfo payInfo);
}
