package com.kevin.monitor.core;

/**
 * <p>
 *    used to record the time
 * </p>
 *
 * @author kevin
 * @since 2016-02-22 11:55
 */
public class Timer{

    public static class Context implements AutoCloseable {
        private final Timer timer;
        private final Clock clock;
        private final long startTime;
        private long duration;

        private Context(Timer timer, Clock clock) {
            this.timer = timer;
            this.clock = clock;
            this.startTime = clock.getTime();
        }

        public Context stop() {
            this.duration = clock.getTime() - startTime;
            return this;
        }

        public void close() {
            stop();
        }

        public long getStartTime() {
            return startTime;
        }

        public long duration(){
            return duration;
        }
    }
    private final Clock clock;

    public Timer() {
        this(Clock.defaultClock());
    }

    public Timer(Clock clock) {
        this.clock = clock;
    }

    public Context time() {
        return new Context(this, clock);
    }
}