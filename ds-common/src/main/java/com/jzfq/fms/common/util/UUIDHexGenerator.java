package com.jzfq.fms.common.util;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by huyinglin on 2016/12/7.
 */
public class UUIDHexGenerator extends AbstractUUIDGenerator {
	
	public static final UUIDHexGenerator DEFAULT = new UUIDHexGenerator();

	public static final SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
	private static volatile Map<String, Set<String>> map = new ConcurrentHashMap<String, Set<String>>();

	protected String format(final int intval) {
		final String formatted = Integer.toString(intval);
		final StringBuffer buf = new StringBuffer("0000");
		int start = 4 - formatted.length();
		if (start < 0) {
			start = 0;
		}
		buf.replace(start, 4, formatted);

		return buf.toString();
	}

	public Serializable generate(final Object obj) {

		synchronized (map) {
			String curTime = sdf.format(new Date());
			String curCount = format(getCount());
			if (map.containsKey(curTime)) {
				Set<String> curSet = map.get(curTime);
				if (curSet.contains(curCount)) {
					try {
						Thread.sleep(5);
					} catch (InterruptedException e) {
					}
					return generate(obj);
				} else {
					curSet.add(curCount);
				}

			} else {
				map.clear();
				resetCounter();
				curCount = format(getCount());
				Set<String> curSet = new HashSet<String>();
				curSet.add(curCount);
				map.put(curTime, curSet);
			}
			return new StringBuffer(28).append(getServerID()).append(curTime)
					.append(curCount).toString();
		}

	}
	public static final String generator() {
		return String.valueOf(UUIDHexGenerator.DEFAULT.generate(null));
	}
}