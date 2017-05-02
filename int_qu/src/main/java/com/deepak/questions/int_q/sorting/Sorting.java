package com.deepak.questions.int_q.sorting;

public interface Sorting {

    Integer[] sort(Integer input[]);
    
    public static void swap(Integer[] input, int i, int j) {
        int temp = input[i];
        input[i] = input[j];
        input[j] = temp;
    }
}
