package com.jzfq.fms.controller;

import com.jzfq.fms.common.DataTablePage;
import com.jzfq.fms.common.common.DataTable;
import com.jzfq.fms.common.common.JsonResult;
import com.jzfq.fms.common.common.PageVo;
import com.jzfq.fms.domain.Product;
import com.jzfq.fms.domain.SysUser;
import com.jzfq.fms.interceptor.PageList;
import com.jzfq.fms.service.IProductService;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
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
import javax.servlet.http.HttpSession;
import java.util.List;

@RequestMapping("/product")
@Controller
public class ProductController extends BaseController {

	private Logger logger=LoggerFactory.getLogger(ProductController.class);

	@Autowired
	private IProductService productService;



	/**
	 * 月息产品列表
	 * @return
	 */
	@RequestMapping("/list")
	public String list(){
		return "product/list";
	}

	@RequestMapping("/daylist")
	public String daylist(){
		return "product/daylist";
	}

	/**
	 * 组装月息产品列表数据
	 */
	@RequestMapping(value="/queryMonthProduct",method = RequestMethod.GET)
	@ResponseBody
	public DataTable<Product> queryTableData(@DataTablePage PageVo vo) {
		PageList<Product> list = productService.getMonthProductsList(vo);
		DataTable data = resultToDataTable(list);
		return data;
	}

	/**
	 * 去往新增-产品
	 */
	@RequestMapping("/toadd")
	public ModelAndView toAdd(ModelAndView mv){
		List<Product> list = productService.getProducts();
		mv.addObject("list",list);
		mv.setViewName("product/product_add");
		return mv;
	}
	/**
	 * 添加/编辑-产品
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addProduct(Product product){
		SysUser user = (SysUser)SecurityUtils.getSubject().getPrincipal();
		product.setCreater(user.getUsername());
		if (product.getId() != null && product.getId() >0){
			productService.updateProduct(product);
		}else {
			productService.addProduct(product);
		}
		return "redirect:/product/list";
	}
	/**
	 * 删除产品
	 */
	@RequestMapping("/del/{id}")
	public @ResponseBody
	JsonResult delProduct(@PathVariable Integer id){
		boolean result = productService.delProduct(id);
		if (result){
			return returnSuccess("操作成功");
		}else{
			return JsonResult.createErrorMsg("操作失败");
		}
	}

	/**
	 * 去往编辑-产品信息
	 */
	@RequestMapping("/toedit/{id}")
	public ModelAndView toEdit(@PathVariable Integer id, ModelAndView mv){
		List<Product> list = productService.getProducts();
		Product product = productService.getProductById(id);
		mv.addObject("list",list);
		mv.addObject("product",product);
		mv.setViewName("product/product_add");
		return mv;
	}

	/**
	 * 组装日息产品列表数据
	 */
	@RequestMapping(value="/queryDayProduct",method = RequestMethod.GET)
	@ResponseBody
	public DataTable<Product> queryDayTableData(@DataTablePage PageVo vo) {
		PageList<Product> list = productService.getDayProductsList(vo);
		DataTable data = resultToDataTable(list);
		return data;
	}

	/**
	 * 去往新增-日息产品
	 */
	@RequestMapping("/toaddday")
	public ModelAndView toAddDay(ModelAndView mv){
		List<Product> list = productService.getProducts();
		mv.addObject("list",list);
		mv.setViewName("product/dayproduct_add");
		return mv;
	}
	/**
	 * 去往编辑-日息产品信息
	 */
	@RequestMapping("/toeditday/{id}")
	public ModelAndView toEditDay(@PathVariable Integer id, ModelAndView mv){
		List<Product> list = productService.getProducts();
		Product product = productService.getProductById(id);
		mv.addObject("list",list);
		mv.addObject("product",product);
		mv.setViewName("product/dayproduct_add");
		return mv;
	}
}
