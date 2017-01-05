package com.jzfq.fms.vo;

import com.jzfq.fms.domain.SysAuth;

import java.util.List;

public class RootMenu {

    private SysAuth rootMenuSysAuth;

    private List<ChildMenu> childMenus;//子集合

    public static class ChildMenu {

        private SysAuth childMenuSysAuth;

        private List<SysAuth> childButtons;

        public List<SysAuth> getChildButtons() {
            return childButtons;
        }

        public void setChildButtons(List<SysAuth> childButtons) {
            this.childButtons = childButtons;
        }

        public SysAuth getChildMenuSysAuth() {
            return childMenuSysAuth;
        }

        public void setChildMenuSysAuth(SysAuth childMenuSysAuth) {
            this.childMenuSysAuth = childMenuSysAuth;
        }
    }

    public List<ChildMenu> getChildMenus() {
        return childMenus;
    }

    public void setChildMenus(List<ChildMenu> childMenus) {
        this.childMenus = childMenus;
    }

    public SysAuth getRootMenuSysAuth() {
        return rootMenuSysAuth;
    }

    public void setRootMenuSysAuth(SysAuth rootMenuSysAuth) {
        this.rootMenuSysAuth = rootMenuSysAuth;
    }
}