package com.ingdirect.es.testbase.template;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;

import org.junit.Test;

public abstract class AbstractPojoTest <T> {
	
	protected T pojo;
	protected T equalsPojo;
	protected T otherPojo;
	
	
	public abstract void beforeTests();
	
	@Test
	public void shouldBeEquals() {
		assertThat( pojo, is( equalsPojo ) );
	}
	
	@Test
	public void shouldBeDistinct() {
		assertThat( pojo, is( not( otherPojo ) ) );
	}
	
	@Test
	public void shouldProduceSameHashCode() {
		assertThat( pojo.hashCode(), is( equalsPojo.hashCode() ) );
	}
	
	@Test
	public void shouldProduceDistinctHashCode() {
		assertThat( pojo.hashCode(), is( not( otherPojo.hashCode() ) ) );
	}
	
	@Test
	public void equalsMethodCheckTheType() {
		assertFalse( pojo.equals( "a string" ) );
	}
	
	@Test
	public void equalsMethodWithNullObject() {
		assertFalse( pojo == null );
	}
	
	@Test
	public void equalsMethodWithSameObject() {
		final T samePojo = pojo;
		assertTrue( pojo.equals( samePojo ) );
	}
	
	@Test
	public void shouldHaveMethodAccessors() throws IntrospectionException, IllegalAccessException,
		IllegalArgumentException, InvocationTargetException {
		for (PropertyDescriptor propertyDescriptor : Introspector.getBeanInfo( pojo.getClass() )
				.getPropertyDescriptors()) {
			assertNotNull( propertyDescriptor.getReadMethod().invoke( pojo ) );
		}
	}
	
	@Test
	public abstract void shouldHaveAllPropertiesAccesibles();

}
