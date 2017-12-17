package com.junit.test;


import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.junit.customrunner.Order;
import com.junit.customrunner.SampleTestRunner;

@RunWith(value = SampleTestRunner.class)
public class SampleTest {

	@Test
	@Order(value = 1)
	public void test1() {
		System.err.println("test 1");
	}

	@Test
	@Order(value = 2)
	@Order(value = 4)
	public void test2() {
		System.err.println("test 2 or test 4");
	}

	@Test
	@Order(value = 3, params = { "2", "5", "created" })
	@Order(value = 5, params = { "3", "6", "updated" })
	public void test3(String param1, String param2,
			String assertMessage) {
		System.err.println("test 3 or 5 : param1=" + param1);
	}

	@Test
	@Order(value = 6)
	public void test4() {
		System.err.println("test 6 ");
	}
	
	@AfterClass
	public static void afterClass() {
		System.err.println("After Class");
	}
}