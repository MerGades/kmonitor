
package com.kevin.monitor.runtime;


/**
 * reference from tprofiler
 */
public class ThreadData {
	/**
	 * 栈帧
	 */
	public ProfStack<long[]> stackFrame = new ProfStack<long[]>();
	/**
	 * 当前栈深度
	 */
	public int stackNum = 0;

	/**
	 * 清空数据
	 */
	public void clear(){
		stackFrame.clear();
		stackNum = 0;
	}
}
