package com.kevin.monitor.core.config;


import java.util.Properties;

/**
 * config for monitor
 * reference from tprofiler
 */
public class MonitorConfig {

    private static final String CONFIG_FILE_NAME = "jimu_monitor.properties";

	private String startTime;

	private String endTime;

	private String excludeClassLoader;

	private String includePackageStartsWith;

	private String excludePackageStartsWith;

	private boolean needNanoTime;

	private boolean ignoreGetSetMethod;

	private boolean debugMode;

	public MonitorConfig() {
		parseProperty();
	}

	private void parseProperty() {
		Properties properties = new Properties();
		try {
			properties.load(getClass().getClassLoader().getResourceAsStream(CONFIG_FILE_NAME));
			
			Properties context = new Properties();
			context.putAll(System.getProperties());
			context.putAll(properties);
			
      		loadConfig(new ConfigureProperties(properties, context));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	* 加载配置
	* @param properties
	*/
	private void loadConfig(Properties properties) {
		String startProfTime = properties.getProperty("startTime");
		String endProfTime = properties.getProperty("endTime");
		String includePackageStartsWith = properties.getProperty("includePackageStartsWith");
		String excludePackageStartsWith = properties.getProperty("excludePackageStartsWith");
		String excludeClassLoader = properties.getProperty("excludeClassLoader");
		String needNanoTime = properties.getProperty("needNanoTime");
		String ignoreGetSetMethod = properties.getProperty("ignoreGetSetMethod");
		String debugMode = properties.getProperty("debugMode");
		setDebugMode("true".equalsIgnoreCase(debugMode == null ? null : debugMode.trim()));
		setExcludeClassLoader(excludeClassLoader);
		setIncludePackageStartsWith(includePackageStartsWith);
		setExcludePackageStartsWith(excludePackageStartsWith);
		setStartTime(startProfTime);
		setEndTime(endProfTime);
		setNeedNanoTime("true".equals(needNanoTime));
		setIgnoreGetSetMethod("true".equals(ignoreGetSetMethod));
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return
	 */
	public String getIncludePackageStartsWith() {
		return includePackageStartsWith;
	}

	/**
	 * @param includePackageStartsWith
	 */
	public void setIncludePackageStartsWith(String includePackageStartsWith) {
		this.includePackageStartsWith = includePackageStartsWith;
	}

	/**
	 * @return
	 */
	public String getExcludePackageStartsWith() {
		return excludePackageStartsWith;
	}

	/**
	 * @param excludePackageStartsWith
	 */
	public void setExcludePackageStartsWith(String excludePackageStartsWith) {
		this.excludePackageStartsWith = excludePackageStartsWith;
	}

	/**
	 * @return
	 */
	public boolean isNeedNanoTime() {
		return needNanoTime;
	}

	/**
	 * @param needNanoTime
	 */
	public void setNeedNanoTime(boolean needNanoTime) {
		this.needNanoTime = needNanoTime;
	}

	/**
	 * @return
	 */
	public boolean isIgnoreGetSetMethod() {
		return ignoreGetSetMethod;
	}

	/**
	 * @param ignoreGetSetMethod
	 */
	public void setIgnoreGetSetMethod(boolean ignoreGetSetMethod) {
		this.ignoreGetSetMethod = ignoreGetSetMethod;
	}

	/**
	 * @return the excludeClassLoader
	 */
	public String getExcludeClassLoader() {
		return excludeClassLoader;
	}

	/**
	 * @param excludeClassLoader the excludeClassLoader to set
	 */
	public void setExcludeClassLoader(String excludeClassLoader) {
		this.excludeClassLoader = excludeClassLoader;
	}

	/**
	 * @return the debugMode
	 */
	public boolean isDebugMode() {
		return debugMode;
	}

	/**
	 * @param debugMode the debugMode to set
	 */
	public void setDebugMode(boolean debugMode) {
		this.debugMode = debugMode;
	}
}
