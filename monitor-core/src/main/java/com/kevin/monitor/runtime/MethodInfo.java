
package com.kevin.monitor.runtime;

/**
 * reference from tprofiler
 */
public class MethodInfo {

	/**
	 * 类名
	 */
	private String mClassName;
	/**
	 * 方法名
	 */
	private String mMethodName;
	/**
	 * 文件名
	 */
	private String mFileName;
	/**
	 * 行号
	 */
	private int mLineNum;

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return mClassName + ":" + mMethodName + ":" + mLineNum;
	}

	/**
	 * @return
	 */
	public String getMClassName() {
		return mClassName;
	}

	/**
	 * @param className
	 */
	public void setMClassName(String className) {
		mClassName = className;
	}

	/**
	 * @return
	 */
	public String getMMethodName() {
		return mMethodName;
	}

	/**
	 * @param methodName
	 */
	public void setMMethodName(String methodName) {
		mMethodName = methodName;
	}

	/**
	 * @return
	 */
	public String getMFileName() {
		return mFileName;
	}

	/**
	 * @param fileName
	 */
	public void setMFileName(String fileName) {
		mFileName = fileName;
	}

	/**
	 * @return
	 */
	public int getMLineNum() {
		return mLineNum;
	}

	/**
	 * @param lineNum
	 */
	public void setMLineNum(int lineNum) {
		mLineNum = lineNum;
	}

}
