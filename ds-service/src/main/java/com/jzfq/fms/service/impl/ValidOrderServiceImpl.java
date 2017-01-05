package com.jzfq.fms.service.impl;


import com.jzfq.fms.common.PlanClient;
import com.jzfq.fms.common.common.PageVo;
import com.jzfq.fms.common.constants.Constants;
import com.jzfq.fms.common.enums.RepayType;
import com.jzfq.fms.common.util.DateUtils;
import com.jzfq.fms.common.util.ServiceValidate;
import com.jzfq.fms.dao.ProductMapper;
import com.jzfq.fms.dao.RepayPlanMapper;
import com.jzfq.fms.dao.ValidOrderMapper;
import com.jzfq.fms.domain.Product;
import com.jzfq.fms.domain.RepayPlan;
import com.jzfq.fms.domain.ValidOrder;
import com.jzfq.fms.interceptor.PageList;
import com.jzfq.fms.manager.OrderManager;
import com.jzfq.fms.service.IValidOrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static com.jzfq.fms.common.util.JdbcUtil.isSuccess;


/**
 * 有效订单
 */
@Service
public class ValidOrderServiceImpl implements IValidOrderService {

	@Autowired
	private ValidOrderMapper validOrderMapper;
	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private OrderManager orderManager;


	@Override
	public boolean addValidOrder(ValidOrder d) {
		ServiceValidate.isTrue(!org.springframework.util.StringUtils.isEmpty(d), "有效订单对象不能为空");
		d.setCreateTime(new Date(System.currentTimeMillis()));
		d.setIsDeal((byte)0);
		d.setDelFlag((byte)1);
		return validOrderMapper.insertSelective(d)>0 ? true : false;
	}

	@Override
	public boolean delValidOrder(int validOrderId) {
		ValidOrder d= validOrderMapper.selectByPrimaryKey(validOrderId);
		d.setDelFlag((byte)1);
		return validOrderMapper.updateByPrimaryKeySelective(d) > 0 ? true : false;
	}

	@Override
	public boolean updateValidOrder(ValidOrder d) {
		//如果id为空 或者对象为空
		if (org.springframework.util.StringUtils.isEmpty(d) || org.springframework.util.StringUtils.isEmpty(d.getId())) {
			ServiceValidate.isTrue(Boolean.FALSE, "有效订单对象不能为空");
		}
		d.setUpdateTime(new Date(System.currentTimeMillis()));
		int result = validOrderMapper.updateByPrimaryKeySelective(d);
		boolean state = isSuccess(result);
		ServiceValidate.isTrue(state, "更新失败");
		return state;
	}

	@Override
	public ValidOrder getValidOrderById(int validOrderId) {
		return validOrderMapper.selectByPrimaryKey(validOrderId);
	}

	@Override
	public List<ValidOrder> queryValidOrderListByIsDeal() {
		return validOrderMapper.queryValidOrderListByIsDeal();
	}

	@Override
	@Transactional
	public void addBatchValidOrder(List<ValidOrder> list) {
		//TODO 应该是批量插入的sql
		for(ValidOrder vo:list){
			validOrderMapper.insertSelective(vo);
		}
	}

	@Override
	public PageList<ValidOrder> queryValidOrderList(PageVo vo) {
		return validOrderMapper.findValidOrderList(vo, vo.getPageable());
	}

	@Override
	public void addValidOrderAndAddRepayPlan(ValidOrder vo) {
        //验证
		serviceValidate(vo);
		ValidOrder v= validOrderMapper.getOrderByOrderId(vo.getOrderId());
		if(v!=null){
			return;
		}
		//查询产品
		vo.setCreateTime(new Date());
		Product product = productMapper.getProduct(vo.getType(), vo.getPeriod());
		//根据传入的产品生成还款计划
		List<RepayPlan> plans = PlanClient.generate(vo, product.getId(), vo.getType());
		//保存订单信息 和还款计划
		orderManager.save(vo, plans);
	}

	@Override
	public List<RepayPlan> getRepayPlansByValidOrder(ValidOrder vo) {
		//验证
		serviceValidate(vo);
		//查询产品
		Product product = productMapper.getProduct(vo.getType(), vo.getPeriod());
		//根据传入的产品生成还款计划
		List<RepayPlan> plans = PlanClient.generate(vo, product.getId(), vo.getType());
		return plans;
	}


	private void serviceValidate(ValidOrder vo){
		ServiceValidate.notNull(vo.getOrderId(), "订单号不能为空");
		ServiceValidate.notNull(vo.getRepayDate(), "还款日不能为空");
		ServiceValidate.notNull(vo.getStoreOrderTime(), "商城下单日期不能为空");
		ServiceValidate.notNull(vo.getPeriod(), "期数不能为空");
		ServiceValidate.notNull(vo.getType(), "类型不能为空");
		ServiceValidate.notNull(vo.getRate(), "利率不能为空");
		ServiceValidate.notNull(vo.getAmount(), "申请金额不能为空");
		ServiceValidate.notNull(vo.getCustomerId(), "客戶ID不能为空");
		ServiceValidate.notNull(vo.getBehead(), "是否是砍头息不能为空");
		ServiceValidate.notNull(vo.getFreeInterest(), "是否免息不能为空");
		ServiceValidate.notNull(vo.getSource(), "来源不能为空");
		ServiceValidate.notNull(vo.getCapitalSide(), "资金方不能为空");
		ServiceValidate.notNull(vo.getType()+"-"+vo.getPeriod(), "该产品不存在");
	}

}
