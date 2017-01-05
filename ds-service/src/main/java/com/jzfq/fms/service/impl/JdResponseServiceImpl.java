package com.jzfq.fms.service.impl;

import com.jzfq.fms.common.util.payutil.*;
import com.jzfq.fms.dao.CallbackLogMapper;
import com.jzfq.fms.domain.CallbackLog;
import com.jzfq.fms.domain.PayLog;
import com.jzfq.fms.domain.TransDetail;
import com.jzfq.fms.manager.PayLogManager;
import com.jzfq.fms.service.IJdResponseService;
import com.jzfq.fms.service.ITransDetailService;
import com.jzfq.fms.vo.jdpay.AsynNotifyResponse;
import com.jzfq.fms.vo.jdpay.PayTradeVo;
import com.jzfq.fms.vo.jdpay.TradeResultResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 * Created by huyinglin on 2016/12/15.
 */
@Service
public class JdResponseServiceImpl implements IJdResponseService {

    private Logger logger= LoggerFactory.getLogger(JdResponseServiceImpl.class);

    @Value("${wepay.jd.merchant.desKey}")
    private String desKey;
    @Value("${wepay.jd.merchant.rsaPublicKey}")
    private String pubKey;
    @Autowired
    private ITransDetailService transDetailService;
    @Autowired
    private CallbackLogMapper callbackLogMapper;
    @Autowired
    private PayLogManager payLogManager;


    /**
     * 对京东支付返回的参数解密
     * @param map
     * @return
     * @throws Exception
     */
    public TradeResultResponse decrypt(Map<String,String> map) throws Exception {
        TradeResultResponse tradeResultRes = new TradeResultResponse();
        try {
            byte[] key = BASE64.decode(desKey);
            tradeResultRes.setTradeNum(ThreeDesUtil.decrypt4HexStr(key, map.get("tradeNum")));
            tradeResultRes.setAmount(ThreeDesUtil.decrypt4HexStr(key, map.get("tradeAmount")));
            tradeResultRes.setCurrency(ThreeDesUtil.decrypt4HexStr(key, map.get("tradeCurrency")));
            tradeResultRes.setTradeTime(ThreeDesUtil.decrypt4HexStr(key, map.get("tradeDate")));
            if (map.containsKey("tradeNote")){
                tradeResultRes.setNote(ThreeDesUtil.decrypt4HexStr(key, map.get("tradeNote")));
            }
            tradeResultRes.setStatus(ThreeDesUtil.decrypt4HexStr(key, map.get("tradeStatus")));
            logger.info("解密接入参数："+tradeResultRes);
            String strSourceData = SignUtil.signString(tradeResultRes, new ArrayList<String>());
            logger.info("strSourceData:"+strSourceData);
            byte[] decryptBASE64Arr = new BASE64Decoder().decodeBuffer(map.get("sign"));
            logger.info("decryptBASE64Arr:"+decryptBASE64Arr);
            byte[] decryptArr = RSACoder.decryptByPublicKey(decryptBASE64Arr, pubKey);
            logger.info("decryptArr",decryptArr);
            String decryptStr = new String(decryptArr, "UTF-8");
            String sha256SourceSignString = SHAUtil.Encrypt(strSourceData, null);
            logger.info("decryptStr[%s],sha256SourceSignString",decryptStr,sha256SourceSignString);
            if (!decryptStr.equals(sha256SourceSignString)) {
                logger.info("支付回调跳转页面验签失败!");
                return null;
            } else {
                logger.info("支付回调跳转页面成功" );
                return tradeResultRes;
            }
        } catch (IllegalAccessException e) {
            logger.error("支付回调跳转页面失败", e);
        }
        return tradeResultRes;
    }

    /**
     * 记录京东支付回调日志
     * @param anRes
     * @param payTrade
     */
    public void addCallBackLog(AsynNotifyResponse anRes,PayTradeVo payTrade){
        CallbackLog log=new CallbackLog();
        log.setTradenum(anRes.getTradeNum());
        log.setAmount(payTrade.getAmount());
        log.setCurrency(payTrade.getCurrency());
        logger.info("交易时间="+payTrade.getTradeTime());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        try {
            Date date = sdf.parse(payTrade.getTradeTime());
            log.setTradetime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        log.setPaytype(payTrade.getPayType());
        log.setStatus(anRes.getStatus());
        log.setTradetype(anRes.getTradeType());
        callbackLogMapper.insert(log);
    }

    /**
     * 记录交易流水
     * @param anRes
     * @param payTrade
     */
    public void addTransDetail(AsynNotifyResponse anRes,PayTradeVo payTrade){
        TransDetail detail=new TransDetail();
        detail.setTransType(String.valueOf(anRes.getTradeType()));
        detail.setAmount(Float.parseFloat(payTrade.getAmount()+""));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        try {
            Date date = sdf.parse(payTrade.getTradeTime());
            detail.setTransTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        detail.setTransStatus(anRes.getStatus());
        detail.setPayPattern(payTrade.getPayType());
        detail.setCurrency(payTrade.getCurrency());
        //根据交易流水号获取订单号和期数
        PayLog payLog=new PayLog();
        payLog.setTradenum(anRes.getTradeNum());
        PayLog pay=this.selectPayLog(payLog);
        detail.setOrderid(Integer.parseInt(pay.getOrderid()));
        detail.setPeriods(Integer.parseInt(pay.getPeriod()));
        detail.setTransChannel("0");    //'0京东支付 1 代扣
        detail.setTransNo(anRes.getTradeNum());
        transDetailService.addTransDetail(detail);
    }

    /**
     * 根据交易流水号查询订单号和期数
     * @param payLog
     * @return
     */
    @Override
    public PayLog selectPayLog(PayLog payLog) {
        return payLogManager.selectPayLog(payLog);
    }

    /**
     * 根据交易流水号查询回调日志
     * @param callbackLog
     * @return
     */
    @Override
    public CallbackLog selectCallbackLog(CallbackLog callbackLog) {
        return callbackLogMapper.selectCallbackLog(callbackLog);
    }
}
