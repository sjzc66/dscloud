package com.jzfq.fms.rest;

import com.jzfq.AbstractTest;
import com.jzfq.fms.common.util.DateUtils;
import com.jzfq.fms.domain.FacePatch;
import com.jzfq.fms.service.IFacePatchService;
import net.sf.json.JSONObject;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * Created by liu on 2016/12/8.
 */
public class FacePatchApiTest extends AbstractTest{
    private Logger logger= LoggerFactory.getLogger(FacePatchApiTest.class);

    @Autowired
    private IFacePatchService facePatchService;

    @Test
    /**
     * 添加或者修改数据到面签补件 测试
     */
    public void insertOrUpdateFacePatchTest() throws Exception{
        FacePatch facePatch = new FacePatch();
        facePatch.setOrderId("121214");
        facePatch.setApplicationTime(new Date(System.currentTimeMillis()));
        facePatch.setPhone("15733446783");
        facePatch.setProductType((byte)2);
        facePatch.setUsername("测试");
        facePatch.setCustomerType((byte)1);
        facePatch.setIdNumber("188765234587652345");
        facePatch.setApprovalStatus((byte)2);
        facePatch.setRecommendCode("121215");
        facePatch.setRepaymentDate(DateUtils.plusDay(DateUtils.getNow(),15));
        facePatch.setImgTail("http://jzfq-fms-test.oss-cn-beijing.aliyuncs.com/img/1479779027639.jpg?Expires=1795139047&OSSAccessKeyId=LTAIi16Aj2Lcaqiv&Signature=gvYGpTUdkd5/DZDyCiFivn6s9%2Bc%3D");
        JSONObject json = JSONObject.fromObject(facePatch);
        System.out.println(json);
        //facePatchService.insertOrUpdateFacePatch(facePatch);
    }

}
