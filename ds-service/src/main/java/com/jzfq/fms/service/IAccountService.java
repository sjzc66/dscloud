package com.jzfq.fms.service;

import com.jzfq.fms.common.enums.AccountType;
import com.jzfq.fms.domain.Account;
import com.jzfq.fms.domain.QuotaApproval;

import java.math.BigDecimal;

public interface IAccountService {


	/**
	 * 通过额度账户id查询额度账户
	 * @param accountId
	 * @return
	 */
	Account getAccountById(int accountId);

	/**
	 * 初始化：1.新增额度账户数据到额度账户表 2.生成第一条账户初始化流水
	 * @param account
	 */
	boolean initialAccountAndLog(Account account, QuotaApproval quotaApproval);

	/**
	 * 提现冻结:1.额度账户可用额度减少 2.生成提现流水
	 * @param customerId
	 * @param modifyAmount
	 * @param accountType
	 */
	void enchashmentFrozenAccountAndLog(Integer customerId, BigDecimal modifyAmount, AccountType accountType);


	/**
	 * 提现完成:1.额度账户可用额度减少 2.生成提现流水
	 * @param customerId
	 * @param modifyAmount
	 * @param accountType
	 */
	void enchashmentFinishAccountAndLog(Integer customerId, BigDecimal modifyAmount, AccountType accountType);

	/**
	 * 还款:1.额度账户可用额度增加 2.生成还款流水
	 * @param customerId
	 * @param modifyAmount
	 * @param accountType
	 */
	void  repaymentAccountAndLog(Integer customerId, BigDecimal modifyAmount, AccountType accountType);

	/**
	 * 依据客户ID查询客户额度账户可用额度
	 * @param customerId
	 * @return
	 */
    BigDecimal getValidamount(Integer customerId);
}
