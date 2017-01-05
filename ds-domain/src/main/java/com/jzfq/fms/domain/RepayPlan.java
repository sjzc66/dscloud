package com.jzfq.fms.domain;

import java.math.BigDecimal;
import java.util.Date;

public class RepayPlan {
    private Integer id;

    private Integer productId;

    private Integer orderId;

    private Integer actualPeriod;

    private Integer period;

    private BigDecimal storeMonthRate;

    private Integer repayType;

    private BigDecimal amount;

    private BigDecimal repayMoney;

    private BigDecimal principal;

    private BigDecimal interest;

    private Date repayday;

    private Byte state;

    private BigDecimal sumPrincipal;

    private BigDecimal sumInterest;

    private Date repidTime;

    private Date createTime;

    private Date updateTime;
    //来源
    private String source;
    
    private String canRepay;

    private Integer overDueDay;

    private BigDecimal overDueFee;
    
    private Integer customerId;
    
    private String title;
    
    private String selfIsCanRepay;
    
    private String payType;
    
    private String payId;
    
    private Date payTime;

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public String getSelfIsCanRepay() {
        return selfIsCanRepay;
    }

    public void setSelfIsCanRepay(String selfIsCanRepay) {
        this.selfIsCanRepay = selfIsCanRepay;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getActualPeriod() {
        return actualPeriod;
    }

    public void setActualPeriod(Integer actualPeriod) {
        this.actualPeriod = actualPeriod;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public BigDecimal getStoreMonthRate() {
        return storeMonthRate;
    }

    public void setStoreMonthRate(BigDecimal storeMonthRate) {
        this.storeMonthRate = storeMonthRate;
    }

    public Integer getRepayType() {
        return repayType;
    }

    public void setRepayType(Integer repayType) {
        this.repayType = repayType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getRepayMoney() {
        return repayMoney;
    }

    public void setRepayMoney(BigDecimal repayMoney) {
        this.repayMoney = repayMoney;
    }

    public BigDecimal getPrincipal() {
        return principal;
    }

    public void setPrincipal(BigDecimal principal) {
        this.principal = principal;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

    public Date getRepayday() {
        return repayday;
    }

    public void setRepayday(Date repayday) {
        this.repayday = repayday;
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    public BigDecimal getSumPrincipal() {
        return sumPrincipal;
    }

    public void setSumPrincipal(BigDecimal sumPrincipal) {
        this.sumPrincipal = sumPrincipal;
    }

    public BigDecimal getSumInterest() {
        return sumInterest;
    }

    public void setSumInterest(BigDecimal sumInterest) {
        this.sumInterest = sumInterest;
    }

    public Date getRepidTime() {
        return repidTime;
    }

    public void setRepidTime(Date repidTime) {
        this.repidTime = repidTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getOverDueDay() {
        return overDueDay;
    }

    public void setOverDueDay(Integer overDueDay) {
        this.overDueDay = overDueDay;
    }

    public BigDecimal getOverDueFee() {
        return overDueFee;
    }

    public void setOverDueFee(BigDecimal overDueFee) {
        this.overDueFee = overDueFee;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCanRepay() {
        return canRepay;
    }

    public void setCanRepay(String canRepay) {
        this.canRepay = canRepay;
    }

    public static class RepayPlanBuilder {
        private RepayPlan plan;

        public RepayPlanBuilder() {
            plan = new RepayPlan();
        }

        public RepayPlanBuilder period(int period) {
            this.plan.setPeriod(period);
            return this;
        }

        public RepayPlanBuilder productId(int productId) {
            this.plan.setProductId(productId);
            return this;
        }

        public RepayPlanBuilder principal(BigDecimal principal) {
            this.plan.setPrincipal(principal);
            return this;
        }

        public RepayPlanBuilder interest(BigDecimal interest) {
            this.plan.setInterest(interest);
            return this;
        }

        public RepayPlanBuilder sumInterest(BigDecimal sum) {
            this.plan.setSumInterest(sum);
            return this;
        }

        public RepayPlanBuilder sumPrincipal(BigDecimal sum) {
            this.plan.setSumPrincipal(sum);
            return this;
        }

        public RepayPlanBuilder repayDay(Date date) {
            this.plan.setRepayday(date);
            return this;
        }

        public RepayPlanBuilder createTime(Date date) {
            this.plan.setCreateTime(date);
            return this;
        }

        public RepayPlanBuilder updateTime(Date date) {
            this.plan.setUpdateTime(date);
            return this;
        }

        public RepayPlanBuilder repayMoney(BigDecimal money) {
            this.plan.setRepayMoney(money);
            return this;
        }

        public RepayPlanBuilder orderId(Integer orderId) {
            this.plan.setOrderId(orderId);
            return this;
        }

        public RepayPlanBuilder storeMonthRate(BigDecimal monthRate) {
            this.plan.setStoreMonthRate(monthRate);
            return this;
        }

        public RepayPlanBuilder amount(BigDecimal amount) {
            this.plan.setAmount(amount);
            return this;
        }

        public RepayPlanBuilder actualPeriod(int period) {
            this.plan.setActualPeriod(period);
            return this;
        }

        public RepayPlanBuilder id(int id) {
            this.plan.setId(id);
            return this;
        }

        public RepayPlanBuilder state(byte state) {
            this.plan.setState(state);
            return this;
        }

        public RepayPlanBuilder overDueFee(BigDecimal fee) {
            this.plan.setOverDueFee(fee);
            return this;
        }

        public RepayPlanBuilder overDueDay(int day) {
            this.plan.setOverDueDay(day);
            return this;
        }
        
        public RepayPlanBuilder customerId(int id){
            this.plan.setCustomerId(id);
            return this;
        }


        public RepayPlan build() {
            return this.plan;
        }

    }
}