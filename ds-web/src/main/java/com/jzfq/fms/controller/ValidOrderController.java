package com.jzfq.fms.controller;

import com.jzfq.fms.common.DataTablePage;
import com.jzfq.fms.common.common.DataTable;
import com.jzfq.fms.common.common.PageVo;
import com.jzfq.fms.domain.Customer;
import com.jzfq.fms.domain.PresentOrder;
import com.jzfq.fms.domain.RepayPlan;
import com.jzfq.fms.domain.ValidOrder;
import com.jzfq.fms.interceptor.PageList;
import com.jzfq.fms.service.ICustomerService;
import com.jzfq.fms.service.IPresentOrderService;
import com.jzfq.fms.service.IRepayService;
import com.jzfq.fms.service.IValidOrderService;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RequestMapping("/validorder")
@Controller
public class ValidOrderController extends BaseController {
	
	private Logger logger=LoggerFactory.getLogger(ValidOrderController.class);

	@Autowired
	private IValidOrderService validOrderService;

	@Autowired
	private IRepayService repayService;

	@RequestMapping("/tolist")
	public String list(){
		return "finance/repayplan_list";
	}

	/**
	 * 组装还款订单列表数据
	 */
	@RequestMapping(value="/queryTableData",method = RequestMethod.GET)
	@ResponseBody
	public DataTable<ValidOrder> queryTableData(@DataTablePage PageVo vo) {
		PageList<ValidOrder> list = validOrderService.queryValidOrderList(vo);
		DataTable data = resultToDataTable(list);
		return data;
	}


	/**
	 * 去往查看还款计划详情
	 */
	@RequestMapping("/todetail/{orderId}")
	public ModelAndView todetail(@PathVariable Integer orderId, ModelAndView mv){
		mv.addObject("orderId", orderId);
		mv.setViewName("finance/repayplan_detail");
		return mv;
	}

    /**
     * 组装订单还款计划列表数据
     * @param vo
     * @return
     */
    @RequestMapping(value="/queryTableDataDetail",method = RequestMethod.GET)
    @ResponseBody
    public DataTable<RepayPlan> queryTableDataDetail(@DataTablePage PageVo vo) {
        PageList<RepayPlan> list = repayService.getPlan(vo);
        DataTable data = resultToDataTable(list);
        return data;
    }

}
