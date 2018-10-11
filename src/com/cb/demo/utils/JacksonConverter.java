package com.cb.demo.utils;

import static com.couchbase.client.deps.com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

import java.io.IOException;
import java.text.SimpleDateFormat;

import com.cb.demo.exceptions.ConversionException;
import com.couchbase.client.deps.com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.couchbase.client.deps.com.fasterxml.jackson.core.JsonProcessingException;
import com.couchbase.client.deps.com.fasterxml.jackson.databind.ObjectMapper;
import com.couchbase.client.deps.com.fasterxml.jackson.databind.SerializationFeature;


/**
 * JsonConverter implementation based on the Jackson Databind library.
 * 
 * @author Tony Piazza
 */
public class JacksonConverter implements JsonConverter {
	private final ObjectMapper mapper = 
		new ObjectMapper()
			.configure(FAIL_ON_UNKNOWN_PROPERTIES, false)
			.setSerializationInclusion(Include.NON_NULL)
			.enable(SerializationFeature.INDENT_OUTPUT)
			.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ"));

	public <T> T fromJson(String source, Class<T> valueType) {
		try {
			return mapper.readValue(source, valueType);
		} catch (IOException e) {
			throw new ConversionException(e);
		}
	}

	public <T> String toJson(T source) {
		try {
			return mapper.writeValueAsString(source);
		} catch (JsonProcessingException e) {
			throw new ConversionException(e);
		}
	}
}