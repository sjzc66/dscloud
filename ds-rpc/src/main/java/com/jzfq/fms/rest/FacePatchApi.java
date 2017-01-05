package com.jzfq.fms.rest;


import com.jzfq.fms.common.common.JsonResult;
import com.jzfq.fms.domain.FacePatch;
import com.jzfq.fms.service.IFacePatchService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * Created by zhishuo on 11/9/16.
 */
@RestController
@RequestMapping("/api/facepatch")
public class FacePatchApi extends BaseApi{

    @Autowired
    private IFacePatchService facePatchService;

    /**
     * 添加/修改数据到面签补件--->(提供给风控系统)
     * @param customerId
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/insertorupdate",method = {RequestMethod.POST})
    public JsonResult insertOrUpdateFacePatch(@RequestParam Integer customerId,@RequestParam String orderId) {
        FacePatch facePatch = new FacePatch();
        facePatch.setOrderId(orderId);
        facePatchService.insertOrUpdateFacePatch(facePatch);
        return returnSuccess(facePatch.getOrderId(), "添加数据到面签补件完成");
    }


}
