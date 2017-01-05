package com.jzfq.fms.controller;

import com.jzfq.fms.common.DataTablePage;
import com.jzfq.fms.common.common.DataTable;
import com.jzfq.fms.common.common.PageVo;
import com.jzfq.fms.domain.Customer;
import com.jzfq.fms.domain.PresentOrder;
import com.jzfq.fms.domain.RepayPlan;
import com.jzfq.fms.interceptor.PageList;
import com.jzfq.fms.service.ICustomerService;
import com.jzfq.fms.service.IPresentOrderService;
import com.jzfq.fms.service.IRepayService;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RequestMapping("/order")
@Controller
public class PresentOrderController extends BaseController {
	
	private Logger logger=LoggerFactory.getLogger(PresentOrderController.class);

	@Autowired
	private IPresentOrderService presentOrderService;

    @Autowired
    private IRepayService repayService;

	@Autowired
	private ICustomerService customerService;
	
	@RequestMapping("/list")
	public String list(){
		return "order/list";
	}

	/**
	 * 组装案件列表数据
	 */
	@RequestMapping(value="/queryTableData",method = RequestMethod.GET)
	@ResponseBody
	public DataTable<PresentOrder> queryTableData(@DataTablePage PageVo vo) {
		PageList<PresentOrder> list = presentOrderService.queryPresentOrderList(vo);
		DataTable data = resultToDataTable(list);
		return data;
	}

	@RequestMapping("/orderDetails")
	public ModelAndView orderDetails(HttpServletRequest request){
		String id=request.getParameter("id");
		PresentOrder presentOrder=presentOrderService.selectPresentOrderById(Integer.parseInt(id));
		Map<String,Object> presentOrderInfo=new HashedMap();
		if(presentOrder!=null&&presentOrder.getCustomerId()!=null){
			Customer customer=customerService.selectCustomerById(Integer.parseInt(presentOrder.getCustomerId()));
			List<RepayPlan> repayPlens=repayService.selectRepayPlanByOrderId(Integer.parseInt(presentOrder.getOrderId()));
			RepayPlan repayPlan=null;
			if(repayPlens.size()>0){
				repayPlan=repayPlens.get(0);
			}
			presentOrderInfo.put("repayPlan",repayPlan);
			presentOrderInfo.put("presentOrder",presentOrder);
			presentOrderInfo.put("customer",customer);
		}
		ModelAndView modelAndView=new ModelAndView();
		modelAndView.addObject("presentOrderInfo",presentOrderInfo);
		modelAndView.setViewName("/order/order_details");
        return modelAndView;
    }

    /**
     * 还款计划数据
     * @param
     * @return
     */
    @RequestMapping(value="/queryPlanData",method = RequestMethod.GET)
    @ResponseBody
    public DataTable<RepayPlan> queryPlanData(@DataTablePage PageVo vo) {
		PageList<RepayPlan> list = repayService.getPlan(vo);
        DataTable data = resultToDataTable(list);
        return data;
    }

}
