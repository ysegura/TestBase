package com.ingdirect.es.testbase.doubles;

import java.lang.reflect.Constructor;
import java.util.Arrays;

import io.github.benas.randombeans.EnhancedRandomBuilder;
import io.github.benas.randombeans.FieldDefinitionBuilder;
import io.github.benas.randombeans.api.EnhancedRandom;
import io.github.benas.randombeans.api.Randomizer;

public class RandomGenerator {

	private volatile static EnhancedRandom defaultEnhancedRandom;
	
	
	private static EnhancedRandom getDefaultRandomizer(){
		
		if (defaultEnhancedRandom == null){
			synchronized (RandomGenerator.class) {
				if (defaultEnhancedRandom == null){
					

					defaultEnhancedRandom = EnhancedRandomBuilder.aNewEnhancedRandomBuilder()
						.collectionSizeRange( 1, 5 )
						.scanClasspathForConcreteTypes( true )
						.build();
				}
			}
		}
		return defaultEnhancedRandom;
		
	}
	
	public static <T> T nextObject(Class<T> clazz, String... excludedFields){
		return getDefaultRandomizer().nextObject( clazz, excludedFields);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T nextImmutableObject(Class<T> clazz, int... excludedPositions){
		Constructor<?> constructor = clazz.getConstructors()[0];
		Class<?>[] parameterTypes = constructor.getParameterTypes();
		Object[] generatedParameters = Arrays.stream( parameterTypes ).map( t ->  getDefaultRandomizer().nextObject(t)).toArray();
		
		Arrays.stream( excludedPositions).forEach( i -> generatedParameters[i] = null );
		
		T generatedInstance = null;
		
		try {
			generatedInstance = (T) constructor.newInstance( generatedParameters );
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return generatedInstance;
	}
	
	public static EnhancedRandomBuilder getBasicRandomBuilder(){
		return EnhancedRandomBuilder.aNewEnhancedRandomBuilder();
	}
	
}
