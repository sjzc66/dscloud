package com.jzfq.fms.rest;


import com.jzfq.fms.common.common.JsonResult;
import com.jzfq.fms.common.exception.ServiceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;


/**
 * 基础封装
 */
public class BaseApi {

    private static final Logger log = LoggerFactory.getLogger(BaseApi.class);


    /**
     * 处理时间的转换器，默认格式是yyyy-MM-dd HH:mm:ss可以被重载
     *
     * @param binder
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }


    @ExceptionHandler
    @ResponseBody
    public final JsonResult handleException(HttpServletRequest request, Exception ex) {

        if (ex instanceof ServiceException) {
            ServiceException be = (ServiceException) ex;
            ex.printStackTrace();
            return JsonResult.createErrorMsg(be.getMessage());
        }
        ex.printStackTrace();
        return JsonResult.createErrorMsg("系统异常");
    }


    public static JsonResult returnSuccess(String msg) {
        return JsonResult.createSuccess(msg);
    }

    public static JsonResult returnSuccess(Object obj, String msg) {
        return JsonResult.createSuccess(obj, msg);
    }

    public static JsonResult returnSuccess() {
        return JsonResult.createSuccess();
    }

    public static JsonResult returnSuccess(Object obj) {
        return JsonResult.createSuccess(obj);
    }

    public JsonResult returnFaild(String msg) {
        return JsonResult.createErrorMsg(msg);

    }

}
