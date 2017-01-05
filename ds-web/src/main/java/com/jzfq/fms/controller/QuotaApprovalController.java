package com.jzfq.fms.controller;

import com.jzfq.fms.common.DataTablePage;
import com.jzfq.fms.common.common.DataTable;
import com.jzfq.fms.common.common.PageVo;
import com.jzfq.fms.domain.Customer;
import com.jzfq.fms.domain.PresentOrder;
import com.jzfq.fms.domain.QuotaApproval;
import com.jzfq.fms.interceptor.PageList;
import com.jzfq.fms.service.ICustomerService;
import com.jzfq.fms.service.IQuotaApprovalService;
import com.sun.javafx.collections.MappingChange;
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
import java.util.Map;

@RequestMapping("/quotaApproval")
@Controller
public class QuotaApprovalController extends BaseController {

	private Logger logger=LoggerFactory.getLogger(QuotaApprovalController.class);

	@Autowired
	private IQuotaApprovalService quotaApprovalService;

	@Autowired
	private ICustomerService customerService;



	/**
	 * 额度审批列表
	 * @return
	 */
	@RequestMapping("/list")
	public String list(){
		return "quotaApproval/list";
	}


	/**
	 * 额度审批详情
	 * @return
	 */
	@RequestMapping("/approvalDetails")
	@ResponseBody
	public ModelAndView approvalDetails(HttpServletRequest request){
		String id=request.getParameter("id");
		QuotaApproval quotaApproval=quotaApprovalService.selectQuotaApprovalById(Integer.parseInt(id));
		Map<String,Object> approvalDetails=new HashedMap();
		ModelAndView modelAndView=new ModelAndView();
		Customer customer=null;
		if(quotaApproval!=null&&quotaApproval.getCustomerId()!=null){
			customer=customerService.selectCustomerById(quotaApproval.getCustomerId());
		}
		approvalDetails.put("quotaApproval",quotaApproval);
		approvalDetails.put("customer",customer);
		modelAndView.addObject("approvalDetails",approvalDetails);
		modelAndView.setViewName("/quotaApproval/approval_details");
        return modelAndView;
    }

	/**
	 * 组装案件列表数据
	 */
	@RequestMapping(value="/queryTableData",method = RequestMethod.GET)
	@ResponseBody
	public DataTable<PresentOrder> queryTableData(@DataTablePage PageVo vo) {
		PageList<QuotaApproval> list = quotaApprovalService.queryQuotaApprovalList(vo);
		DataTable data = resultToDataTable(list);
		return data;
	}

}
