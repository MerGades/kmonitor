package com.kevin.monitor.core.config;

import java.util.HashSet;
import java.util.Set;

/**
 * reference from tprofiler
 */
public class MonitorFilter {

	/**
	 * 注入的Package集合
	 */
	private static Set<String> includePackage = new HashSet<String>();
	/**
	 * 不注入的Package集合
	 */
	private static Set<String> excludePackage = new HashSet<String>();
	/**
	 * 不注入的ClassLoader集合
	 */
	private static Set<String> excludeClassLoader = new HashSet<String>();

	static {
		includePackage.add("com/jimu");

		// 默认不注入的Package
		excludePackage.add("java/");// 包含javax
		excludePackage.add("sun/");// 包含sunw
		excludePackage.add("com/sun/");
		excludePackage.add("org/");// 包含org/xml org/jboss org/apache/xerces org/objectweb/asm  
		// 不注入profile本身
		excludePackage.add("com/jimu/monitor");
	}

	/**
	 * 
	 * @param className
	 */
	public static void addIncludeClass(String className) {
		String icaseName = className.toLowerCase().replace('.', '/');
		includePackage.add(icaseName);
	}

	/**
	 * 
	 * @param className
	 */
	public static void addExcludeClass(String className) {
		String icaseName = className.toLowerCase().replace('.', '/');
		excludePackage.add(icaseName);
	}

	/**
	 * 
	 * @param classLoader
	 */
	public static void addExcludeClassLoader(String classLoader) {
		excludeClassLoader.add(classLoader);
	}

	/**
	 * 是否是需要注入的类
	 * 
	 * @param className
	 * @return
	 */
	public static boolean isNeedInject(String className) {
		String icaseName = className.toLowerCase().replace('.', '/');
		for (String v : includePackage) {
			if (icaseName.startsWith(v)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 是否是不需要注入的类
	 * 
	 * @param className
	 * @return
	 */
	public static boolean isNotNeedInject(String className) {
		String icaseName = className.toLowerCase().replace('.', '/');
		for (String v : excludePackage) {
			if (icaseName.startsWith(v)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 是否是不需要注入的类加载器
	 * 
	 * @param classLoader
	 * @return
	 */
	public static boolean isNotNeedInjectClassLoader(String classLoader) {
		for (String v : excludeClassLoader) {
			if (classLoader.equals(v)) {
				return true;
			}
		}
		return false;
	}
}
