package com.jzfq.fms.service;

import com.jzfq.fms.common.common.PageVo;
import com.jzfq.fms.domain.RepayPlan;
import com.jzfq.fms.domain.ValidOrder;
import com.jzfq.fms.interceptor.PageList;

import java.util.List;

public interface IValidOrderService {

	/**
	 * 添加有效订单
	 * @param d
	 * @return
	 */
	boolean addValidOrder(ValidOrder d);

	/**
	 * 通过有效订单id删除有效订单
	 * @param validOrderId
	 * @return
	 */
	boolean delValidOrder(int validOrderId);

	/**
	 * 更新有效订单
	 * @param d
	 * @return
	 */
	boolean updateValidOrder(ValidOrder d);

	/**
	 * 通过有效订单id查询有效订单
	 * @param validOrderId
	 * @return
	 */
	ValidOrder getValidOrderById(int validOrderId);

    List<ValidOrder> queryValidOrderListByIsDeal();

    void addBatchValidOrder(List<ValidOrder> list);

    PageList<ValidOrder> queryValidOrderList(PageVo vo);

    void addValidOrderAndAddRepayPlan(ValidOrder validOrder);

	List<RepayPlan> getRepayPlansByValidOrder(ValidOrder validOrder);
}
