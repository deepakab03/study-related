package com.deepak.questions.int_q.sorting;

import static org.hamcrest.Matchers.arrayContaining;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class SortingTest {
    private static final Logger logger = LoggerFactory.getLogger(SortingTest.class);
    
    @Parameters(method="sortParams")
    @Test public void
    whenSort_givenBubbleSortImpl_shouldSortCorrectly(Integer[] input, Integer[] expectedOutput) {
        Sorting sorting = new BubbleSort();
        logger.info("Input :{}", Arrays.toString(input));
        
        Integer[] sortedArray = sorting.sort(input);
        
        logger.info("sortedArray :{}", Arrays.toString(sortedArray));
        assertThat(sortedArray, arrayContaining(expectedOutput));
    }
    
    Object[] sortParams() {
                             //input, expectedOutput
        return new Object[][] { 
            { new Integer[] {10,5,2,4,7,1,8,9,6,0, 3}, new Integer[] {0,1,2,3,4,5,6,7,8,9,10} },
            { new Integer[] {1,2,3,4}, new Integer[] {1,2,3,4} }
        
        };
    }
    
}
