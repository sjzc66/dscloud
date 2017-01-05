package com.jzfq.fms.controller;

import com.alibaba.fastjson.JSON;
import com.jzfq.fms.common.enums.JdPaySource;
import com.jzfq.fms.common.util.DateEnum;
import com.jzfq.fms.common.util.DateUtils;
import com.jzfq.fms.common.util.payutil.*;
import com.jzfq.fms.domain.BasePayOrderInfo;
import com.jzfq.fms.domain.PayLog;
import com.jzfq.fms.manager.PayLogManager;
import com.jzfq.fms.service.IDGeneratorService;
import com.jzfq.fms.service.IJdPayService;
import com.jzfq.fms.vo.jdpay.AsynNotifyResponse;
import com.jzfq.fms.vo.jdpay.TradeResultResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import sun.misc.BASE64Decoder;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by huyinglin on 2016/12/8.
 */
@Controller
@RequestMapping("/test")
public class testController {

    private Logger logger= LoggerFactory.getLogger(testController.class);

    @Autowired
    private IDGeneratorService idGeneratorService;

    @Autowired
    private PayLogManager payLogManager;

    @Autowired
    private IJdPayService IJdPayService;

    //支付url
    @Value("${wepay.jd.url}")
    private String payUrl;

    //京东支付商户号
    @Value("${wepay.jd.merchant}")
    private String merchant;

    //私钥
    @Value("${wepay.jd.merchant.rsaPrivateKey}")
    private String priKey;

    @Value("${wepay.jd.merchant.desKey}")
    private String desKey;

    @Value("wepay.jd.merchant.rsaPublicKey")
    private String pubKey;

    @Resource
    private HttpServletRequest request;

    //支付成功返回成功标记
    private static final String SUCCESS_PAGE_STRING="ok";
    //支付失败返回失败标记
    private static final String FAIL_PAGE_STRING="fail";

    @RequestMapping("/tolist")
    public ModelAndView tolist(){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addObject("merchant","22294531");
        modelAndView.addObject("tradeTime", DateUtils.dateToStr(new Date(), DateEnum.DATE_BANK_SEQ));
        modelAndView.setViewName("/test/payStart");

        return modelAndView;
    }

    @RequestMapping("/toadd")
    public ModelAndView toadd(){
        ModelAndView modelAndView=new ModelAndView();

        BasePayOrderInfo basePayOrderInfo = new BasePayOrderInfo();
        basePayOrderInfo.setVersion("V2.0");
        basePayOrderInfo.setMerchant("22294531");
        basePayOrderInfo.setTradeNum("1481249194632");
        basePayOrderInfo.setTradeName("商品");
        basePayOrderInfo.setTradeTime(DateUtils.dateToStr(new Date(),DateEnum.DATE_BANK_SEQ));
        basePayOrderInfo.setAmount("1");
        basePayOrderInfo.setCurrency("CNY");
        basePayOrderInfo.setCallbackUrl("http://jdpaydemo.jd.com/success.htm");
        basePayOrderInfo.setNotifyUrl("http://jdpaydemo.jd.com/asynNotify.htm");
        basePayOrderInfo.setUserId(idGeneratorService.generate());
        basePayOrderInfo.setOrderType("1");
        List<String> unSignedKeyList = new ArrayList<String>();
        unSignedKeyList.add("sign");
        basePayOrderInfo.setSign(SignUtil.signRemoveSelectedKeys(basePayOrderInfo, priKey, unSignedKeyList));

        try {
            payLogManager.addPayLog(getPayLog(basePayOrderInfo));
        } catch (Exception e) {
            System.out.print("支付报文保存异常："+e);
        }
        System.out.print("签名原串："+JSON.toJSONString(basePayOrderInfo));

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
        System.out.print("==返回信息:=="+JSON.toJSONString(basePayOrderInfo));
        modelAndView.addObject("payOrderInfo", basePayOrderInfo);
        modelAndView.addObject("payUrl", "https://wepay.jd.com/jdpay/saveOrder");
        modelAndView.setViewName("/test/autoSubmit");
        return modelAndView;
    }


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
        payLog.setUsertype(info.getUserType()==null?"BIZ":info.getUserType());
        return payLog;
    }

    @RequestMapping("/topaylist")
    public ModelAndView toList(){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("test/list");
        return modelAndView;
    }
    @RequestMapping("/gotoJDpay")
    public ModelAndView jdPayTest(){
        BasePayOrderInfo payOrderInfo=new BasePayOrderInfo();
        payOrderInfo.setAmount("1");//交易金额
        payOrderInfo.setUserId("1234560");//用户编号
        payOrderInfo.setSource(String.valueOf(JdPaySource.MOBILE.ordinal()));
        payOrderInfo.setTradeName("桔子分期-产品支付");//交易名称
        payOrderInfo.setPeriod("2");
        payOrderInfo.setOrderId("2123231");
        return IJdPayService.pay(payOrderInfo);
    }


    @RequestMapping("/success")
    public String success(ModelMap map, HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            System.out.println("接收页面回调成功请求开始" + "-----------------");
            TradeResultResponse tradeResultRes = new TradeResultResponse();
            byte[] key = BASE64.decode(desKey);
            String tradeNum = request.getParameter("tradeNum");
            tradeResultRes.setTradeNum(ThreeDesUtil.decrypt4HexStr(key, tradeNum));
            String tradeAmount = request.getParameter("amount");
            tradeResultRes.setAmount(ThreeDesUtil.decrypt4HexStr(key, tradeAmount));
            String tradeCurrency = request.getParameter("currency");
            tradeResultRes.setCurrency(ThreeDesUtil.decrypt4HexStr(key, tradeCurrency));
            String tradeDate = request.getParameter("tradeTime");
            tradeResultRes.setTradeTime(ThreeDesUtil.decrypt4HexStr(key, tradeDate));
            String tradeNote = request.getParameter("note");
            tradeResultRes.setNote(ThreeDesUtil.decrypt4HexStr(key, tradeNote));
            String tradeStatus = request.getParameter("status");
            tradeResultRes.setStatus(ThreeDesUtil.decrypt4HexStr(key, tradeStatus));
            String sign = request.getParameter("sign");
            System.out.println("sign:" + sign);
            String strSourceData = SignUtil.signString(tradeResultRes, new ArrayList<String>());
            System.out.println("strSourceData:" + strSourceData);
            byte[] decryptBASE64Arr = new BASE64Decoder().decodeBuffer(sign);
            byte[] decryptArr = RSACoder.decryptByPublicKey(decryptBASE64Arr, pubKey);
            String decryptStr = new String(decryptArr, "UTF-8");
            System.out.println("decryptStr:" + decryptStr);
            String sha256SourceSignString = SHAUtil.Encrypt(strSourceData, null);
            System.out.println("sha256SourceSignString:" + sha256SourceSignString);
            System.out.println(decryptStr + "|" + sha256SourceSignString);
            if (!decryptStr.equals(sha256SourceSignString)) {
                request.setAttribute("errorMsg", "验证签名失败！");
                return "fail";
            } else {
                System.out.println("接收页面回调成功------" + tradeResultRes);
                request.setAttribute("tradeResultRes", tradeResultRes);
                return "http://www.baidu.com";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "http://www.google.com.cn";
        }
    }

    /**
     *
     * @Title: execute
     * @Description: 异步通知受理
     * @param @param httpServletRequest
     * @param @return 设定文件
     * @return String 返回类型
     * @throws
     */
    @RequestMapping(value = "/asynNotify.htm", method = RequestMethod.POST)
    public String execute(HttpServletRequest httpServletRequest) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream()));
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            logger.info("异步通知原始数据:" + sb);
        } catch (IOException e) {
            logger.error("异步通知原始数据异常:" + e);
            return "fail";
        }

        try {
            AsynNotifyResponse anRes = JdPayUtil.parseResp(pubKey, desKey, sb.toString(), AsynNotifyResponse.class);
            logger.info("异步通知解析数据:" + anRes);
            logger.info("异步通知订单号：" + anRes.getTradeNum() + ",状态：" + anRes.getStatus() + "成功!!!!");

        } catch (Exception e) {
            logger.error(e.getMessage());
            return "fail";
        }
        return SUCCESS_PAGE_STRING;
    }



}
