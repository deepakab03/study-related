package com.deepak.questions.int_q.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.lang3.ArrayUtils;

public class RandomUniqueNumberGenerator {

    public static void main(String[] args) throws IOException {
        final int[] expectedArray = expectedArray(1_000_000);
        
        List<Integer> list = shuffledList(expectedArray);
        
        String expected = Arrays.stream(expectedArray).mapToObj(i -> String.valueOf(i)).collect(Collectors.joining(","));
        String input = list.stream().map(i -> String.valueOf(i)).collect(Collectors.joining(","));

        Files.write(Paths.get(System.getProperty("user.dir") + "/expect.txt"), expected.getBytes());
        Files.write(Paths.get(System.getProperty("user.dir") + "/input.txt"), input.getBytes());
    }

    public static int[] expectedArray(int upperRange) {
        final int[] expectedArray = IntStream.range(0, upperRange).toArray();
        return expectedArray;
    }
    
    public static List<Integer> shuffledList(final int[] expectedArray) {
        int[] interArray = Arrays.copyOf(expectedArray, expectedArray.length);
        List<Integer> list = Arrays.asList(ArrayUtils.toObject(interArray));
        Collections.shuffle(list);
        return list;
    }
    
    public static int[] shuffledAsIntArray(final int[] expectedArray) {
        List<Integer> list = shuffledList(expectedArray);
        return list.stream().mapToInt(i -> i).toArray();
    }
}
