package com.cb.demo.utils;

/**
 * Simple interface that defines methods for converting between Java types
 * and JSON.
 * 
 * @author Tony Piazza
 */
public interface JsonConverter {
	<T> T fromJson(String source, Class<T> type);

	<T> String toJson(T source);
}