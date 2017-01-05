package com.jzfq.fms.service.impl;


import com.jzfq.fms.common.common.PageVo;
import com.jzfq.fms.common.util.ServiceValidate;
import com.jzfq.fms.dao.PresentOrderMapper;
import com.jzfq.fms.domain.PresentOrder;
import com.jzfq.fms.interceptor.PageList;
import com.jzfq.fms.service.IPresentOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 提现订单
 */
@Service
public class PresentOrderServiceImpl implements IPresentOrderService {

	@Autowired
	private PresentOrderMapper presentOrderMapper;


	@Override
	public PresentOrder getPresentOrderById(int orderId) {
		return presentOrderMapper.selectByPrimaryKey(orderId);
	}

	@Override
	public PageList<PresentOrder> queryPresentOrderList(PageVo vo) {
		return presentOrderMapper.findPresentOrderList(vo, vo.getPageable());
	}

	@Override
	public boolean insertPresentOrder(PresentOrder presentOrder) {
		ServiceValidate.notNull(presentOrder.getRepayDate(),"还款时间不能为空");
		ServiceValidate.notNull(presentOrder.getLoanLimit(),"贷款额度不能为空");
		ServiceValidate.notNull(presentOrder.getPeriods(),"期数不能为空");
		ServiceValidate.notNull(presentOrder.getInterest(),"利率不能为空");
		ServiceValidate.notNull(presentOrder.getProductCode(),"产品编码不能为空");
		ServiceValidate.notNull(presentOrder.getMonthly(),"月供不能为空");
		ServiceValidate.notNull(presentOrder.getSource(),"订单来源不能为空");
		ServiceValidate.notNull(presentOrder.getOrderStatus(),"订单状态不能为空");
		ServiceValidate.notNull(presentOrder.getBankCode(),"银行卡号不能为空");
		ServiceValidate.notNull(presentOrder.getBankName(),"开户行不能为空");
		boolean result=presentOrderMapper.insert(presentOrder)>0?true:false;
		ServiceValidate.isTrue(result,"提现订单插入失败");
		return result;
	}

	/**
	 * 根据ID查询提现订单
	 * @param id
	 * @return
	 */
	@Override
	public PresentOrder selectPresentOrderById(Integer id) {
		return presentOrderMapper.selectByPrimaryKey(id);
	}
}
