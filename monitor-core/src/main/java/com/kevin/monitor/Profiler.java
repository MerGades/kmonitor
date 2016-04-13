package com.kevin.monitor;

import com.kevin.monitor.core.Metric;
import com.kevin.monitor.core.Reservoir;
import com.kevin.monitor.core.ReservoirFactory;
import com.kevin.monitor.runtime.MethodCache;
import com.kevin.monitor.runtime.MethodInfo;
import com.kevin.monitor.runtime.ThreadData;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * reference from tprofiler
 */
public class Profiler {

	public static AtomicInteger instrumentClassCount = new AtomicInteger(0);

	public static AtomicInteger instrumentMethodCount = new AtomicInteger(0);

	public static ThreadLocal<ThreadData> threadProfile = new ThreadLocal<ThreadData>();

	public static void start(int methodId) {
		System.out.println(String.format("start the method%s", MethodCache.getMethodInfo(methodId)));
		if(!Manager.instance().canProfile()) {
			return;
		}

		long startTime;
		if (Manager.isNeedNanoTime()) {
			startTime = System.nanoTime();
		} else {
			startTime = System.currentTimeMillis();
		}
		try {
			ThreadData thrData = threadProfile.get();
			if (thrData == null) {
				thrData = new ThreadData();
				threadProfile.set(thrData);
			}

			long[] frameData = new long[4];
			frameData[0] = methodId;
			frameData[1] = thrData.stackNum;
			frameData[2] = startTime;
			frameData[3] = startTime;

			thrData.stackFrame.push(frameData);
			thrData.stackNum++;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void end(int methodId) {
		System.out.println(String.format("end the method%s", MethodCache.getMethodInfo(methodId)));
		if(!Manager.instance().canProfile()) {
			return;
		}

		long endTime;
		if (Manager.isNeedNanoTime()) {
			endTime = System.nanoTime();
		} else {
			endTime = System.currentTimeMillis();
		}
		try {
			ThreadData thrData = threadProfile.get();
			if (thrData == null || thrData.stackNum <= 0 || thrData.stackFrame.size() == 0) {
				return;
			}

			thrData.stackNum--;
			long[] frameData = thrData.stackFrame.pop();
			long id = frameData[0];
			if (methodId != id) {
				return;
			}
			long useTime = endTime - frameData[2];
			if (Manager.isNeedNanoTime()) {
				if (useTime > 500000) {
					frameData[2] = useTime;
				}
			} else if (useTime > 1) {
				frameData[2] = useTime;
			}
			saveMetric(frameData); // save the metric to reservoir
		} catch (Exception e) {
			// do nothing
		} finally {
			threadProfile.set(null);
		}
	}

	/**
	 * convert the raw statistic data and add it to reservoir
	 * @param statisticData
	 */
	private static void saveMetric(long[] statisticData) {

		long methodId = statisticData[0];
		long duration = statisticData[2];
		long callAt = statisticData[3];
		MethodInfo methodInfo = MethodCache.getMethodInfo((int)methodId);
		if (methodInfo == null) return;
		Metric metric = new Metric(genKey(methodInfo), duration, callAt);
		Reservoir reservoir = ReservoirFactory.getInstance();
		reservoir.add(metric);
	}

	/**
	 * generate the key with class name  _ method name
	 * @param methodInfo
	 * @return
	 */
	private static String genKey(MethodInfo methodInfo){
		String className = methodInfo.getMClassName();
		String methodName = methodInfo.getMMethodName();
		return className + "_" + methodName;
	}

}
