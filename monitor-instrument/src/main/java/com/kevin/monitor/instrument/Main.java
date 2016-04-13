package com.kevin.monitor.instrument;

import com.kevin.monitor.Manager;

import java.lang.instrument.Instrumentation;

public class Main {

	/**
	 * @param args
	 * @param inst
	 */
	public static void premain(String args, Instrumentation inst) {
		Manager.instance().initialization();
		inst.addTransformer(new MonitorTransformer());
		Manager.instance().startupThread();
	}
}
