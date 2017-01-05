package com.jzfq.fms.rest;


import com.jzfq.fms.common.common.JsonResult;
import com.jzfq.fms.vo.AccountAndQuotaApproval;
import com.jzfq.fms.service.IAccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;


/**
 * Created by zhishuo on 11/9/16.
 */
@RestController
@RequestMapping("/api/account")
public class AccountApi extends BaseApi{

    @Autowired
    private IAccountService accountService;

    /**
     * 初始化额度账户--->(提供给风控系统)
     * 1.1 更新额度审批表（是否激活、最大额度）；
     * 1.2 判断是否激活标识，如果是0（激活），执行第2步；
     * 2.1 新增额度账户数据到额度账户表
     * 2.2 生成第一条账户初始化流水
     * @param accountAndQuotaApproval
     * @return
     */
    @RequestMapping(value = "/initialaccount",method = {RequestMethod.POST})
    public JsonResult initialAccount(@RequestBody AccountAndQuotaApproval accountAndQuotaApproval){
        boolean result = accountService.initialAccountAndLog(accountAndQuotaApproval.getAccount(),accountAndQuotaApproval.getQuotaApproval());

        if (result){
            return returnSuccess(accountAndQuotaApproval.getAccount().getCustomerId(),"此客户已经初始化了额度账户");
        }else{
            return returnSuccess(accountAndQuotaApproval.getAccount().getCustomerId(),"未初始化额度账户，建议不激活账户");
        }

    }


    /**
     * 依据客户ID查询额度账户可用额度--->(提供给PHP商城)
     * @param customerId
     * @return
     */
    @RequestMapping(value = "/getvalidamount",method = {RequestMethod.POST})
    public JsonResult validamount(@RequestParam Integer customerId){
        BigDecimal b = accountService.getValidamount(customerId);
        return returnSuccess(b,"此客户额度账户可用额度查询完毕");
    }


}
