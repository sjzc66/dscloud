package com.jzfq.fms.dao;

import com.jzfq.fms.common.common.PageVo;
import com.jzfq.fms.common.common.Pageable;
import com.jzfq.fms.domain.ValidOrder;
import com.jzfq.fms.interceptor.PageList;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ValidOrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ValidOrder record);

    int insertSelective(ValidOrder record);

    ValidOrder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ValidOrder record);

    int updateByPrimaryKey(ValidOrder record);

    List<ValidOrder> queryValidOrderListByIsDeal();

    ValidOrder getOrderByOrderId(Integer orderId);

    PageList<ValidOrder> findValidOrderList(@Param("vo") PageVo vo, Pageable pageable);
}