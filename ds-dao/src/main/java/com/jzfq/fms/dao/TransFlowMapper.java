package com.jzfq.fms.dao;

import com.jzfq.fms.domain.TransFlow;

public interface TransFlowMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TransFlow record);

    int insertSelective(TransFlow record);

    TransFlow selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TransFlow record);

    int updateByPrimaryKey(TransFlow record);
    
    TransFlow getFlowByPlanId(Integer planId);
}