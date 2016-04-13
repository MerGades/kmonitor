package com.kevin.monitor.core;

/**
 * <p>
 *  format the metric item for the collector on remote.
 * </p>
 *
 * @author czheng
 * @since 2016-02-22 10:44
 */
public interface Formatter {

    String format(Metric metric);

    String format(String prefix, Metric metric);
}
