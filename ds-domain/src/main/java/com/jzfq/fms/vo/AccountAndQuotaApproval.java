package com.jzfq.fms.vo;

import com.jzfq.fms.domain.Account;
import com.jzfq.fms.domain.QuotaApproval;

/**
 * Created by 星星 on 2016/12/8.
 */
public class AccountAndQuotaApproval {

    private Account account;
    private QuotaApproval quotaApproval;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public QuotaApproval getQuotaApproval() {
        return quotaApproval;
    }

    public void setQuotaApproval(QuotaApproval quotaApproval) {
        this.quotaApproval = quotaApproval;
    }
}
