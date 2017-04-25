package com.ingdirect.es.testbase.template;


public enum HttpMethod {
	POST("POST"), 
	GET("GET");
	
	private final String name;
	
	private HttpMethod(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

}
