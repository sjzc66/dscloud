package com.jzfq.fms.service.impl;


import com.jzfq.fms.common.ArithForAccountAndlog;
import com.jzfq.fms.common.enums.AccountType;
import com.jzfq.fms.dao.AccountMapper;
import com.jzfq.fms.domain.Account;
import com.jzfq.fms.domain.QuotaApproval;
import com.jzfq.fms.manager.AccountManager;
import com.jzfq.fms.service.IAccountService;
import com.jzfq.fms.service.IQuotaApprovalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;


/**
 * 额度账号service
 */
@Service
public class AccountServiceImpl implements IAccountService {

	@Autowired
	private AccountManager accountManager;

	@Autowired
	private AccountMapper accountMapper;

	@Autowired
	private IQuotaApprovalService quotaApprovalService;

	private Logger logger= LoggerFactory.getLogger(AccountServiceImpl.class);

	@Override
	public Account getAccountById(int accountId) {
		return accountMapper.selectByPrimaryKey(accountId);
	}

	/**
	 * 初始化：1.新增额度账户数据到额度账户表 2.生成第一条账户初始化流水
	 * @param account
	 */
	@Override
	public boolean initialAccountAndLog(Account account, QuotaApproval quotaApproval) {
		logger.info("=====初始化账户额度开始========");
		boolean flag = false;
		if (quotaApproval.getIsActived() == 0){
			quotaApprovalService.updateQuotaApprovalMaxAcount(quotaApproval);
			logger.info("=====未初始化账户额度，建议不激活账户额度=====");
			return flag;
		}else{
			ArithForAccountAndlog.AccountAndLog accountAndLog = ArithForAccountAndlog.initialAmountForAccount(account);
			accountManager.saveAccountAndLog(accountAndLog.getAccount(),accountAndLog.getAccountLog(),quotaApproval);
			logger.info("=====初始化账户额度成功======");
			flag = true;
			return flag;
		}
	}

	/**
	 * 提现冻结:1.额度账户可用额度减少 2.生成提现流水
	 * @param customerId     客户ID
	 * @param modifyAmount   修改金额（负数是提现 正数是还款）
	 * @param accountType    账号类型（1：现金账户  2：消费账户）
	 */
	@Override
	public void enchashmentFrozenAccountAndLog(Integer customerId, BigDecimal modifyAmount, AccountType accountType) {
		Account at = accountMapper.selectByCustomerId(customerId);
		Account account = ArithForAccountAndlog.arithAmountForAccount(at,modifyAmount,accountType);
		accountMapper.insert(account);
	}

	/**
	 * 提现完成:1.额度账户可用额度减少 2.生成提现流水
	 * @param customerId     客户ID
	 * @param modifyAmount   修改金额（负数是提现 正数是还款）
	 * @param accountType    账号类型（1：现金账户  2：消费账户）
	 */
	@Override
	public void enchashmentFinishAccountAndLog(Integer customerId, BigDecimal modifyAmount, AccountType accountType) {
		Account account = accountMapper.selectByCustomerId(customerId);
		ArithForAccountAndlog.AccountAndLog accountAndLog = ArithForAccountAndlog.arithAmountForAccountAndLog(account,modifyAmount,accountType);
		accountManager.modifyAccountAndSaveLog(accountAndLog.getAccount(),accountAndLog.getAccountLog());
	}

	/**
	 * 还款:1.额度账户可用额度增加 2.生成还款流水
	 * @param customerId     客户ID
	 * @param modifyAmount   修改金额（负数是提现 正数是还款）
	 * @param accountType    账号类型（1：现金账户  2：消费账户）
	 */
	@Override
	public void repaymentAccountAndLog(Integer customerId, BigDecimal modifyAmount, AccountType accountType) {
		Account account = accountMapper.selectByCustomerId(customerId);
		ArithForAccountAndlog.AccountAndLog accountAndLog = ArithForAccountAndlog.arithAmountForAccountAndLog(account,modifyAmount,AccountType.CASH_TYPE);
		accountManager.modifyAccountAndSaveLog(accountAndLog.getAccount(),accountAndLog.getAccountLog());
	}

	/**
	 * 依据客户ID查询客户额度账户可用额度
	 * @param customerId
	 * @return
	 */
	@Override
	public BigDecimal getValidamount(Integer customerId) {
		Account account = accountMapper.selectByCustomerId(customerId);
		return account.getAllValidAmount();
	}



}
