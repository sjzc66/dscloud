package com.jzfq.fms.dao;

import com.jzfq.fms.common.common.PageVo;
import com.jzfq.fms.common.common.Pageable;
import com.jzfq.fms.domain.QuotaApproval;
import com.jzfq.fms.interceptor.PageList;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface QuotaApprovalMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(QuotaApproval record);

    int insertSelective(QuotaApproval record);

    QuotaApproval selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(QuotaApproval record);

    int updateByPrimaryKey(QuotaApproval record);

    PageList<QuotaApproval> findQuotaApprovalList(@Param("vo") PageVo vo, Pageable pageable);

    int updateQuotaApprovalByOrderId(QuotaApproval quotaApproval);

    /**
     * 更新 最大额度、是否激活账户
     * @param quotaApproval
     * @return
     */
    int updateQuotaApprovalMaxAcount(QuotaApproval quotaApproval);

    /**
     * 更新是否推送成功
     * @param quotaApproval
     * @return
     */
    int updateIsPushed(QuotaApproval quotaApproval);

    /**
     * 查询未成功推送风控的审批单
     * @return
     */
    List<QuotaApproval> queryQuotaApprovalByIsPushed();

}