package com.kevin.monitor.instrument;

import com.kevin.monitor.Profiler;
import com.kevin.monitor.core.annotation.JimuTimer;
import com.kevin.monitor.runtime.MethodCache;
import org.objectweb.asm.*;

import static org.objectweb.asm.Opcodes.INVOKESTATIC;

/**
 * reference from tprofiler
 */
public class MonitorMethodAdapter extends MethodAdapter {
	/**
	 * 方法ID
	 */
	private int mMethodId = 0;

	private static final String TIMER_ANNOTATION =
			"L" + JimuTimer.class.getName().replaceAll("\\.", "/") + ";";

	private boolean inject = false;

	@Override
	public AnnotationVisitor visitAnnotation(String s, boolean b) {
		if(s != null && s.length() > 0 && s.equals(TIMER_ANNOTATION)){
			inject = true;
		}
		return super.visitAnnotation(s, b);
	}

	/**
	 * @param visitor
	 * @param fileName
	 * @param className
	 * @param methodName
	 */
	public MonitorMethodAdapter(MethodVisitor visitor, String fileName, String className, String methodName) {
		super(visitor);
		mMethodId = MethodCache.Request();
		MethodCache.UpdateMethodName(mMethodId, fileName, className, methodName);
		// 记录方法数
		Profiler.instrumentMethodCount.getAndIncrement();
	}

	/* (non-Javadoc)
	 * @see org.objectweb.asm.MethodAdapter#visitCode()
	 */
	public void visitCode() {
		if(inject) {
			this.visitLdcInsn(mMethodId);
			this.visitMethodInsn(INVOKESTATIC, "com/jimu/monitor/Profiler", "start", "(I)V");
		}
		super.visitCode();
	}

	/* (non-Javadoc)
	 * @see org.objectweb.asm.MethodAdapter#visitLineNumber(int, org.objectweb.asm.Label)
	 */
	public void visitLineNumber(final int line, final Label start) {
		MethodCache.UpdateLineNum(mMethodId, line);
		super.visitLineNumber(line, start);
	}

	/* (non-Javadoc)
	 * @see org.objectweb.asm.MethodAdapter#visitInsn(int)
	 */
	public void visitInsn(int inst) {
		if(inject){
			switch (inst) {
				case Opcodes.ARETURN:
				case Opcodes.DRETURN:
				case Opcodes.FRETURN:
				case Opcodes.IRETURN:
				case Opcodes.LRETURN:
				case Opcodes.RETURN:
				case Opcodes.ATHROW:
					this.visitLdcInsn(mMethodId);
					this.visitMethodInsn(INVOKESTATIC, "com/jimu/monitor/Profiler", "end", "(I)V");
					break;
				default:
					break;
			}
		}

		super.visitInsn(inst);
	}

}
