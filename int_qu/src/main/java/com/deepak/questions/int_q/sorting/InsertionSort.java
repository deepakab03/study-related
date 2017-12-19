package com.deepak.questions.int_q.sorting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Stopwatch;

public class InsertionSort implements Sorting {
    private static final Logger logger = LoggerFactory.getLogger(InsertionSort.class);
    
    private boolean logArrayPerIteratotion;
    
    private Stopwatch stopWatch = Stopwatch.createUnstarted();
    
    @Override
    public int[] sort(int[] input) {
        Sorting.preLog(input, stopWatch, logger);
        
        for (int i = 1 ;i < input.length; i++) {
            int key = input[i];
            int j = i - 1;
            while (j >= 0 && key < input[j]) {
                input[j + 1] = input[j];
                j--;
                if (logArrayPerIteratotion) Sorting.logArray(input, logger);
            }
            input[j + 1] = key;
        }
        
        Sorting.postLog(input, stopWatch, logger);
        return input;
    }

    public int[] sortCorrect(int[] input) {
        Sorting.preLog(input, stopWatch, logger);
        
        for (int i = 1; i < input.length ; i++) {
            int key = input[i];
            if (key < input[i - 1]) {
                int j = i - 1;
                while (j >= 0 && key < input[j] ) {
                    input[j + 1] = input[j];
                    j--;
                }
                input[j + 1] = key;
            }
        }
        
        Sorting.postLog(input, stopWatch, logger);
        return input;
    }
}
