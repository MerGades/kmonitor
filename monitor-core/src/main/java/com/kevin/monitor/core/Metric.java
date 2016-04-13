package com.kevin.monitor.core;

/**
 * <p>
 * the metric for encapsulate the metric
 * so far, just record the key, exec duration, call at
 * </p>
 *
 * @author kevin
 * @since 2016-02-22 10:49
 */
public class Metric {

    private String key;
    private long value;
    private long timestamp;
    private int status;
    private String reason;

    /**
     *
     * @param key        :  detail method
     * @param value      :  exec duration
     * @param timestamp  :  call at
     */
    public Metric(String key, long value, long timestamp) {
        this.key = key;
        this.value = value;
        this.timestamp = timestamp;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
