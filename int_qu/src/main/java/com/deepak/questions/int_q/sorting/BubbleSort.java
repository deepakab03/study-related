package com.deepak.questions.int_q.sorting;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Concept: Bubble sort - for ascending sorting, the smallest number 'bubbles' to the top (head of the array (index 0) (the
 * lighter air pockets and things bubble to the top or head of the water), for descending sorting, the largest number
 * 'bubbles' up to the head of the array. NOT that the largest number goes to head of the array.
 */
public class BubbleSort implements Sorting {
    private static final Logger logger = LoggerFactory.getLogger(BubbleSort.class);
    private boolean writeCounter = false;

    @Override
    public Integer[] sort(Integer[] input) {
        int opCounter = 0;
        int swapCounter = 0;
        logger.trace("---Initial Array: {}", Arrays.toString(input));
        for (int i = 0; i < input.length; i++) {
            for (int j = i + 1; j < input.length; j++) {
                if (writeCounter) logger.trace("Comparision Counter {}", ++opCounter);
                // 5,3,2,6,4
                if (input[i] > input[j]) {
                    Sorting.swap(input, i, j);
                    if (writeCounter) logger.trace("Swap Counter {}", ++swapCounter);
                }
                logger.trace("Array: {}", Arrays.toString(input));
            }
        }
        return input;
    }

}
