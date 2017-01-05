package com.jzfq.fms.service.impl;

import com.alibaba.fastjson.JSON;
import com.jzfq.fms.common.enums.JdPayOrderType;
import com.jzfq.fms.common.enums.JdPaySource;
import com.jzfq.fms.common.util.DateEnum;
import com.jzfq.fms.common.util.DateUtils;
import com.jzfq.fms.common.util.ServiceValidate;
import com.jzfq.fms.common.util.payutil.BASE64;
import com.jzfq.fms.common.util.payutil.SignUtil;
import com.jzfq.fms.common.util.payutil.ThreeDesUtil;
import com.jzfq.fms.domain.BasePayOrderInfo;
import com.jzfq.fms.domain.PayLog;
import com.jzfq.fms.manager.PayLogManager;
import com.jzfq.fms.service.IDGeneratorService;
import com.jzfq.fms.service.IJdPayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by huyinglin on 2016/12/6.
 */
@Service
public class JdPayServiceImpl implements IJdPayService {

    private Logger logger= LoggerFactory.getLogger(JdPayServiceImpl.class);

    //支付url
    @Value("${wepay.jd.url}")
    private String wepayUrl;

    //支付成功后跳转的URL
    @Value("${wepay.jd.callbackUrl}")
    private String  callbackUrl;

    //支付完成后，异步通知商户服务相关支付结果
    @Value("${wepay.jd.notifyUrl}")
    private String notifyUrl;

    //京东支付版本号
    @Value("${wepay.jd.version}")
    private String payVersion;

    //京东支付商户号
    @Value("${wepay.jd.merchant}")
    private String merchant;

    //私钥
    @Value("${wepay.jd.merchant.rsaPrivateKey}")
    private String priKey;

    @Value("${wepay.jd.merchant.desKey}")
    private String desKey;

    @Autowired
    private PayLogManager payLogManager;

    @Value("${wepay.jd.tradeName}")
    private String tradeName;

    @Value("${base.url}")
    private String baseUrl;

    @Value("${h5pay.jd.url}")
    private String h5payUrl;

    @Autowired
    private IDGeneratorService idGeneratorService;

    /**
     * 京东支付请求参数组装
     * @param payInfo
     * @return
     */
    @Override
    public ModelAndView pay(BasePayOrderInfo payInfo) {
        //交易金额从商城获取
        ServiceValidate.notNull(payInfo.getAmount(),"交易金额不能为空");
        ServiceValidate.notNull(payInfo.getTradeName(),"订单名称不能为空");
       // ServiceValidate.notNull(payInfo.getTradeNum(),"交易流水号不能为空");
        ServiceValidate.notNull(payInfo.getSource(),"来源不能为空");
        ServiceValidate.notNull(payInfo.getUserId(),"用户ID不能为空");
        ServiceValidate.notNull(payInfo.getOrderId(),"订单编号不能为空");
        ServiceValidate.notNull(payInfo.getPeriod(),"期数不能为空");

        ModelAndView modelAndView=new ModelAndView();
        BasePayOrderInfo basePayOrderInfo = new BasePayOrderInfo();
        basePayOrderInfo.setVersion(payVersion);
        basePayOrderInfo.setMerchant(merchant);
        basePayOrderInfo.setTradeNum(idGeneratorService.generate());//交易流水号自动生成映射支付日志中的订单号
        basePayOrderInfo.setTradeName(payInfo.getTradeName());
        basePayOrderInfo.setTradeTime(DateUtils.dateToStr(new Date(),DateEnum.DATE_BANK_SEQ));
        basePayOrderInfo.setAmount(payInfo.getAmount());
        basePayOrderInfo.setCurrency("CNY");
        basePayOrderInfo.setCallbackUrl(baseUrl+callbackUrl);
        basePayOrderInfo.setNotifyUrl(baseUrl+notifyUrl);
        basePayOrderInfo.setUserId(payInfo.getUserId());
        basePayOrderInfo.setOrderType(String.valueOf(JdPayOrderType.INVENTED.ordinal()));//0-实物，1-虚拟，10-门店订单，11-服务订单，12-美食订单，13-厂直类型订单，14-一般自营类订单，15-开放仓类订单
        List<String> unSignedKeyList = new ArrayList<String>();
        unSignedKeyList.add("sign");
        basePayOrderInfo.setSign(SignUtil.signRemoveSelectedKeys(basePayOrderInfo, priKey, unSignedKeyList));
        try {
            basePayOrderInfo.setOrderId(payInfo.getOrderId());
            basePayOrderInfo.setPeriod(payInfo.getPeriod());
            basePayOrderInfo.setSource(payInfo.getSource());
            payLogManager.addPayLog(getPayLog(basePayOrderInfo));
        } catch (Exception e) {
            logger.error("支付报文保存异常："+e);
        }
        logger.info("签名原串："+JSON.toJSONString(basePayOrderInfo));
        byte[] key = BASE64.decode(desKey);
        basePayOrderInfo.setVersion(basePayOrderInfo.getVersion());
        basePayOrderInfo.setMerchant(basePayOrderInfo.getMerchant());
        basePayOrderInfo.setTradeNum(ThreeDesUtil.encrypt2HexStr(key,basePayOrderInfo.getTradeNum()));
        basePayOrderInfo.setTradeName(ThreeDesUtil.encrypt2HexStr(key,basePayOrderInfo.getTradeName()));
        basePayOrderInfo.setTradeTime(ThreeDesUtil.encrypt2HexStr(key,basePayOrderInfo.getTradeTime()));
        basePayOrderInfo.setAmount(ThreeDesUtil.encrypt2HexStr(key,basePayOrderInfo.getAmount()));
        basePayOrderInfo.setOrderType(ThreeDesUtil.encrypt2HexStr(key,basePayOrderInfo.getOrderType()));//0-实物，1-虚拟，10-门店订单，11-服务订单，12-美食订单，13-厂直类型订单，14-一般自营类订单，15-开放仓类订单
        basePayOrderInfo.setCurrency(ThreeDesUtil.encrypt2HexStr(key,basePayOrderInfo.getCurrency()));
        basePayOrderInfo.setCallbackUrl(ThreeDesUtil.encrypt2HexStr(key,basePayOrderInfo.getCallbackUrl()));
        basePayOrderInfo.setNotifyUrl(ThreeDesUtil.encrypt2HexStr(key,basePayOrderInfo.getNotifyUrl()));
        basePayOrderInfo.setUserId(ThreeDesUtil.encrypt2HexStr(key,basePayOrderInfo.getUserId()));
        basePayOrderInfo.setSign(basePayOrderInfo.getSign());
        logger.info("返回信息basePayOrderInfo:"+JSON.toJSONString(basePayOrderInfo));
        modelAndView.addObject("payOrderInfo", basePayOrderInfo);
        modelAndView.addObject("payUrl", payInfo.getSource().equals(String.valueOf(JdPaySource.PC.ordinal()))?wepayUrl:h5payUrl);
        modelAndView.setViewName("/test/autoSubmit");
        return modelAndView;
    }

    /**
     * 增加支付的请求报文信息
     * @param info
     * @return
     */
    private PayLog getPayLog(BasePayOrderInfo info){
        PayLog payLog=new PayLog();
        payLog.setVersion(info.getVersion());
        payLog.setSign(info.getSign());
        payLog.setMerchant(info.getMerchant());
        payLog.setTradenum(info.getTradeNum());
        payLog.setTradename(info.getTradeName());
        payLog.setTradetime(DateUtils.getNow());
        payLog.setAmount(new BigDecimal(info.getAmount()+""));
        payLog.setOrdertype(info.getOrderType());
        payLog.setCurrency(info.getCurrency());
        payLog.setCallbackurl(info.getCallbackUrl());
        payLog.setNotifyurl(info.getNotifyUrl());
        payLog.setUserid(info.getUserId());
        payLog.setUsertype("BIZ");
        payLog.setOrderid(info.getOrderId());
        payLog.setPeriod(info.getPeriod());
        payLog.setSource(info.getSource());
        return payLog;
    }
}
