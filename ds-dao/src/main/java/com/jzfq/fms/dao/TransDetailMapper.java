package com.jzfq.fms.dao;

import com.jzfq.fms.domain.TransDetail;

public interface TransDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TransDetail record);

    int insertSelective(TransDetail record);

    TransDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TransDetail record);

    int updateByPrimaryKey(TransDetail record);
}