package com.junit.customrunner;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.junit.internal.runners.statements.InvokeMethod;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.ParentRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

public class SampleTestRunner extends BlockJUnit4ClassRunner {

	private Map<Integer, FrameworkMethod> testMethods = null;

	Class<?> clazz;

	private void initilization(Class<?> clazz) {
		this.clazz = clazz;
		Map<Integer, FrameworkMethod> lclTestMethods = new HashMap<Integer, FrameworkMethod>();
		Method[] classMethods = clazz.getDeclaredMethods();
		for (int i = 0; i < classMethods.length; i++) {
			Method classMethod = classMethods[i];
			TestOrder testOrder = classMethod.getAnnotation(TestOrder.class);
			if (testOrder != null) {
				Arrays.stream(testOrder.value()).forEach(e -> {
					lclTestMethods.put(e.value(), new FrameworkMethod(classMethod));
				});
			}

			Order order = classMethod.getAnnotation(Order.class);
			if (order != null) {
				lclTestMethods.put(order.value(), new FrameworkMethod(classMethod));
			}
		}
		testMethods = new TreeMap<Integer, FrameworkMethod>(lclTestMethods);

	}

	@Override
	protected void collectInitializationErrors(List<Throwable> errors) {
		// do nothing
	}

	//
	@Override
	protected List<FrameworkMethod> computeTestMethods() {
		initilization(super.getTestClass().getJavaClass());
		return testMethods.values().stream().collect(Collectors.toList());
	}

	public SampleTestRunner(Class<?> clazz) throws InitializationError {
		super(clazz);

	}

	protected Statement methodInvoker(FrameworkMethod method, Object test) {
		Integer key = getFirstKeyByValue(method);
		if (key != null) {
			testMethods.remove(key);
		}
		return new SampleInvokeMethod(method, test, key);
	}

	public Integer getFirstKeyByValue(FrameworkMethod value) {
		return testMethods.entrySet().stream().filter(entry -> Objects.equals(entry.getValue(), value))
				.map(Map.Entry::getKey).findFirst().orElse(null);
	}

}
