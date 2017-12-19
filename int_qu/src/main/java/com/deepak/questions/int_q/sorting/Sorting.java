package com.deepak.questions.int_q.sorting;

import static java.lang.String.format;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;

import com.google.common.base.Stopwatch;

public interface Sorting {

    int NUM_LOG_THRESHOLD = 10000;

    int[] sort(int input[]);
    
    public static void swap(int[] input, int i, int j) {
        int temp = input[i];
        input[i] = input[j];
        input[j] = temp;
    }
    
    public static String elapsedTimeAsStr(final Stopwatch stopWatch) {

        stopWatch.stop();
        int min = (int) stopWatch.elapsed(TimeUnit.MINUTES);
        int sec = (int) stopWatch.elapsed(TimeUnit.SECONDS);
        long millis = stopWatch.elapsed(TimeUnit.MILLISECONDS);
        if (min > 0) {
            sec = sec % 60;
        }
        if (sec > 0) {
            millis = millis % 1000;
        } 
        String time = format("%d min, %d seconds, %d milllis", min, sec, millis);
        return time;
    
    }
    
    public static void preLog(int[] input, Stopwatch stopWatch,Logger logger) {
        stopWatch.start();
        if (input.length <= NUM_LOG_THRESHOLD) {
            logger.info("--Input size {}, data:{}", input.length, Arrays.toString(input));
        } else {
            logger.info("--Large input size: {}", input.length);
        }
    }
    
    public static void postLog(int[] input, Stopwatch stopWatch,Logger logger) {
        String time = Sorting.elapsedTimeAsStr(stopWatch);
        
        if (input.length <= NUM_LOG_THRESHOLD) {
            logger.info("Sorting Time {}, SortedArray :{}", time, Arrays.toString(input));
        } else {
            logger.info("Sorting time {}", time);
        }
        
    }
    
    public static void logArray(int[] input, Logger logger) {
        logger.trace("Array: {}", Arrays.toString(input));
    }

}
