package com.jzfq.fms.controller;

import com.jzfq.fms.common.DataTablePage;
import com.jzfq.fms.common.common.DataTable;
import com.jzfq.fms.common.common.PageVo;
import com.jzfq.fms.domain.FacePatch;
import com.jzfq.fms.interceptor.PageList;
import com.jzfq.fms.service.IFacePatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhishuo on 10/28/16.
 */
@Controller
@RequestMapping("/facepatch")
public class FacePatchController extends BaseController{

    @Autowired
    private IFacePatchService facePatchService;

    /**
     * 去往面签补件列表
     */
    @RequestMapping("/tolist")
    public ModelAndView tolist(ModelAndView mv){
        mv.setViewName("market/facepatch_list");
        return mv;
    }

    /**
     * 组装面签补件列表数据
     */
    @RequestMapping(value="/queryTableData",method = RequestMethod.GET)
    @ResponseBody
    public DataTable<FacePatch> queryTableData(@DataTablePage PageVo vo) {
        PageList<FacePatch> list = facePatchService.queryFacePatchList(vo);
        DataTable data = resultToDataTable(list);
        return data;

    }

    /**
     * 去往查看详情-面签补件
     */
    @RequestMapping("/todetail/{id}")
    public ModelAndView toEdit(@PathVariable Integer id, ModelAndView mv){
        mv.addObject("facePatch", facePatchService.getFacePatchById(id));
        mv.setViewName("market/facepatch_detail");
        return mv;
    }


    @RequestMapping(value = "/uploadimg", method = RequestMethod.POST)
    public String uploadImg(HttpServletRequest request,MultipartFile file) {
        Integer id = Integer.parseInt(request.getParameter("id"));
        facePatchService.uploadImg2OSS(id, file);//此处是调用上传服务接口
        return "redirect:/facepatch/tolist";
    }

}
