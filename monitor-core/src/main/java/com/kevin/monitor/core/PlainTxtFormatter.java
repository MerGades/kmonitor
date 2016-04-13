package com.kevin.monitor.core;

/**
 * <p>
 * format the metrics to standard txt line like following
 * key,value,callAt\n
 * </p>
 *
 * @author czheng
 * @since 2016-02-22 12:11
 */
public class PlainTxtFormatter implements Formatter{


    public String format(Metric metric) {
       return format("exec_time", metric);
    }


    public String format(String prefix, Metric metric) {
        String key = metric.getKey();
        long execDuration = metric.getValue();
        long callAt = metric.getTimestamp();
        return String.format("%s_%s,%s,%s\n", prefix, isBlank(key) ? "unknown_call" : key, execDuration, callAt);
    }

    private boolean isBlank(CharSequence string){
        return string == null || containsOnlyWhitespaces(string);
    }

    public static boolean containsOnlyWhitespaces(CharSequence string) {
        int size = string.length();
        for (int i = 0; i < size; i++) {
            char c = string.charAt(i);
            if (isWhitespace(c) == false) {
                return false;
            }
        }
        return true;
    }

    public static boolean isWhitespace(char c) {
        return c <= ' ';
    }
}
