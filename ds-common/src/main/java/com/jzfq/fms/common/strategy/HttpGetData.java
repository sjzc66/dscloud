package com.jzfq.fms.common.strategy;

import com.jzfq.fms.common.common.ResponseDTO;
import com.jzfq.fms.common.common.ReturnCode;
import com.jzfq.fms.common.httpclient.HttpConnectionManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by zhishuo on 11/16/16.
 */
public abstract class HttpGetData {

    private static Logger log = LoggerFactory.getLogger(HttpGetData.class);

    public Object doGet(String url) {
        ResponseDTO rd = HttpConnectionManager.doGet(url);
        if (rd.getCode() == ReturnCode.REQUEST_SUCCESS.code()) {
            return processData(rd.getAttach());
        }
        log.error("请求：{}发生错误,返回结果：{}", url, rd.getAttach());
        return null;
    }


    public Object doPost(String url, Map<Object, Object> params) {
        ResponseDTO rd = HttpConnectionManager.doPost(url, params);
        if (rd.getCode() == ReturnCode.REQUEST_SUCCESS.code()) {
            return processData(rd.getAttach());
        }
        log.error("请求：{}发生错误,请求参数：{} ,返回结果：{}", new Object[]{url, params, rd.getAttach()});
        return null;

    }


    protected abstract Object processData(Object obj);
}
