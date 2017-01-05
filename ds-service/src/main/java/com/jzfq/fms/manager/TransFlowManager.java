package com.jzfq.fms.manager;

import com.jzfq.fms.dao.RepayPlanMapper;
import com.jzfq.fms.dao.TransFlowMapper;
import com.jzfq.fms.domain.RepayPlan;
import com.jzfq.fms.domain.TransFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by huyinglin on 2016/11/30.
 */
@Component
public class TransFlowManager {

    @Autowired
    private TransFlowMapper transFlowMapper;

    @Autowired
    private RepayPlanMapper repayPlanMapper;


    @Transactional
    public boolean addTransFlow(TransFlow transFlow,RepayPlan repayPlan){
        //插入流水
        boolean result=repayPlanMapper.updateStateByOrderId(repayPlan)>0?true:false;

        boolean state=transFlowMapper.insert(transFlow)>0?true:false;
        if(result&&state){
            return true;
        }
        return false;
    }

    @Transactional
    public int updateTransFlow(TransFlow transFlow){
        return transFlowMapper.updateByPrimaryKey(transFlow);
    }
    
    public TransFlow getFlowByPlanId(int repayPlanId){
        return transFlowMapper.getFlowByPlanId(repayPlanId);
    }

}
