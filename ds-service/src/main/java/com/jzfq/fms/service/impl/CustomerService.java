package com.jzfq.fms.service.impl;

import com.jzfq.fms.dao.CustomerMapper;
import com.jzfq.fms.domain.Customer;
import com.jzfq.fms.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by huyinglin on 2016/11/25.
 */
@Service
public class CustomerService implements ICustomerService {

    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public Customer selectCustomerById(Integer id) {
        return customerMapper.selectByPrimaryKey(id);
    }
}
