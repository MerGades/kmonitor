package com.kevin.monitor.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

/**
 * <p>
 *    used to send the metrics to metrics collector on schedule time
 * </p>
 *
 * @author czheng
 * @since 2016-02-22 16:29
 */
public class Slf4jScheduledReporter extends ScheduledReporter {

    private static final Logger REPORTER = LoggerFactory.getLogger(Slf4jScheduledReporter.class);
    private Reservoir reservoir;
    private Formatter formatter = new PlainTxtFormatter(); // just use plain txt

    public Slf4jScheduledReporter(Reservoir reservoir) {
        super("slf4j-reporter");
        this.reservoir = reservoir;
    }

    @Override
    public void doReport() {
        Collection<Metric> metrics =  reservoir.fetch(50);
        if(metrics != null && metrics.size() > 0) {
            StringBuilder sb = new StringBuilder();
            for(Metric metric : metrics){
                sb.append(formatter.format(metric));
            }
            REPORTER.info(sb.toString());
        }
    }
}

