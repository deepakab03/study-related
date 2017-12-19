package com.deepak.questions.int_q.sorting;

import static org.apache.commons.lang3.ArrayUtils.toObject;
import static org.hamcrest.Matchers.arrayContaining;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class SortingTest {
    private static final Logger logger = LoggerFactory.getLogger(SortingTest.class);
    
    @Parameters(method="sortParamsExtended")
    @Test() public void
    whenSort_givenBubbleSortImpl_shouldSortCorrectly(int[] input, int[] expectedOutput) {
        BubbleSort sorting = new BubbleSort();
        
        int[] sortedArray = sorting.sort(input);
        
        assertThat(toObject(sortedArray), arrayContaining(toObject(expectedOutput)));
    }
    
    Object[] sortParamsBasic() throws IOException, URISyntaxException {
        return new Object[][] { 
            { new int[] {10,5,2,4,7,1,8,9,6,0,3}, new int[] {0,1,2,3,4,5,6,7,8,9,10} },
            { new int[] {1,2,3,4}, new int[] {1,2,3,4} },
            { new int[] {4,3,2,1}, new int[] {1,2,3,4} },
            { new int[] {3,1,2,7,4,6,5}, new int[] {1,2,3,4,5,6,7} },
            { new int[] {5,3,2,6,4}, new int[] {2,3,4,5,6} },
            { inputArray("1_hund"),  expectedArray("1_hund") }
            
        };
    }
    
    Object[] sortParamsExtended() throws IOException, URISyntaxException {
        Object[] basicSortParams = sortParamsBasic();
        Object[] data = new Object[][] { 
            { inputArray("1_thou"),  expectedArray("1_thou") },
            { inputArray("10_thou"),  expectedArray("10_thou") }
        };
        return ArrayUtils.addAll(basicSortParams, data);
    }
    
    private int[] inputArray(String prefix) throws IOException, URISyntaxException {
        URL resource = getClass().getResource("/com/deepak/questions/int_q/util/" + prefix + "_random_unique_nums.txt");
        Path path = Paths.get(resource.toURI());
        return read(path);
    }
    
    private int[] expectedArray(String prefix) throws IOException, URISyntaxException {
        Path path = Paths.get(getClass().getResource("/com/deepak/questions/int_q/util/" + prefix + "_random_unique_nums_sorted.txt").toURI());
        return read(path);
    }

    public int[] read(Path path) throws IOException {
        logger.debug("Reading from {}", path);
        List<String> lines = Files.readAllLines(path);
        String lineWithNum = lines.get(0);
        int[] intArray = Arrays.stream(lineWithNum.split(",")).map(str -> Integer.parseInt(str)).mapToInt(i -> i).toArray();
        return intArray;
    }
    
    Object[] sortParamsInclLarge() throws IOException, URISyntaxException {
        Object[] extendedSortParams = sortParamsExtended();
        Object[] data = new Object[][] { { inputArray("1_lac"), expectedArray("1_lac") },
                { inputArray("10_lac"), expectedArray("10_lac") } };

        return ArrayUtils.addAll(extendedSortParams, data);
    }    
    
    @Parameters(method="sortParamsInclLarge")
    @Test() public void
    whenSort_givenInsertionSortImpl_shouldSortCorrectly(int[] input, int[] expectedOutput) {
        Sorting sorting = new InsertionSort();
        
        int[] sortedArray = sorting.sort(input);
        
        assertThat(toObject(sortedArray), arrayContaining(toObject(expectedOutput)));
    }
    
}
