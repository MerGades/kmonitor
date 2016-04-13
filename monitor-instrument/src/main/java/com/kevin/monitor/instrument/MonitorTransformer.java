
package com.kevin.monitor.instrument;

import com.kevin.monitor.core.config.MonitorFilter;
import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * reference from tprofiler
 */
public class MonitorTransformer implements ClassFileTransformer {

	/* (non-Javadoc)
	 * @see java.lang.instrument.ClassFileTransformer#transform(java.lang.ClassLoader, java.lang.String, java.lang.Class, java.security.ProtectionDomain, byte[])
	 */
	public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
	        ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
		if (loader != null && MonitorFilter.isNotNeedInjectClassLoader(loader.getClass().getName())) {
			return classfileBuffer;
		}
		if (!MonitorFilter.isNeedInject(className)) {
			return classfileBuffer;
		}
		if (MonitorFilter.isNotNeedInject(className)) {
			return classfileBuffer;
		}

		try {
			ClassReader reader = new ClassReader(classfileBuffer);
			ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
			ClassAdapter adapter = new MonitorClassAdapter(writer, className);
			reader.accept(adapter, 0);
			return writer.toByteArray();
		} catch (Throwable e) {
			return classfileBuffer;
		}
	}
}
