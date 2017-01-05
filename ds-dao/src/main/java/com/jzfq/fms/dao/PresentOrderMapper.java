package com.jzfq.fms.dao;

import com.jzfq.fms.common.common.PageVo;
import com.jzfq.fms.common.common.Pageable;
import com.jzfq.fms.domain.PresentOrder;
import com.jzfq.fms.interceptor.PageList;
import org.apache.ibatis.annotations.Param;

public interface PresentOrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PresentOrder record);

    int insertSelective(PresentOrder record);

    PresentOrder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PresentOrder record);

    int updateByPrimaryKey(PresentOrder record);

    PageList<PresentOrder> findPresentOrderList(@Param("vo") PageVo vo, Pageable pageable);

}