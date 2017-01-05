package com.jzfq.fms.service;

import com.jzfq.fms.common.common.PageVo;
import com.jzfq.fms.domain.PresentOrder;
import com.jzfq.fms.interceptor.PageList;

public interface IPresentOrderService {


	/**
	 * 通过订单id查询订单
	 * @param orderId
	 * @return
	 */
	PresentOrder getPresentOrderById(int orderId);

	/**
	 * 查询所有订单
	 * @return
	 */
	PageList<PresentOrder> queryPresentOrderList(PageVo vo);

	/**
	 * 插入提现订单
	 * @param presentOrder
	 * @return
	 */
	boolean insertPresentOrder(PresentOrder presentOrder);


	PresentOrder selectPresentOrderById(Integer id);

}
