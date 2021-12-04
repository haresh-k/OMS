package com.oms.utils;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Wrapper<T> {
	public static <T> Wrapper<T> wrap(T data) {return new Wrapper<>(data);}
	
	@JsonProperty 
	private final T data;
	
	private Wrapper(T data) {this.data = data;}
}
