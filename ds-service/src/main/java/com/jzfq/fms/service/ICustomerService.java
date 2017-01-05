package com.jzfq.fms.service;

import com.jzfq.fms.domain.Customer;

/**
 * Created by huyinglin on 2016/11/25.
 */
public interface ICustomerService {

    Customer selectCustomerById(Integer id);

}
