package com.jzfq.fms.service.impl;

import com.jzfq.fms.common.enums.RepayState;
import com.jzfq.fms.common.strategy.HttpGetData;
import com.jzfq.fms.common.util.ServiceValidate;
import com.jzfq.fms.domain.RepayPlan;
import com.jzfq.fms.domain.TransFlow;
import com.jzfq.fms.manager.RepayPlanManager;
import com.jzfq.fms.manager.TransFlowManager;
import com.jzfq.fms.service.ITransFlowService;

import net.sf.json.JSONObject;

import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by huyinglin on 2016/11/30.
 */
@Service
public class TransFlowServiceImpl extends HttpGetData implements ITransFlowService {

    private Logger logger = LoggerFactory.getLogger(TransFlowServiceImpl.class);

    @Autowired
    private TransFlowManager transFlowManager;

    @Autowired
    private RepayPlanManager repayPlanManager;

    @Value("${synchroRepayPeriods.url}")
    private String synchroRepayPeriodsUrl;

    @Value("${synchroRepayNotifySuccess.url}")
    private String synchroRepayNotifySuccessUrl;

    @Override
    public boolean addTransFlow(TransFlow transFlow) {
        ServiceValidate.notNull(transFlow, "交易流水为空");
        RepayPlan plan = new RepayPlan();
        plan.setOrderId(transFlow.getOrderId());
        plan.setPeriod(transFlow.getPeriods());
        plan.setState((byte) RepayState.ALREADY_STATE.ordinal());
        plan.setRepidTime(transFlow.getFlowTime());
        boolean isTrue = transFlowManager.addTransFlow(transFlow, plan);

        //调用催收系统更改催收成功状态接口
        if (isTrue) {
            Map<Object, Object> parames = new HashMap<Object, Object>();
            parames.put("caseId", transFlow.getPlanId());//还款计划ID
            Object message = doPost(synchroRepayNotifySuccessUrl, parames);
            if (message != null && JSONObject.fromObject(message).getBoolean("success")) {
                logger.info("调用更改催收成功状态接口：" + message.toString());
            } else {
                logger.info("调用更改催收成功状态接口异常");
            }
        }
        if (isTrue) {
            //由于PHP无测试环境先不调用
            Map<Object, Object> parames = new HashedMap();
            parames.put("order_id", transFlow.getOrderId());//订单号
            parames.put("stage_number", transFlow.getPeriods());//期数
            try {
                Object message = doPost(synchroRepayPeriodsUrl, parames);
                if (message != null) {
                    logger.info("调用PHP同步还款状态返回信息：" + message.toString());
                } else {
                    logger.info("调用PHP接口异常");
                }
                return true;
            } catch (Exception e) {
                logger.error("调用php还款接口失败，[url:{}]，[parames:{}]", synchroRepayPeriodsUrl, parames);
                e.printStackTrace();
                return true;
            }
        }
        return false;
    }

    @Override
    public int updateTransFlow(TransFlow transFlow) {
        return transFlowManager.updateTransFlow(transFlow);
    }

    @Override
    protected Object processData(Object obj) {
        return obj;
    }
}
