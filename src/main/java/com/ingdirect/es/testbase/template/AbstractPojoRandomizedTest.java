package com.ingdirect.es.testbase.template;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;

import org.jadira.cloning.BasicCloner;
import org.jadira.cloning.api.Cloner;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.ReflectionUtils;

import com.ingdirect.es.testbase.doubles.RandomGenerator;


public abstract class AbstractPojoRandomizedTest<T>  extends AbstractPojoTest<T> {
	
	protected Class<?> getConcreteClass(){
		return (Class<?>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	@SuppressWarnings("unchecked")
	@Override
	@Before
	public void beforeTests() {
		
		Class<?> clazz = getConcreteClass();
		pojo =  (T) RandomGenerator.nextImmutableObject(clazz);
		equalsPojo = (T) clonePojoWithJadira( pojo );
		otherPojo =  (T) RandomGenerator.nextImmutableObject( clazz );
	}
	
	
	protected T clonePojoWithJadira(T original){
		Cloner cloner = new BasicCloner();
		return cloner.clone( original );
	}
	
	protected void  changeFinalField(T object, String fieldName, Object newValue) throws NoSuchFieldException, IllegalAccessException {
		Field distinctField = getConcreteClass().getDeclaredField( fieldName );
		distinctField.setAccessible( true );
		distinctField.set( object, newValue );
	}
	
//	private T clonePojoWithOrika(T original){
//		DefaultMapperFactory mapperFactory = new DefaultMapperFactory.Builder().useAutoMapping( true ).useBuiltinConverters( true ).build();
//		mapperFactory.classMap( AcquisitionOrderAggregateRootImpl.class, AcquisitionOrderAggregateRootImpl.class ).byDefault().register();
//		T cloned = (T) mapperFactory.getMapperFacade().map( original, AcquisitionOrderAggregateRootImpl.class);
//		
//		return cloned;
//	}
//
//	private T clonePojoWithMapperComponent(T original){
//		
//		T cloned = (T) mapper.map( original, AcquisitionOrderAggregateRootImpl.class );
//		
//		return cloned;
//	}
	
	
//	private AcquisitionOrderAggregateRootImpl clonePojoWithReflection(AcquisitionOrderAggregateRootImpl original){
//		
//		Constructor<?> constructor = AcquisitionOrderAggregateRootImpl.class.getConstructors()[0];
//		
//		List<String> fieldNames = Arrays.stream( constructor.getTypeParameters())
//			.map( TypeVariable::getName )
//			.collect( Collectors.toList() );
//		
//		List<Object> fieldValues = fieldNames.stream().map( f -> readField( original, f ) ).collect( Collectors.toList() );
//		
//		AcquisitionOrderAggregateRootImpl equal = null;
//		
//		try {
//			equal = (AcquisitionOrderAggregateRootImpl) constructor.newInstance( fieldValues.toArray( new Object[fieldValues.size()] ) );
//		} catch (Exception  e) {
//			
//			e.printStackTrace();
//		}
//		
//		return equal;
//	}
	
//	private Object readField(Object object, String fieldName){
//		Object result = null;
//		try {
//			result = FieldUtils.readField( object, fieldName );
//		} catch (IllegalAccessException e) {
//			e.printStackTrace();
//		}
//		
//		return result;
//	}
	
	@Test
	@Override
	public void shouldHaveAllPropertiesAccesibles() {
		
		try {
			for (PropertyDescriptor property : Introspector.getBeanInfo( getConcreteClass() ).getPropertyDescriptors()){
				Field field = ReflectionUtils.findField( getConcreteClass(), property.getName());
				if (field != null){
					field.setAccessible( true );
					assertThat( property.getReadMethod().invoke( pojo ), is( field.get( pojo )) );
				}
			}
		} catch (Exception e) {
			fail();
		}
	}
}
