package com.jzfq.fms.service;

import com.jzfq.fms.common.common.PageVo;
import com.jzfq.fms.domain.Customer;
import com.jzfq.fms.domain.QuotaApproval;
import com.jzfq.fms.interceptor.PageList;

import java.util.List;

/**
 * Created by huyinglin on 2016/11/23.
 */
public interface IQuotaApprovalService {

    /**
     * 查询额度审批单
     * @param vo
     * @return
     */
    PageList<QuotaApproval> queryQuotaApprovalList(PageVo vo);

    /**
     * 通过风控传的数据插入额度审批表和用户表
     * @param quotaApproval
     * @return
     */
    boolean insertQuotaApprovalAndCustomer(QuotaApproval quotaApproval);

    /**
     * 根据订单号修改额度审批表中审核状态
     * @param orderId
     * @return
     */
    boolean updateQuotaApprovalAuditStatusByOrderId(String orderId,String auditStatus);

    QuotaApproval selectQuotaApprovalById(Integer id);

    /**
     * 修改额度审批表 是否激活、最大额度 信息
     * @param quotaApproval
     * @return
     */
    boolean updateQuotaApprovalMaxAcount(QuotaApproval quotaApproval);

    /**
     * 查询未成功推送风控的审批单
     * @return
     */
    List<QuotaApproval> queryQuotaApprovalByIsPushed();

    /**
     * 通知风控
     * @param quotaApproval
     * @return
     */
    void noticeRisk(QuotaApproval quotaApproval);
}
