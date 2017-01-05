package com.jzfq.fms.service;

import com.jzfq.fms.common.common.PageVo;
import com.jzfq.fms.domain.SysUser;
import com.jzfq.fms.interceptor.PageList;

import java.util.Set;

/**
 * Created by zhishuo on 9/27/16.
 */
public interface ISysUserService {


    SysUser addUser(SysUser user);

    boolean delUser(int userId);

    boolean updateUser(SysUser user);

    SysUser getUserById(int userId);

    SysUser getUserByUserName(String userName);

    SysUser getUserByUserNameAndPwd(String userName, String pwd);


    boolean modifyPassword(SysUser user, SysUser u);

    PageList<SysUser> findUserList(PageVo vo);

    Set<String> getRoleNameSetByUsername(String username);

    Set<String> getAuthSetByUsername(String username);
}
