package com.jzfq.fms.dao;

import com.jzfq.fms.domain.CallbackLog;

public interface CallbackLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CallbackLog record);

    int insertSelective(CallbackLog record);

    CallbackLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CallbackLog record);

    int updateByPrimaryKey(CallbackLog record);

    CallbackLog selectCallbackLog(CallbackLog callbackLog);
}