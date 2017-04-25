package com.ingdirect.es.testbase.template;

import static org.junit.Assert.fail;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;
import org.springframework.util.ReflectionUtils;

import com.ingdirect.es.testbase.doubles.RandomGenerator;
import sun.reflect.Reflection;

public abstract class AbstractMutablePojoRandomizedTest<T>  extends AbstractPojoRandomizedTest<T> {
		
	@SuppressWarnings("unchecked")
	@Override
	@Before
	public void beforeTests() {
		
		Class<?> clazz = getConcreteClass();
		pojo =  (T) RandomGenerator.nextObject(clazz);
		equalsPojo = (T) clonePojoWithJadira( pojo );
		otherPojo =  (T) RandomGenerator.nextObject( clazz );
	}
	
	@Test	
	public void shouldHaveAllPropertiesAccesiblesToSet() {
		
		try {
			for (PropertyDescriptor property : Introspector.getBeanInfo( getConcreteClass() ).getPropertyDescriptors()){
				Field field = ReflectionUtils.findField( getConcreteClass(), property.getName());
				Method writeMethod = property.getWriteMethod();
				if (writeMethod != null && field != null){					
					field.setAccessible( true );
					writeMethod.invoke( pojo, field.get( otherPojo ));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
