package com.deepak.questions.int_q.sorting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BubbleSort implements Sorting {
    private static final Logger logger = LoggerFactory.getLogger(BubbleSort.class);
    
    @Override
    public Integer[] sort(Integer[] input) {
        int opCounter = 0;
        int swapCounter = 0;
        for (int i = 0; i < input.length; i++) {
            for (int j = i + 1; j < input.length; j++) {
                logger.info("Comparision Counter {}", ++opCounter);
                
                if (input[i] > input[j]) {
                    Sorting.swap(input, i , j);
                    logger.info("Swap Counter {}", ++swapCounter);
                }
            }
        }
        return input;
    }

}
