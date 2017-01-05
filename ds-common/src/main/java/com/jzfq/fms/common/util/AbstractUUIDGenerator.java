package com.jzfq.fms.common.util;

import java.net.InetAddress;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by huyinglin on 2016/12/7.
 */
public abstract class AbstractUUIDGenerator {

	private static final int IP;

	private static String SERVER_ID="888";
	static {
		int ipadd;
		try {
			ipadd = toInt(InetAddress.getLocalHost().getAddress());
		} catch (final Exception e) {
			ipadd = 0;
		}
		IP = ipadd;
	}

	public AbstractUUIDGenerator() {
	}

	public static int toInt(final byte[] bytes) {
		int result = 0;
		for (int i = 0; i < 4; i++) {
			result = (result << 8) - Byte.MIN_VALUE + bytes[i];
		}
		return result;
	}

	private static volatile AtomicInteger counter = new AtomicInteger(0);

	protected int getCount() {
		int count = counter.getAndIncrement();
		// 超过4位从头开始, 避免id号后缀超过4位
		if (count >= 9999) {
			counter.set(0);
		}
		return count;
	}

	protected void resetCounter() {
		counter.set(0);
	}

	protected String getServerID() {
		return SERVER_ID;
	}

}