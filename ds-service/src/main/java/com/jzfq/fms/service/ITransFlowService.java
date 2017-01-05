package com.jzfq.fms.service;

import com.jzfq.fms.domain.TransFlow;

/**
 * Created by huyinglin on 2016/11/30.
 */
public interface ITransFlowService {

    boolean addTransFlow(TransFlow transFlow);

    int updateTransFlow(TransFlow transFlow);
}
