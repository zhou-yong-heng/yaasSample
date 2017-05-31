package com.sample.wishlistDemo.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperUtil {
	
	
    private final static ObjectMapper mapper = new ObjectMapper();
	
	
	private ObjectMapperUtil(){
		
	}
	
	public static ObjectMapper instance(){
		return mapper;
	}
	
	public static String toJson(Object o){
		try {
			return mapper.writeValueAsString(o);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object fromJson(String json, Class clazz){
		try {
			return mapper.readValue(json, clazz);
		} catch (JsonParseException e) {
			throw new RuntimeException(e);
		} catch (JsonMappingException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	

}
