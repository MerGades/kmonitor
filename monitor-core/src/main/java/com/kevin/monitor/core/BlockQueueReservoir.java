package com.kevin.monitor.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * <p>
 *  save the metric item in blocking queue.
 * </p>
 *
 * @author czheng
 * @since 2016-02-22 11:08
 */
public class BlockQueueReservoir implements Reservoir {

    private static final int MAX_COUNT = 500;

    private int maxSize;

    public BlockQueueReservoir() {
        this(MAX_COUNT);
    }

    public BlockQueueReservoir(int maxSize) {
        this.maxSize = maxSize;
    }

    private BlockingQueue<Metric> queue = new LinkedBlockingQueue<>();

    public int size() {
        return queue.size();
    }

    public void add(Metric metric) {
        int size  = queue.size();
        if(size > MAX_COUNT) return;
//            throw new IllegalStateException(String.format("saved metrics in queue is over max count(%s)!!!pls check", maxSize));
        queue.add(metric);
    }

    public Collection<Metric> fetch(int size) {
        List<Metric> metricList = new ArrayList<Metric>();
        queue.drainTo(metricList, size);
        return metricList;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public int getMaxSize() {
        return maxSize;
    }
}
