package com.jzfq.fms.shiro;


import org.apache.shiro.authc.UsernamePasswordToken;

import javax.servlet.ServletRequest;

/**
 * Created by zhishuo on 10/29/16.
 */
public class CaptchaUsernamePasswordToken extends UsernamePasswordToken {
    private String captcha;
    private ServletRequest request;
    private boolean skipVerification;

    public ServletRequest getRequest() {
        return request;
    }

    public void setRequest(ServletRequest request) {
        this.request = request;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public boolean isSkipVerification() {
        return skipVerification;
    }

    public void setSkipVerification(boolean skipVerification) {
        this.skipVerification = skipVerification;
    }

    public CaptchaUsernamePasswordToken(String username, String password, boolean rememberMe, String host,
                                        String captcha) {
        super(username, password, rememberMe, host);
        this.captcha = captcha;
        skipVerification = false;
    }

    public CaptchaUsernamePasswordToken(String username, String password, boolean rememberMe, String host,
                                        boolean skipVerification) {
        super(username, password, rememberMe, host);
        this.skipVerification = skipVerification;
    }

    public CaptchaUsernamePasswordToken() {
        super();
    }
}
