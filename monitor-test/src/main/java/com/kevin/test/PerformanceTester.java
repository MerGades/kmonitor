package com.kevin.test;

/**
 * <p>
 * performance test monitor
 * </p>
 *
 * @author kevin
 * @since 2016-03-03 10:30
 */
public class PerformanceTester {


    public static void main(String[] args){
        RunningMan rm = new RunningMan();
        while (true) {
            rm.test();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                //do nothing
            }
        }
    }

}
