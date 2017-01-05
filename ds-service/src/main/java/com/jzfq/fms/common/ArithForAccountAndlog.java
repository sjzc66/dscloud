package com.jzfq.fms.common;

import com.jzfq.fms.common.enums.AccountType;
import com.jzfq.fms.common.util.ServiceValidate;
import com.jzfq.fms.domain.Account;
import com.jzfq.fms.domain.AccountLog;
import com.jzfq.fms.domain.RepayPlan;
import com.jzfq.fms.domain.ValidOrder;
import com.jzfq.fms.service.impl.AccountServiceImpl;
import org.apache.commons.collections.map.HashedMap;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by zhishuo on 11/18/16.
 */
public class ArithForAccountAndlog {

    /**
     * 初始化--->额度账户（计算相关金额）
     * @param account
     * @return
     */
    public static AccountAndLog initialAmountForAccount(Account account){
        AccountLog log = new AccountLog();
        if(account.getCashInitAmount()!=null&&account.getConsumeInitAmount()==null){
            account.setAllValidAmount(account.getCashInitAmount());
            account.setAllInitAmount(account.getCashInitAmount());
            log.setModifyAmount(account.getCashInitAmount());//修改金额
            log.setCashValidAmount(account.getCashValidAmount());//现金可用授信额度
            log.setAllValidAmount(account.getCashValidAmount());//可用额度总和

        } else if (account.getConsumeInitAmount()!=null&&account.getCashInitAmount()==null) {
            account.setAllValidAmount(account.getConsumeInitAmount());
            account.setAllInitAmount(account.getConsumeInitAmount());
            log.setModifyAmount(account.getConsumeInitAmount());//修改金额
            log.setConsumeValidAmount(account.getConsumeValidAmount());//消费可用授信额度
            log.setAllValidAmount(account.getConsumeValidAmount());//可用额度总和
        }else if(account.getCashInitAmount()!=null&&account.getConsumeInitAmount()!=null){
            account.setAllValidAmount(account.getCashInitAmount().add(account.getConsumeInitAmount()));
            account.setAllInitAmount(account.getCashInitAmount().add(account.getConsumeInitAmount()));
            log.setModifyAmount(account.getCashInitAmount().add(account.getConsumeInitAmount()));//修改金额
            log.setCashValidAmount(account.getCashValidAmount());//现金可用授信额度
            log.setConsumeValidAmount(account.getConsumeValidAmount());//消费可用授信额度
            log.setAllValidAmount(account.getCashInitAmount().add(account.getConsumeInitAmount()));//可用额度总和
        }else{
            ServiceValidate.isTrue(false, "现金额度和消费额度不能都为空");
        }

        account.setActivated((byte)0);
        account.setCachFrozenAmount(new BigDecimal(0));//现金账户冻结金额为0
        account.setConsumeFrozenAmount(new BigDecimal(0));//消费账户冻结金额为0
        log.setAccountId(account.getId());//账号id
        log.setCustomerId(account.getCustomerId());//客户id
        log.setCreateTime(new Date());

        ArithForAccountAndlog.AccountAndLog accountAndLog = new AccountAndLog();
        accountAndLog.setAccount(account);
        accountAndLog.setAccountLog(log);
        return accountAndLog;
    }


    /**
     * 提现冻结--->额度账户（计算相关金额）
     * @param at
     * @param modifyAmount
     * @param accountType
     * @return
     */
    public static Account arithAmountForAccount(Account at, BigDecimal modifyAmount, AccountType accountType){
        if(accountType.name().equals(AccountType.CASH_TYPE)){

            at.setCachFrozenAmount(modifyAmount);//现金账户冻结金额
            BigDecimal newCashValidAmount = at.getCashValidAmount().add(modifyAmount);
            //修改现金账户可用额度（额度账户表）
            at.setCashValidAmount(newCashValidAmount);
        }else{

            at.setConsumeFrozenAmount(modifyAmount);//消费账户冻结金额
            BigDecimal newConsumeValidAmount = at.getConsumeValidAmount().add(modifyAmount);
            //修改消费账户可用额度（额度账户表）
            at.setConsumeValidAmount(newConsumeValidAmount);
        }
        BigDecimal newAllValidAmount = at.getAllValidAmount().add(modifyAmount);
        //修改账户可用额度（额度账户表）
        at.setAllValidAmount(newAllValidAmount);
        return at;
    }

    /**
     * 提现完成/还款--->额度账户（计算相关金额）
     * @param at
     * @param modifyAmount
     * @param accountType
     * @return
     */
    public static AccountAndLog arithAmountForAccountAndLog(Account at, BigDecimal modifyAmount, AccountType accountType){
        AccountLog log = new AccountLog();
        if(accountType.name().equals(AccountType.CASH_TYPE)){
            BigDecimal newCashValidAmount = at.getCashValidAmount().add(modifyAmount);
            //修改现金账户可用额度（额度账户表）
            at.setCashValidAmount(newCashValidAmount);
            //现金账户可用额度（额度账户流水表）
            log.setCashValidAmount(newCashValidAmount);
        }else{
            BigDecimal newConsumeValidAmount = at.getConsumeValidAmount().add(modifyAmount);
            //修改消费账户可用额度（额度账户表）
            at.setConsumeValidAmount(newConsumeValidAmount);
            //消费账户可用额度（额度账户流水表）
            log.setConsumeValidAmount(newConsumeValidAmount);
        }
        BigDecimal newAllValidAmount = at.getAllValidAmount().add(modifyAmount);

        //修改账户可用额度（额度账户表）
        at.setCachFrozenAmount(new BigDecimal(0));//重置现金账户冻结金额为0
        at.setConsumeFrozenAmount(new BigDecimal(0));//重置消费账户冻结金额为0
        at.setAllValidAmount(newAllValidAmount);

        //账户可用额度（额度账户流水表）
        log.setAllValidAmount(newAllValidAmount);
        //账户修改额度（额度账户流水表）
        log.setModifyAmount(modifyAmount);
        log.setCreateTime(new Date());
        log.setCustomerId(at.getCustomerId());
        log.setAccountId(at.getId());
        log.setModifyAmount(modifyAmount);

        ArithForAccountAndlog.AccountAndLog accountAndLog = new AccountAndLog();
        accountAndLog.setAccount(at);
        accountAndLog.setAccountLog(log);
        return accountAndLog;
    }

    public static class AccountAndLog {

        private Account account;

        private AccountLog accountLog;

        public Account getAccount() {
            return account;
        }

        public void setAccount(Account account) {
            this.account = account;
        }

        public AccountLog getAccountLog() {
            return accountLog;
        }

        public void setAccountLog(AccountLog accountLog) {
            this.accountLog = accountLog;
        }
    }

}
