package com.kevin.monitor.core.annotation;

import java.lang.annotation.*;

/**
 * <p>
 *    mark the method need to record exec time.
 * </p>
 *
 * @author czheng
 * @since 2016-02-26 11:14
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JimuTimer {
}
