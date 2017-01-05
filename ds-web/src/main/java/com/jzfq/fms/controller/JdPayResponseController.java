package com.jzfq.fms.controller;

import com.jzfq.fms.common.enums.RepayState;
import com.jzfq.fms.common.util.StringUtil;
import com.jzfq.fms.common.util.payutil.JdPayUtil;
import com.jzfq.fms.domain.CallbackLog;
import com.jzfq.fms.domain.PayLog;
import com.jzfq.fms.domain.RepayPlan;
import com.jzfq.fms.manager.RepayPlanManager;
import com.jzfq.fms.service.IJdResponseService;
import com.jzfq.fms.vo.jdpay.AsynNotifyResponse;
import com.jzfq.fms.vo.jdpay.PayTradeVo;
import com.jzfq.fms.vo.jdpay.TradeResultResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by huyinglin on 2016/12/11.
 * 京东支付回调
 */
@Controller
@RequestMapping("/jdPayResponse")
public class JdPayResponseController {

    private Logger logger= LoggerFactory.getLogger(JdPayResponseController.class);

    @Autowired
    private IJdResponseService jdResponseService;
    @Value("${paySuccessUrl}")
    private String paySuccessUrl;
    //跳转PHP支付失败页面
    @Value("${payFailUrl}")
    private String payFailUrl;
    //支付成功返回成功标记
    private static final String SUCCESS_PAGE_STRING="ok";
    //支付失败返回失败标记
    private static final String FAIL_PAGE_STRING="fail";

    NumberFormat nf = new DecimalFormat("##.##");

    @Value("${wepay.jd.merchant.desKey}")
    private String desKey;
    @Value("${wepay.jd.merchant.rsaPublicKey}")
    private String pubKey;

    @Autowired
    private RepayPlanManager repayPlanManager;



    /**
     * 支付成功后跳转的页面
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/success")
    public String success(HttpServletRequest request, HttpServletResponse response,Model model) throws Exception {
            Map<String,String> requestMap = new HashMap<>();
            logger.info("=====支付成功回调请求开始=====");
            requestMap.put("tradeNum", request.getParameter("tradeNum"));
            requestMap.put("tradeAmount", request.getParameter("amount"));
            requestMap.put("tradeCurrency", request.getParameter("currency"));
            requestMap.put("tradeDate", request.getParameter("tradeTime"));
            requestMap.put("tradeStatus", request.getParameter("status"));
            requestMap.put("sign", request.getParameter("sign"));
            if (StringUtil.checkNotEmpty(request.getParameter("note"))){
                requestMap.put("tradeNote", request.getParameter("note"));
            }
            requestMap.put("tradeName",request.getParameter("tradeName"));
           TradeResultResponse tradeResultResponse=jdResponseService.decrypt(requestMap);
           if(tradeResultResponse!=null){
              //跳转支付成功页面
               PayLog payLog=new PayLog();
               payLog.setTradenum(tradeResultResponse.getTradeNum());
               PayLog pay=jdResponseService.selectPayLog(payLog);
               model.addAttribute("tradeAmount",nf.format(Double.parseDouble(tradeResultResponse.getAmount())/100));
               model.addAttribute("amount",tradeResultResponse.getAmount());//供失败页面获取
               model.addAttribute("orderId",pay.getOrderid());
               model.addAttribute("period",pay.getPeriod());
               model.addAttribute("tradeName",pay.getTradename());
               model.addAttribute("userId",pay.getUserid());
               model.addAttribute("source",pay.getSource());
               return paySuccessUrl;
           }
           return payFailUrl;
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
    @RequestMapping(value = "/notifyNote")
    public String execute(HttpServletRequest httpServletRequest) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) httpServletRequest.getInputStream()));
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            logger.info("异步通知原始数据:" + sb);
            try {
                AsynNotifyResponse anRes = JdPayUtil.parseResp(pubKey, desKey, sb.toString(), AsynNotifyResponse.class);
                if(anRes!=null){
                    CallbackLog callbackLog=new CallbackLog();
                    callbackLog.setTradenum(anRes.getTradeNum());
                    CallbackLog callback=jdResponseService.selectCallbackLog(callbackLog);
                    if(callback!=null){
                        PayTradeVo payTrade=anRes.getPayList().get(0);//当用户组包含多个支付方式时会有多个 暂时只有一个
                        try {
                            //记录回调日志
                            jdResponseService.addCallBackLog(anRes,payTrade);
                            //记录交易流水
                            jdResponseService.addTransDetail(anRes,payTrade);
                        } catch (Exception e) {
                            logger.info("回调日志记录或交易流水保存异常"+e);
                        }
                        logger.info("异步通知解析数据:" + anRes);
                        logger.info("异步通知订单号和支付状态：" + anRes.getTradeNum() + ",状态：" + anRes.getStatus() + "成功!!!!");
                        PayLog payLog=new PayLog();
                        payLog.setTradenum(anRes.getTradeNum());
                        PayLog pay=jdResponseService.selectPayLog(payLog);
                        RepayPlan repayPlan=new RepayPlan();
                        repayPlan.setOrderId(Integer.parseInt(pay.getOrderid()));
                        repayPlan.setPeriod(Integer.parseInt(pay.getPeriod()));
                        repayPlan.setRepidTime(pay.getTradetime());
                        repayPlan.setState((byte)RepayState.ALREADY_STATE.ordinal());//更新还款计划为已还款
                        int result=repayPlanManager.updatePlan(repayPlan);
                        logger.info("更新还款计划状态："+result);
                    }

                }

            } catch (Exception e) {
                logger.error(e.getMessage());
                return FAIL_PAGE_STRING;
            }
            logger.info("==异步通知受理接口调用完毕==");
            return SUCCESS_PAGE_STRING;
        } catch (IOException e) {
            logger.error("异步通知异常:" + e);
            return FAIL_PAGE_STRING;
        }
    }



}
