package com.deepak.questions.int_q.sorting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Stopwatch;

/**
 * Concept: Bubble sort - for ascending sorting, the largest number bubbles up to the top (end) of the array with each iteration
 */
public class BubbleSort implements Sorting {
    private static final Logger logger = LoggerFactory.getLogger(BubbleSort.class);
    private Stopwatch stopWatch = Stopwatch.createUnstarted();
    private boolean writeCounter = false;
    private boolean logArrayPerIteratotion = false;
    
    /**
     * For sample input: [3, 1, 2, 7, 4, 6, 5], after the 2nd complete iteration
     */
    @Override
    public int[] sort(int[] input) {
        int opCounter = 0;
        int swapCounter = 0;
        Sorting.preLog(input, stopWatch, logger);;
        
        int len = input.length;
        int iterCounter = 0;
        while (len > 0) {
            int newLen = 0;
            for (int j = 0; j < len - 1; j++) {
                if (writeCounter) logger.trace("Comparision Counter {}", ++opCounter);
                if (input[j] > input[j + 1]) {
                    Sorting.swap(input, j, j + 1);
                    newLen = j + 1;
                    if (writeCounter) logger.trace("Swap Counter {}", ++swapCounter);
                }
                if (logArrayPerIteratotion) Sorting.logArray(input, logger);
            }
            len = newLen;
            if (logArrayPerIteratotion) logger.trace("Iteration {} completed", ++iterCounter);
        }
        
        Sorting.postLog(input, stopWatch, logger);
        return input;
    }
    
    public int[] alternateEfficientImpl(int[] input) {
        int opCounter = 0;
        int swapCounter = 0;
        Sorting.preLog(input, stopWatch, logger);;
        
        int n = input.length;
        do {
            int newN = 0;
            for (int j = 1; j <= n - 1; j++) {
                if (writeCounter) logger.trace("Comparision Counter {}", ++opCounter);
                if (input[j - 1] > input[j]) {
                    Sorting.swap(input, j - 1, j);
                    newN = j;
                    if (writeCounter) logger.trace("Swap Counter {}", ++swapCounter);
                }
                if (logArrayPerIteratotion) Sorting.logArray(input, logger);
            }
            n = newN;
            if (logArrayPerIteratotion) logger.trace("Iteration {} completed", n);
        } while (n!= 0);
        
        Sorting.postLog(input, stopWatch, logger);
        return input;
    }
    
    
    /**
     * In case no swaps happen, then don't need to proceed with sorting, hence we cut down on some iterations. 
     * Use a swapped boolean to check
     * 
     * @param input
     * @return
     */
    public int[] sortUnoptimized3(int[] input) {
        int opCounter = 0;
        int swapCounter = 0;
        Sorting.preLog(input, stopWatch, logger);;
        boolean swapped = true;
        
        for (int i = 0; i < input.length - 1 && swapped; i++) {
            swapped = false; 
            for (int j = 0; j < input.length - i - 1; j++) {
                if (writeCounter) logger.trace("Comparision Counter {}", ++opCounter);
                if (input[j] > input[j + 1]) {
                    Sorting.swap(input, j, j + 1);
                    swapped = true;
                    if (writeCounter) logger.trace("Swap Counter {}", ++swapCounter);
                }
                if (logArrayPerIteratotion) Sorting.logArray(input, logger);
            }
            if (logArrayPerIteratotion) logger.trace("Iteration {} completed", i);
        }
        
        Sorting.postLog(input, stopWatch, logger);
        return input;
    }

    /**
     * Removing need for extra len variable
     * @param input
     * @return
     */
    public int[] sortUnoptimized2(int[] input) {
        int opCounter = 0;
        int swapCounter = 0;
        Sorting.preLog(input, stopWatch, logger);;
        
        for (int i = 0; i < input.length - 1; i++) {
            for (int j = 0; j < input.length - i - 1; j++) {
                if (writeCounter) logger.trace("Comparision Counter {}", ++opCounter);
                if (input[j] > input[j + 1]) {
                    Sorting.swap(input, j, j + 1);
                    if (writeCounter) logger.trace("Swap Counter {}", ++swapCounter);
                }
                if (logArrayPerIteratotion) Sorting.logArray(input, logger);
            }
            if (logArrayPerIteratotion) logger.trace("Iteration {} completed", i);
        }
        
        Sorting.postLog(input, stopWatch, logger);
        return input;
    }
    
    /**
     * Cutting down on inner j loops that are no required as the largest number has bubbled to the top
     * And on outer 1 loop
     * @param input
     * @return
     */
    public int[] sortUnoptimized1(int[] input) {
        int opCounter = 0;
        int swapCounter = 0;
        Sorting.preLog(input, stopWatch, logger);;
        
        int len = input.length;
        for (int i = 0; i < input.length - 1; i++) {
            for (int j = 0; j < len - 1; j++) {
                if (writeCounter) logger.trace("Comparision Counter {}", ++opCounter);
                if (input[j] > input[j + 1]) {
                    Sorting.swap(input, j, j + 1);
                    if (writeCounter) logger.trace("Swap Counter {}", ++swapCounter);
                }
                if (logArrayPerIteratotion) Sorting.logArray(input, logger);
            }
            if (logArrayPerIteratotion) logger.trace("Iteration {} completed", i);
            len--;
        }
        
        Sorting.postLog(input, stopWatch, logger);
        return input;
    }
    
    public int[] sortUnoptimized(int[] input) {
        int opCounter = 0;
        int swapCounter = 0;
        Sorting.preLog(input, stopWatch, logger);;
        
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input.length - 1; j++) {
                if (writeCounter) logger.trace("Comparision Counter {}", ++opCounter);
                // 5,3,2,6,4
                if (input[j] > input[j + 1]) {
                    Sorting.swap(input, j, j + 1);
                    if (writeCounter) logger.trace("Swap Counter {}", ++swapCounter);
                }
                if (logArrayPerIteratotion) Sorting.logArray(input, logger);
            }
            if (logArrayPerIteratotion) logger.trace("Iteration {} completed", i);
        }
        
        Sorting.postLog(input, stopWatch, logger);
        return input;
    }
    
    /**
     * Weird sort that works
     * @param input
     * @return
     */
    public int[] sortOld(int[] input) {
        int opCounter = 0;
        int swapCounter = 0;
        Sorting.preLog(input, stopWatch, logger);;
        
        for (int i = 0; i < input.length; i++) {
            for (int j = i + 1; j < input.length; j++) {
                if (writeCounter) logger.trace("Comparision Counter {}", ++opCounter);
                // 5,3,2,6,4
                if (input[i] > input[j]) {
                    Sorting.swap(input, i, j);
                    if (writeCounter) logger.trace("Swap Counter {}", ++swapCounter);
                }
                if (logArrayPerIteratotion) Sorting.logArray(input, logger);
            }
        }
        
        Sorting.postLog(input, stopWatch, logger);
        return input;
    }

}
