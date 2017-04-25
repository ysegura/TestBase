package com.ingdirect.es.testbase.template;

import org.junit.Test;

public abstract class AbstractMutablePojoTest<T> extends AbstractPojoTest<T> {

	@Test
	public abstract void shouldHaveAllPropertiesAccesiblesToSet() throws Exception;
}

