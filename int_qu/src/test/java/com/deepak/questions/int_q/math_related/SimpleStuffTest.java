package com.deepak.questions.int_q.math_related;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

public class SimpleStuffTest {

	private SimpleStuff simpleStuff = new SimpleStuff();
	
	@Test public void
	whenConstructFibonacciUpTill_givenMaxNumber_shouldOutputCorrectValues() {
		List<Integer> list = simpleStuff.constructFibonacciUpTill(10);
		
		assertThat(list, contains(0, 1, 1, 2, 3, 5, 8));
	}
}
