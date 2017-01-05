package com.jzfq.fms.controller;

import com.jzfq.fms.common.DataTablePage;
import com.jzfq.fms.common.common.DataTable;
import com.jzfq.fms.common.common.JsonResult;
import com.jzfq.fms.common.common.PageVo;
import com.jzfq.fms.domain.SysRole;
import com.jzfq.fms.domain.SysUser;
import com.jzfq.fms.interceptor.PageList;
import com.jzfq.fms.service.ISysRoleService;
import com.jzfq.fms.service.ISysUserService;
import com.jzfq.fms.shiro.PasswordHelper;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by zhishuo on 9/27/16.
 */
@Controller
@RequestMapping("/sysuser")
public class SysUserController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger("SysUserController");

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private ISysRoleService sysRoleService;


    /**
     * 去往用户管理列表
     */
    @RequestMapping("/tolist")
    public ModelAndView tolist(ModelAndView mv){
        mv.setViewName("system/sysuser_list");
        return mv;
    }

    /**
     * 组装用户列表数据
     */
    @RequestMapping(value="/queryTableData",method = RequestMethod.GET)
    @ResponseBody
    public DataTable<SysUser> queryTableData(@DataTablePage PageVo vo) {
        PageList<SysUser> list = sysUserService.findUserList(vo);
        DataTable data = resultToDataTable(list);
        return data;

    }

    /**
     * 去往新增-用户
     */
    @RequestMapping("/toadd")
    public ModelAndView toAdd(ModelAndView mv){
        List<SysRole> list = sysRoleService.queryAllRoleList();
        mv.addObject("list", list);
        mv.setViewName("system/sysuser_add");
        return mv;
    }

    /**
     * 去往编辑-用户
     */
    @RequestMapping("/toedit/{id}")
    public ModelAndView toEdit(@PathVariable Integer id, ModelAndView mv){
        List<SysRole> list = sysRoleService.queryAllRoleList();
        SysUser user = sysUserService.getUserById(id);
        String[] roleIds = user.getRoleIds().split(",");
        for(SysRole r:list){
            for(String roleId:roleIds) {
                if (r.getId()== Integer.parseInt(roleId)) {
                    r.setDelFlag((byte)1);
                }
            }
        }
        mv.addObject("list", list);
        mv.addObject("user", user);
        mv.setViewName("system/sysuser_add");
        return mv;
    }

    /**
     * 添加/编辑-用户
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addUser(SysUser user) {

        if(user.getId() != null && user.getId() > 0){
            sysUserService.updateUser(user);
        }else{
            SysUser u = PasswordHelper.encryptPassword(user);
            sysUserService.addUser(u);
        }
        return "redirect:/sysuser/tolist";
    }

    /**
     * 删除用户
     */
    @RequestMapping("/del/{id}")
    public @ResponseBody
    JsonResult delUser(@PathVariable Integer id){
        boolean result = sysUserService.delUser(id);
    	if(result){
    		return returnSuccess("操作成功！");
    	}else{
    		return JsonResult.createErrorMsg("操作失败！");
    	}
    }

    /**
     * 验证新增账号是否已经注册
     */
    @RequestMapping("/only/{userName}")
    public @ResponseBody JsonResult onlyUser(@PathVariable String userName){
        SysUser user = sysUserService.getUserByUserName(userName);
        if(user==null){
            return returnSuccess("账号还没注册");
        }else{
            return JsonResult.createErrorMsg("账号已经注册");
        }
    }

    /**
     * 修改登录密码  oldpassword  password
     */
    @RequestMapping(value = "/modifyPassword")
    public String modifyPassword(@RequestParam (value = "newPassword",required = true) String newPassword) {
        SysUser user = (SysUser)SecurityUtils.getSubject().getPrincipal();
        log.info("user----------->"+user.getUsername());
        if(user == null){//如果session过期了，此时user为null
            return "redirect:/index";
        }
        user.setPassword(newPassword);
        SysUser u = PasswordHelper.encryptPassword(user);
        sysUserService.modifyPassword(user, u);
        //清空缓存
        SecurityUtils.getSubject().logout();
        return "redirect:/index";
    }

} 
