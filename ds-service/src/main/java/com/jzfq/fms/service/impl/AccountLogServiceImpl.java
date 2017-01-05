package com.jzfq.fms.service.impl;


import com.jzfq.fms.dao.AccountLogMapper;
import com.jzfq.fms.dao.AccountMapper;
import com.jzfq.fms.service.IAccountLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 额度账号流水service
 */
@Service
public class AccountLogServiceImpl implements IAccountLogService {

	@Autowired
	private AccountMapper accountMapper;

	@Autowired
	private AccountLogMapper accountLogMapper;





}
