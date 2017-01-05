package com.jzfq.fms.service.impl;

import com.jzfq.fms.common.util.UUIDHexGenerator;
import com.jzfq.fms.service.IDGeneratorService;
import org.springframework.stereotype.Service;


@Service
public class IDGeneratorServiceImpl implements IDGeneratorService {

	@Override
	public String generate() {
		String uuid = UUIDHexGenerator.generator();
		return uuid;
	}
}
