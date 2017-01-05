package com.jzfq.fms.dao;

import com.jzfq.fms.domain.Account;

public interface AccountMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Account record);

    int insertSelective(Account record);

    Account selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Account record);

    int updateByPrimaryKey(Account record);

    Account selectByCustomerId(Integer customerId);
}