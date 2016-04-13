
package com.kevin.monitor.runtime;

import java.util.Vector;

/**
 * reference from tprofiler
 */
public class MethodCache {

	/**
	 * 方法缓存默认大小
	 */
	private static final int INIT_CACHE_SIZE = 1024;
	/**
	 * 方法名缓存
	 */
	private static Vector<MethodInfo> mCacheMethods = new Vector<MethodInfo>(INIT_CACHE_SIZE);

	/**
	 * 占位并生成方法ID
	 * 
	 * @return
	 */
	public synchronized static int Request() {
		mCacheMethods.add(new MethodInfo());
		return mCacheMethods.size() - 1;
	}

	/**
	 * 更新行号
	 * 
	 * @param id
	 * @param linenum
	 */
	public synchronized static void UpdateLineNum(int id, int linenum) {
		mCacheMethods.get(id).setMLineNum(linenum);
	}

	/**
	 * 更新类名方法名
	 * 
	 * @param id
	 * @param fileName
	 * @param className
	 * @param methodName
	 */
	public synchronized static void UpdateMethodName(int id, String fileName, String className, String methodName) {
		MethodInfo methodInfo = mCacheMethods.get(id);
		methodInfo.setMFileName(fileName);
		methodInfo.setMClassName(className);
		methodInfo.setMMethodName(methodName);
	}

	public static MethodInfo getMethodInfo(int id){
		return mCacheMethods.get(id);
	}
}
