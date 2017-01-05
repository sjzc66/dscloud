package com.jzfq.fms.service.impl;


import com.aliyun.oss.OSSClient;
import com.jzfq.fms.common.common.PageVo;
import com.jzfq.fms.common.util.DateEnum;
import com.jzfq.fms.common.util.DateUtils;
import com.jzfq.fms.common.util.OSSUtil;
import com.jzfq.fms.common.util.ServiceValidate;
import com.jzfq.fms.dao.FacePatchMapper;
import com.jzfq.fms.domain.FacePatch;
import com.jzfq.fms.interceptor.PageList;
import com.jzfq.fms.service.IFacePatchService;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;


/**
 * 面签补件service
 */
@Service
public class FacePatchServiceImpl implements IFacePatchService {

	@Autowired
	private FacePatchMapper facePatchMapper;


	@Override
	public FacePatch getFacePatchById(int facePatchId) {
		FacePatch fp = facePatchMapper.selectByPrimaryKey(facePatchId);
		String atStr = DateUtils.dateToStr(fp.getApplicationTime(),DateEnum.DATE_SIMPLE);
		fp.setApplicationTimeStr(atStr);
		String rdStr = DateUtils.dateToStr(fp.getRepaymentDate(),DateEnum.DATE_SIMPLE);
		fp.setRepaymentDateStr(rdStr);
		return fp;
	}

	@Override
	public PageList<FacePatch> queryFacePatchList(PageVo vo) {
		return facePatchMapper.findFacePatchList(vo, vo.getPageable());
	}

	@Override
	public void uploadImg2OSS(Integer id, MultipartFile file) {

		if (file.getSize() > 10 * 1024 * 1024) {
			ServiceValidate.isTrue(false, "上传图片大小不能超过10M");
		}
		OSSClient client = OSSUtil.getOSSClient();
		String bucketName = "jzfq-fms-test";
		OSSUtil.createBucket(client,bucketName);
		String diskName = "img/";

		//生成文件名称
		String originalFilename = file.getOriginalFilename();
		String substring = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
		Random random = new Random();
		String fileName = random.nextInt(10000) + System.currentTimeMillis() + substring;

		CommonsMultipartFile cf= (CommonsMultipartFile)file;
		DiskFileItem fi = (DiskFileItem)cf.getFileItem();
		File f = fi.getStoreLocation();

		//调用oss上传图片
		try {
			InputStream inputStream = file.getInputStream();
			String md5key = OSSUtil.uploadObject2OSS(client, f, fileName, bucketName, diskName);
		} catch (IOException e) {
			e.printStackTrace();
			ServiceValidate.isTrue(false, "上传图片到OSS出现异常");
		}

		//生成上传到oss的图片地址
		String url = OSSUtil.getUrl(bucketName,diskName + fileName);
		//修改数据图片地址
		FacePatch fc= new FacePatch();
		fc.setId(id);
		fc.setImgTail(url);
		facePatchMapper.updateByPrimaryKeySelective(fc);
		// TODO: 2016/12/26 通知风控
	}

    @Override
    public void insertOrUpdateFacePatch(FacePatch facePatch) {
		//先查此面签补件是否已经在本地数据库
		FacePatch fp = facePatchMapper.selectByOrderId(facePatch.getOrderId());
		if(fp!=null){
			//状态变更为再次上传照片 0没有上传   1 已经上传  2再次上传
			fp.setApprovalStatus((byte)2);
			facePatchMapper.updateByPrimaryKeySelective(fp);
		}else{
			facePatch.setApprovalStatus((byte)0);
			facePatchMapper.insert(facePatch);
		}
    }


}
