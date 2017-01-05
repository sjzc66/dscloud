package com.jzfq.fms.job;

import com.jzfq.fms.common.strategy.HttpGetData;
import com.jzfq.fms.common.util.DateEnum;
import com.jzfq.fms.common.util.DateUtils;
import com.jzfq.fms.domain.ValidOrder;
import com.jzfq.fms.service.IRepayService;
import com.jzfq.fms.template.OrderHttpGetData;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * 从商城抓住有效订单并插入数据库任务
 *
 * @date 2016年9月29日 下午4:48:11
 */
@Component("grabValidOrderTask")
public class GrabValidOrderTask {

    private static Logger log = LoggerFactory.getLogger(GrabValidOrderTask.class);

    @Value("${grabValidOrderTask.url}")
    private String grabValidOrderTaskUrl;

    @Autowired
    private IRepayService repayService;


    private static DateTime start = new DateTime("2014-05-30");

    private static DateTime end = new DateTime("2017-01-03");

    private static HttpGetData client = new OrderHttpGetData();


    private List<ValidOrder> getData(String url) {
        List<ValidOrder> list = (List<ValidOrder>) client.doGet(url);
        return list;
    }


    public void getHistoryData() {
        log.info("从商城同步历史订单开始");
        String date = DateUtils.dateToStr(start.toDate(), DateEnum.DATE_SIMPLE);
        log.info("获取{}日期的数据", date);
        if (start.isAfter(end)) {
            log.info("所有数据已同步完毕,执行到{},结束的期限:{}", start, end);
            return;
        }
        try {
            List<ValidOrder> orders = getData(grabValidOrderTaskUrl + date);
            try {
                repayService.saveDataAndRepayPlan(orders);
            } catch (Exception e) {
                e.printStackTrace();
                log.error("插入数据库异常:{}，日期：{}", e, date);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获取数据失败：{},日期:{}", e, date);
        }
        start = start.plusDays(1);
        log.info("从商城同步历史订单结束");
    }

}
