package com.jzfq.fms.dao;

import com.jzfq.fms.domain.PayLog;

public interface PayLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PayLog record);

    int insertSelective(PayLog record);

    PayLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PayLog record);

    int updateByPrimaryKey(PayLog record);

    PayLog selectPayLog(PayLog payLog);
}