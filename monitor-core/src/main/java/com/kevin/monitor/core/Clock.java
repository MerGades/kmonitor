package com.kevin.monitor.core;

/**
 * <p>
 *    clock for basic time
 * </p>
 *
 * @author czheng
 * @since 2016-02-22 10:33
 */
public abstract class Clock {
    /**
     * Returns the current time tick.
     *
     * @return time tick in nanoseconds
     */
    public abstract long getTick();

    public long getTime() {
        return System.currentTimeMillis();
    }

    private static final Clock DEFAULT = new UserTimeClock();

    public static Clock defaultClock() {
        return DEFAULT;
    }

    /**
     * A clock implementation which returns the current time in epoch nanoseconds.
     */
    public static class UserTimeClock extends Clock {
        @Override
        public long getTick() {
            return System.nanoTime();
        }
    }
}
