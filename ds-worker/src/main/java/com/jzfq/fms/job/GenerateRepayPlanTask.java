package com.jzfq.fms.job;

import com.alibaba.fastjson.JSON;
import com.jzfq.fms.common.httpclient.HttpConnectionManager;
import com.jzfq.fms.common.strategy.HttpGetData;
import com.jzfq.fms.common.util.DateEnum;
import com.jzfq.fms.common.util.DateUtils;
import com.jzfq.fms.domain.ValidOrder;
import com.jzfq.fms.service.IRepayService;
import com.jzfq.fms.service.IValidOrderService;
import com.jzfq.fms.template.OrderHttpGetData;
import com.jzfq.fms.vo.RepayPlanInfo;
import org.apache.commons.collections.map.HashedMap;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 依据未处理有效订单生成还款计划任务
 *
 * @date 2016年9月29日 下午4:48:11
 */
@Component("generateRepayPlanTask")
public class GenerateRepayPlanTask {


    private static Logger log = LoggerFactory.getLogger(GenerateRepayPlanTask.class);

    @Autowired
    private IRepayService repayService;

    private static DateTime start = new DateTime("2014-05-30");
    private static DateTime end = new DateTime("2016-12-28");

    /**
     * 计算滞纳金
     */
    public void calculateOverdue() {
        log.info("扫描逾期并计算滞纳金开始");
        try {
            repayService.calOverDueFee();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("逾期扫描失败:{}", e);
        }
        log.info("扫描逾期并计算滞纳金结束");
    }

    /**
     * 每日为催收系统推送逾期的还款计划
     */
    public void pushOverdueRepayPlanForDay() {
        log.info("今日{}为催收系统推送逾期的还款计划开始",new DateTime(new Date()));
        try {
            List<RepayPlanInfo> list = repayService.getOverdueRepayPlanForDay();
            String listJsonStr  = JSON.toJSONString(list);
            Map<Object, Object> params = new HashedMap();
            params.put("list",listJsonStr);
            String pushOverdueRepayPlanTaskUrl = "http://pms-pre.juzifenqi.cn/openapi/repay/generate";
            HttpConnectionManager.doPost(pushOverdueRepayPlanTaskUrl, params);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("今日{}为催收系统推送逾期的还款计划失败:{}", new DateTime(new Date()), e);
        }
        log.info("今日{}为催收系统推送逾期的还款计划结束",new DateTime(new Date()));

    }


    /**
     * 同步历史逾期的还款计划给催收系统
     */
    public void pushOverdueRepayPlanForHistory() {
        log.info("同步历史逾期的还款计划给催收系统开始");
        String date = DateUtils.dateToStr(start.toDate(), DateEnum.DATE_SIMPLE);
        log.info("获取{}日期的数据", date);
        if (start.isAfter(end)) {
            log.info("所有数据已同步完毕,执行到{},结束的期限:{}", start, end);
            return;
        }
        Date histroyDate = start.toDate();
        try {
            List<RepayPlanInfo> list = repayService.getOverdueRepayPlanForHistory(histroyDate);
            Map<Object, Object> params = new HashedMap();
            String listJsonStr  = JSON.toJSONString(list);
            log.info("listJsonStr---->"+listJsonStr);
            params.put("list",listJsonStr);
            String pushOverdueRepayPlanTaskUrl = "http://pms-pre.juzifenqi.cn/openapi/repay/generate";
            HttpConnectionManager.doPost(pushOverdueRepayPlanTaskUrl, params);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获取数据失败：{},日期:{}", e, date);
        }
        start = start.plusDays(1);
        log.info("同步历史逾期的还款计划给催收系统结束");
    }



}
