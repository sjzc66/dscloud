package com.jzfq.fms.controller;

import com.jzfq.fms.common.common.JsonResult;
import com.jzfq.fms.domain.Account;
import com.jzfq.fms.domain.BasePayOrderInfo;
import com.jzfq.fms.domain.FacePatch;
import com.jzfq.fms.domain.PresentOrder;
import com.jzfq.fms.domain.QuotaApproval;
import com.jzfq.fms.domain.RepayPlan;
import com.jzfq.fms.domain.ValidOrder;
import com.jzfq.fms.service.IAccountService;
import com.jzfq.fms.service.IFacePatchService;
import com.jzfq.fms.service.IJdPayService;
import com.jzfq.fms.service.IPresentOrderService;
import com.jzfq.fms.service.IProductService;
import com.jzfq.fms.service.IQuotaApprovalService;
import com.jzfq.fms.service.IRepayService;
import com.jzfq.fms.service.IValidOrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by zhishuo on 11/9/16.
 */
@RestController
@RequestMapping("/openapi")
public class OpenApiController extends BaseController {

    @Autowired
    private IRepayService repayService;

    @Autowired
    private IValidOrderService validOrderService;

    @Autowired
    private IProductService productService;

    @Autowired
    private IFacePatchService facePatchService;

    @Autowired
    private IQuotaApprovalService quotaApprovalService;

    @Autowired
    private IPresentOrderService presentOrderService;

    @Autowired
    private IAccountService accountService;

    @Autowired
    private IJdPayService jdPayService;


    /**
     * @param validOrder
     * @return
     */
    @RequestMapping(value = "/plan/generate", method = RequestMethod.POST)
    public JsonResult getPlan(ValidOrder validOrder) {
        repayService.generateRepayPlan(validOrder);
        return returnSuccess(validOrder.getOrderId(), "生成完成");
    }

    /**
     * 1、依据已发货订单插入有效订单  2、依据有效订单生成还款计划  (提供给PHP商城)
     *
     * @param validOrder 订单ID 利率 金额 期数 类型 （0白条 1商城 2随手借）
     * @return
     */
    @RequestMapping("/validorder/insertvalidorder")
    public JsonResult insertValidOrder(ValidOrder validOrder) {
        validOrderService.addValidOrderAndAddRepayPlan(validOrder);
        return returnSuccess("orderId", "1、添加数据到有效订单  2、生成还款计划插入到数据库");
    }

    /**
     * 1、依据php传过来的有效订单查询还款计划并返回  (提供给PHP商城)
     *
     * @param validOrder 订单ID 利率 金额 期数 类型 （0白条 1商城 2随手借）
     * @return
     */
    @RequestMapping("/validorder/getrepayplan")
    public JsonResult getrepayplan(ValidOrder validOrder) {
        List<RepayPlan> repayPlanList = validOrderService.getRepayPlansByValidOrder(validOrder);
        return returnSuccess(repayPlanList, "依据php传过来的有效订单查询还款计划并返回");
    }

    /**
     * 获取产品列表
     *
     * @return
     */
    @RequestMapping("/product/list")
    public JsonResult getProducts() {
        return returnSuccess(productService.getProducts());
    }

    /**
     * 获取是否有逾期帐单
     *
     * @return
     */
    @RequestMapping("/overdue/count")
    public JsonResult getOverdue() {
        //如果有逾期 是否会垫付
        return returnSuccess();
    }


    /**
     * 添加/修改数据到面签补件(提供给风控系统) 1.待上传资料  2。审核中  3 重传资料  4 风控审核通过
     *
     * @param facePatch
     * @return
     */
    @RequestMapping("/facepatch/insertorupdate")
    public JsonResult insertOrUpdateFacePatch(FacePatch facePatch) {
        facePatchService.insertOrUpdateFacePatch(facePatch);
        return returnSuccess("orderId", "添加数据到面签补件完成");
    }


    /**
     * 接受风控额度审批和用户信息保存额度审批表和用户表
     *
     * @param quotaApproval
     * @return
     */
    @RequestMapping("/approval/insert")
    public JsonResult insertQuotaApprovalAndCutomer(QuotaApproval quotaApproval) {
        quotaApprovalService.insertQuotaApprovalAndCustomer(quotaApproval);
        return returnSuccess(quotaApproval.getOrderId(), "此订单号的额度审批和用户信息录入成功");
    }

    /**
     * 根据风控传入的订单号更新额度审批表中的审核状态
     *
     * @param orderId
     * @return
     */
    @RequestMapping("/approval/update")
    public JsonResult updateQuotaApprovalAuditStatusByOrderId(String orderId, String auditStatus) {
        quotaApprovalService.updateQuotaApprovalAuditStatusByOrderId(orderId, auditStatus);
        return returnSuccess(orderId, "此订单号的审核状态已更新成功");
    }

    /**
     * 插入提现记录信息
     *
     * @param presentOrder
     * @return
     */
    @RequestMapping("/presentorder/insert")
    public JsonResult insertPersentOrder(PresentOrder presentOrder) {
        presentOrderService.insertPresentOrder(presentOrder);
        return returnSuccess(presentOrder.getOrderId(), "此笔提现已录入成功");
    }

    /**
     * 初始化：1.新增额度账户数据到额度账户表 2.生成第一条账户初始化流水
     *
     * @param account
     * @return
     */
    @RequestMapping("/account/initial")
    public JsonResult initialAccountAndLog(Account account, QuotaApproval quotaApproval) {
        accountService.initialAccountAndLog(account, quotaApproval);
        return returnSuccess(account.getCustomerId(), "此客户已经初始化了额度账户");
    }

    /**
     * 依据客户ID查询是否有过逾期
     *
     * @param customerId
     * @return
     */
    @RequestMapping("/plan/overdue")
    public JsonResult whetherOverdue(Integer customerId) {
        boolean b = repayService.whetherOverdue(customerId);
        return returnSuccess(b, "此客户是否逾期查询完毕");
    }

    /**
     * 依据客户ID查询额度账户可用额度
     *
     * @param customerId
     * @return
     */
    @RequestMapping("/account/validamount")
    public JsonResult validamount(Integer customerId) {
        BigDecimal b = accountService.getValidamount(customerId);
        return returnSuccess(b, "此客户额度账户可用额度查询完毕");
    }

    /**
     * 京东支付
     *
     * @param payInfo 支付参数
     * @return
     */
    @RequestMapping(value = "/jdpay/pay", method = RequestMethod.POST)
    public void pay(BasePayOrderInfo payInfo) {
        jdPayService.pay(payInfo);
    }
}
