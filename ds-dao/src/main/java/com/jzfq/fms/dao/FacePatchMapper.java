package com.jzfq.fms.dao;

import com.jzfq.fms.common.common.PageVo;
import com.jzfq.fms.common.common.Pageable;
import com.jzfq.fms.domain.FacePatch;
import com.jzfq.fms.interceptor.PageList;
import org.apache.ibatis.annotations.Param;

public interface FacePatchMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FacePatch record);

    int insertSelective(FacePatch record);

    FacePatch selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FacePatch record);

    int updateByPrimaryKey(FacePatch record);

    PageList<FacePatch> findFacePatchList(@Param("vo") PageVo vo, Pageable pageable);

    FacePatch selectByOrderId(String orderId);
}