package com.jzfq.fms.template;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jzfq.fms.common.constants.Constants;
import com.jzfq.fms.common.strategy.HttpGetData;
import com.jzfq.fms.common.util.DateEnum;
import com.jzfq.fms.common.util.DateUtils;
import com.jzfq.fms.domain.ValidOrder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by zhishuo on 11/16/16.
 */
public class OrderHttpGetData extends HttpGetData {

    private static Logger log = LoggerFactory.getLogger(OrderHttpGetData.class);

    @Override
    protected Object processData(Object obj) {
        log.info("php返回数据：{}", JSON.toJSON(obj));
        List<ValidOrder> result = new ArrayList<>();
        JSONObject json = JSON.parseObject(obj.toString());
        List<Map> datas = JSON.parseArray(json.get("data").toString(), Map.class);
        for (Map data : datas) {
            result.add(toOrder(data));
        }
        return result;
    }

    private ValidOrder toOrder(Map map) {
        ValidOrder order = new ValidOrder();
        order.setOrderId(Integer.valueOf(mapGetStr(map, "id")));
        order.setAmount(new BigDecimal(mapGetStr(map, "loan_amount")));
        order.setBehead(Byte.valueOf(mapGetStr(map, "is_kantouxi")));
        order.setCreateTime(new Date());
        order.setPeriod(Byte.valueOf(mapGetStr(map, "stage_number")));
        order.setRate(new BigDecimal(mapGetStr(map, "rate")));
        order.setRepayDate(DateUtils.toDate(mapGetStr(map, "first_payment_date"), DateEnum.DATE_SIMPLE));
        order.setType(Byte.valueOf(mapGetStr(map, "order_type")));
        order.setIsDeal(Constants.ZERO);
        order.setStoreOrderTime(DateUtils.toDate(mapGetStr(map, "add_time"), DateEnum.DATE_FORMAT));
        order.setFreeInterest(Byte.valueOf(mapGetStr(map, "is_interest_free")));
        order.setSource(Byte.valueOf(mapGetStr(map, "source")));
        order.setAlreadyRepaidPeriod(Byte.valueOf(mapGetStr(map, "already_repaid_stage")));
        order.setCapitalSide(Byte.valueOf(mapGetStr(map, "capital_side")));
        order.setCustomerId(Integer.valueOf(mapGetStr(map, "uid")));
        order.setCustomerJson(mapGetStr(map, "user_info"));
        order.setTitle(mapGetStr(map, "title"));
        return order;
    }

    private String mapGetStr(Map map, String key) {
        Object obj = map.get(key);
        return obj == null ? "" : obj.toString();
    }
}
