package com.jzfq.fms.dao;

import com.jzfq.fms.domain.AccountLog;

public interface AccountLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AccountLog record);

    int insertSelective(AccountLog record);

    AccountLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AccountLog record);

    int updateByPrimaryKey(AccountLog record);
}