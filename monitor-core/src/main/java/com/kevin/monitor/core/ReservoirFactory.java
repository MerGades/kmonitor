package com.kevin.monitor.core;

/**
 * <p>
 *   used to build the reservoir.
 * </p>
 *
 * @author czheng
 * @since 2016-02-29 15:23
 */
public class ReservoirFactory {

    private static final Reservoir DEFAULT_RESERVOIR = new BlockQueueReservoir();

    public static Reservoir getInstance(){
        return DEFAULT_RESERVOIR;
    }

}
