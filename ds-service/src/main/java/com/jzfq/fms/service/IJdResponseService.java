package com.jzfq.fms.service;

import com.jzfq.fms.domain.CallbackLog;
import com.jzfq.fms.domain.PayLog;
import com.jzfq.fms.vo.jdpay.AsynNotifyResponse;
import com.jzfq.fms.vo.jdpay.PayTradeVo;
import com.jzfq.fms.vo.jdpay.TradeResultResponse;

import java.util.Map;

/**
 * Created by huyinglin on 2016/12/15.
 */
public interface IJdResponseService {

    /**
     * 解密京东返回的参数
     * @param params
     * @return
     */
    TradeResultResponse decrypt(Map<String,String> params) throws  Exception;

    /**
     *  记录回调日志
     * @param asynNotifyResponse
     * @param payTradeVo
     */
    void addCallBackLog(AsynNotifyResponse asynNotifyResponse, PayTradeVo payTradeVo);

    /**
     * 记录交易流水
     * @param asynNotifyResponse
     * @param payTradeVo
     */
    void addTransDetail(AsynNotifyResponse asynNotifyResponse, PayTradeVo payTradeVo);

    /**
     * 根据交易流水号查询订单号和期数
     * @param payLog
     * @return
     */
    PayLog selectPayLog(PayLog payLog);

    /**
     * 根据交易流水号查询回调日志
     * @param callbackLog
     * @return
     */
    CallbackLog selectCallbackLog(CallbackLog callbackLog);

}
