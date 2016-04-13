package com.kevin.monitor.core;

import java.util.Collection;

/**
 * <p>
 *  temporarily save the metric
 * </p>
 *
 * @author czheng
 * @since 2016-02-22 10:41
 */
public interface Reservoir {
    /**
     * Returns the number of values recorded.
     */
    int size();

    /**
     * Adds a new recorded value to the reservoir.
     */
    void add(Metric metric);


    /**
     * fetch specified number for the metrics
     * @param size
     * @return
     */
    Collection<Metric> fetch(int size);

}
