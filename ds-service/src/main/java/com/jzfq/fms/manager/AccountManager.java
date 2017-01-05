package com.jzfq.fms.manager;

import com.jzfq.fms.dao.AccountLogMapper;
import com.jzfq.fms.dao.AccountMapper;

import com.jzfq.fms.dao.QuotaApprovalMapper;
import com.jzfq.fms.domain.Account;
import com.jzfq.fms.domain.AccountLog;
import com.jzfq.fms.domain.QuotaApproval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zhishuo on 11/25/16.
 */
@Component
public class AccountManager {

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private AccountLogMapper accountLogMapper;

    @Autowired
    private QuotaApprovalMapper quotaApprovalMapper;

    @Transactional
    public void saveAccountAndLog(Account account, AccountLog accountLog, QuotaApproval quotaApproval){
        quotaApprovalMapper.updateQuotaApprovalMaxAcount(quotaApproval);
        accountMapper.insert(account);
        accountLogMapper.insert(accountLog);
    }

    @Transactional
    public void modifyAccountAndSaveLog(Account account, AccountLog accountLog){
        accountMapper.updateByPrimaryKey(account);
        accountLogMapper.insert(accountLog);
    }
}
