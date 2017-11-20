package com.deepak.questions.int_q.math_related;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class SimpleStuff {
	
	public List<Integer> constructFibonacciUpTill(int maxReqd) {
		return fibonacciUpTillUsingNoVars(maxReqd);
//		return fibonacciUpTillUsing2Vars(maxReqd);
//		return fibonacciUpTillUsing3Vars(i);
	}

	private List<Integer> fibonacciUpTillUsingNoVars(int maxReqd) {
		List<Integer> list = new ArrayList<>(maxReqd);
		list.add(0);
		list.add(1);
		System.out.format("%d, %d",list.get(0), list.get(1));
		for (int i = 2; list.get(i - 1) < maxReqd; i ++) {
			int num = list.get(i - 1) + list.get (i - 2);
			if (num > maxReqd) {
				break;
			}
			list.add(num);
			System.out.format(", %d", list.get(i));
		}
		return list;
	}
	
	
	private List<Integer> fibonacciUpTillUsing2Vars(int maxReqd) {
		List<Integer> list = new ArrayList<>();
		int a = 0, b = 1;
		list.add(a);
//		list.add(b);
		System.out.println(a);
		while (b < maxReqd) {
			System.out.println(b);
			list.add(b);
			b = a + b;
			a = b - a;
		}
		return list;
	}

	private List<Integer> fibonacciUpTillUsing3Vars(int maxReqd) {
		List<Integer> list = new ArrayList<>();
		int a = 0, b = 1, temp = 0;
		list.add(a);
		list.add(b);
		while (true) {
			System.out.println(a);
			temp = a + b;
			if (temp > maxReqd) {
				break;
			}
			list.add(temp);
			a = b;
			b = temp;
		}
		return list;
	}
}
