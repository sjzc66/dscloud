package com.jzfq.fms.manager;

import com.jzfq.fms.dao.PayLogMapper;
import com.jzfq.fms.domain.PayLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by huyinglin on 2016/12/8.
 */
@Component
public class PayLogManager {

    @Autowired
    private PayLogMapper payLogMapper;

    @Transactional
    public int addPayLog(PayLog payLog){
       return  payLogMapper.insert(payLog);
    }

    public PayLog selectPayLog(PayLog payLog){
        return payLogMapper.selectPayLog(payLog);
    }
}
