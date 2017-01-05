package com.jzfq.fms.service.impl;

import com.jzfq.fms.dao.TransDetailMapper;
import com.jzfq.fms.domain.TransDetail;
import com.jzfq.fms.service.ITransDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by huyinglin on 2016/12/13.
 */
@Service
public class TransDetailServiceImpl implements ITransDetailService {

    @Autowired
    private TransDetailMapper transDetailMapper;

    @Override
    public void addTransDetail(TransDetail transDetail) {
        transDetailMapper.insert(transDetail);
    }
}
