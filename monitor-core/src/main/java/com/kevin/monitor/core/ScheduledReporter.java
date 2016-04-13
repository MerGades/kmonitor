package com.kevin.monitor.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>
 *    used to send the metrics to metrics collector on schedule time
 * </p>
 *
 * @author kevin
 * @since 2016-02-22 16:29
 */
public abstract class ScheduledReporter implements Closeable {

    private static final Logger LOG = LoggerFactory.getLogger(ScheduledReporter.class);

    private static class NamedThreadFactory implements ThreadFactory {
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        private NamedThreadFactory(String name) {
            final SecurityManager s = System.getSecurityManager();
            this.group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
            this.namePrefix = "metrics-" + name + "thread-";
        }

        public Thread newThread(Runnable r) {
            final Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
            t.setDaemon(true);
            if (t.getPriority() != Thread.NORM_PRIORITY) {
                t.setPriority(Thread.NORM_PRIORITY);
            }
            return t;
        }
    }

    private static final AtomicInteger FACTORY_ID = new AtomicInteger();

    private final ScheduledExecutorService executor;

    protected ScheduledReporter(String name) {
        this(Executors.newSingleThreadScheduledExecutor(new NamedThreadFactory(name + '-' + FACTORY_ID.incrementAndGet())));
    }

    protected ScheduledReporter(ScheduledExecutorService executor) {
        this.executor = executor;
    }

    public void start(long period, TimeUnit unit) {
        executor.scheduleAtFixedRate(new Runnable() {
            public void run() {
                try {
                    report();
                } catch (RuntimeException ex) {
                    LOG.error("RuntimeException thrown from {}#report. Exception was suppressed.",
                            ScheduledReporter.this.getClass().getSimpleName(), ex);
                }
            }
        }, period, period, unit);
    }

    public void stop() {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(1, TimeUnit.SECONDS)) {
                executor.shutdownNow();
                if (!executor.awaitTermination(1, TimeUnit.SECONDS)) {
                    LOG.warn(getClass().getSimpleName() + ": ScheduledExecutorService did not terminate");
                }
            }
        } catch (InterruptedException ie) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    public void close() {
        stop();
    }

    public void report() {
        synchronized (this) {
            doReport();
        }
    }

    public abstract void doReport();
}

