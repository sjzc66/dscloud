package com.jzfq.fms.common;

import com.jzfq.fms.common.common.PageVo;
import com.jzfq.fms.common.common.Pageable;
import com.jzfq.fms.common.util.StringUtil;

import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.AbstractNamedValueMethodArgumentResolver;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhishuo on 11/9/16.
 */
public class DataTableMethodArgumentResolver extends AbstractNamedValueMethodArgumentResolver implements
        WebArgumentResolver {

    private static final Logger log = LoggerFactory.getLogger(DataTableMethodArgumentResolver.class);

    @Override
    public Object resolveArgument(MethodParameter methodParameter, NativeWebRequest webRequest) throws Exception {
        if (!this.supportsParameter(methodParameter)) {
            return WebArgumentResolver.UNRESOLVED;
        }
        return this.resolveArgument(methodParameter, null, webRequest, null);
    }

    @Override
    protected NamedValueInfo createNamedValueInfo(MethodParameter parameter) {
        DataTablePage annotation = parameter.getParameterAnnotation(DataTablePage.class);
        return new DataTablePageNamedValueInfo(annotation);
    }

    @Override
    protected Object resolveName(String name, MethodParameter parameter, NativeWebRequest webRequest) throws Exception {
        PageVo vo = new PageVo();

        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);


        request.setCharacterEncoding("UTF-8");
        Map<String, Object> parameters = new HashedMap(20);
        Enumeration enums = request.getParameterNames();
        while (enums.hasMoreElements()) {
            String key = (String) enums.nextElement();
            if (!key.startsWith("columns")) {
                String str = request.getParameter(key);
                if (StringUtil.checkNotEmpty(str)) {
                    parameters.put(key, str);
                }
            }
        }
        vo.setParameters(parameters);

        String start = request.getParameter("start");
        String length = request.getParameter("length");
        //封装排序参数
        String order = request.getParameter("order[0][column]");//排序的列号  
        String orderDir = request.getParameter("order[0][dir]");//排序的顺序asc or desc  
        String orderColumn = request.getParameter("columns[" + order + "][data]");//排序的列

        Map<String, Object> sortParameters = new HashMap<String, Object>();
        sortParameters.put(orderColumn, orderDir);
        vo.setSort(sortParameters);

        Pageable pageable = new Pageable();
        pageable.setCurrentPage((Integer.parseInt(start) / Integer.parseInt(length) + 1));
        pageable.setPageSize(Integer.valueOf(length));
        String search = request.getParameter("search[value]");
        vo.setPageable(pageable);
        return vo;
    }

    @Override
    protected void handleMissingValue(String name, MethodParameter parameter) throws ServletException {
        String paramType = parameter.getParameterType().getName();
        throw new ServletRequestBindingException("Missing request json parameter '" + name
                + "' for method parameter type [" + paramType + "]");
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        if (parameter.hasParameterAnnotation(DataTablePage.class)) {
            return true;
        }
        return false;
    }

    private class DataTablePageNamedValueInfo extends NamedValueInfo {

        private DataTablePageNamedValueInfo() {
            super("", false, null);
        }

        private DataTablePageNamedValueInfo(DataTablePage annotation) {
            super(annotation.value(), annotation.required(), null);
        }
    }
}
