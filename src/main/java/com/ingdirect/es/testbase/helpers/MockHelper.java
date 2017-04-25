package com.ingdirect.es.testbase.helpers;

import java.lang.reflect.Field;

public class MockHelper {

	public static void mockStaticField(Class<?> clazz, String fieldname, Object mock)
			throws NoSuchFieldException, IllegalAccessException {
		final Field mockedField = clazz.getDeclaredField(fieldname);
		mockedField.setAccessible(true);
		mockedField.set(clazz, mock);
	}
	
	public static void mockField(Object object, String fieldname, Object mock)
			throws NoSuchFieldException, IllegalAccessException {
		final Field mockedField = object.getClass().getDeclaredField(fieldname);
		mockedField.setAccessible(true);
		mockedField.set(object, mock);
	}
}
