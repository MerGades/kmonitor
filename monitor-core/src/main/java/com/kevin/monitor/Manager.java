
package com.kevin.monitor;

import com.kevin.monitor.core.ReservoirFactory;
import com.kevin.monitor.core.ScheduledReporter;
import com.kevin.monitor.core.Slf4jScheduledReporter;
import com.kevin.monitor.core.config.MonitorConfig;
import com.kevin.monitor.core.config.MonitorFilter;

import java.util.concurrent.TimeUnit;

/**
 * reference from tprofiler
 */
public class Manager {

	private static boolean NEED_NANO_TIME;

	private static boolean IGNORE_GETSET_METHOD;

	private static Manager manager = new Manager();

	private MonitorConfig profConfig;

	private volatile boolean profileFlag = false;

	private boolean isDebugMode;

	private static Object lock = new Object();

	private volatile boolean isStartUp = false;
	private volatile boolean isInitialed = false;
	private volatile boolean isShutdown = false;

	private ScheduledReporter reporter;

	/**
	 * 私有构造器
	 */
	private Manager() {}

	/**
	 * 初始化配置
	 */
	public void initialization() {
		if(!isInitialed){
			synchronized (lock) {
				if(!isInitialed){
					profConfig = new MonitorConfig();
					NEED_NANO_TIME = profConfig.isNeedNanoTime();
					IGNORE_GETSET_METHOD = profConfig.isIgnoreGetSetMethod();
					isDebugMode = profConfig.isDebugMode();
					profileFlag = true;
					setProfFilter();

					isInitialed = true;
				}
			}
		}
	}

	/**
	 * @return
	 */
	public static Manager instance() {
		return manager;
	}

	/**
	 * @return the needNanoTime
	 */
	public static boolean isNeedNanoTime() {
		return NEED_NANO_TIME;
	}

	/**
	 * @return the ignoreGetSetMethod
	 */
	public static boolean isIgnoreGetSetMethod() {
		return IGNORE_GETSET_METHOD;
	}

	/**
	 * 判断当前是否可以采集数据
	 * @return
	 */
	public boolean canProfile() {
		return profileFlag;
	}

	/**
	 * @return the isDebugMode
	 */
	public boolean isDebugMode() {
		return isDebugMode;
	}

	/**
	 * 设置包名过滤器
	 * 
	 */
	private void setProfFilter() {
		String classLoader = profConfig.getExcludeClassLoader();
		if (classLoader != null && classLoader.trim().length() > 0) {
			String[] _classLoader = classLoader.split(";");
			for (String pack : _classLoader) {
				MonitorFilter.addExcludeClassLoader(pack);
			}
		}
		String include = profConfig.getIncludePackageStartsWith();
		if (include != null && include.trim().length() > 0) {
			String[] _includes = include.split(";");
			for (String pack : _includes) {
				MonitorFilter.addIncludeClass(pack);
			}
		}
		String exclude = profConfig.getExcludePackageStartsWith();
		if (exclude != null && exclude.trim().length() > 0) {
			String[] _excludes = exclude.split(";");
			for (String pack : _excludes) {
				MonitorFilter.addExcludeClass(pack);
			}
		}
	}

	public void startupThread() {
		if(!canProfile()) return;
		if(!isStartUp){
			synchronized (lock) {
				if(!isStartUp){
					this.reporter = new Slf4jScheduledReporter(ReservoirFactory.getInstance());
					this.reporter.start(1000, TimeUnit.MILLISECONDS);
					isStartUp = true;
				}
			}
		}
	}

	public void shutdownThread(){
		if(!isShutdown && isStartUp){
			synchronized (lock) {
				if(!isShutdown && isStartUp){
					this.reporter.stop();
					isShutdown = true;
				}
			}
		}
	}
}
