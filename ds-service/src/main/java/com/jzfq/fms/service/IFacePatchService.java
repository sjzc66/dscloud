package com.jzfq.fms.service;

import com.jzfq.fms.common.common.PageVo;
import com.jzfq.fms.domain.FacePatch;
import com.jzfq.fms.interceptor.PageList;
import org.springframework.web.multipart.MultipartFile;

public interface IFacePatchService {


	/**
	 * 通过面签补件id查询订单
	 * @param facePatchId
	 * @return
	 */
	FacePatch getFacePatchById(int facePatchId);

	/**
	 * 查询所有面签补件
	 * @return
	 */
	PageList<FacePatch> queryFacePatchList(PageVo vo);

	void uploadImg2OSS(Integer id, MultipartFile file);

    void insertOrUpdateFacePatch(FacePatch facePatch);

}
