package com.junit.customrunner;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.internal.runners.statements.InvokeMethod;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

public class SampleInvokeMethod extends Statement {

	private final FrameworkMethod testMethod;
	private final Object target;
	private final int index;

	public SampleInvokeMethod(FrameworkMethod testMethod, Object target, int index) {
		this.testMethod = testMethod;
		this.target = target;
		this.index = index;
	}

	@Override
	public void evaluate() throws Throwable {
		Order order = testMethod.getAnnotation(Order.class);

		if (order != null) {
			testMethod.invokeExplosively(target, order.params());
			return;
		}

		TestOrder testorder = testMethod.getAnnotation(TestOrder.class);
		if (testorder != null) {
			order = Arrays.stream(testorder.value()).filter(e -> e.value() == index).findFirst().orElse(null);
			if (order != null) {
				testMethod.invokeExplosively(target, order.params());
				return;
			}
		}

		testMethod.invokeExplosively(target);

	}

}
