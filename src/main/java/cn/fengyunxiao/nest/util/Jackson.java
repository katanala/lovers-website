package cn.fengyunxiao.nest.util;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Jackson {
	private static final ObjectMapper mapper = new ObjectMapper();
	
	private Jackson() {
	}

	public static ObjectMapper getObjectMapper() {
		return mapper;
	}
	
	public static String toJson(Object object) throws JsonProcessingException {
		return mapper.writeValueAsString(object);
	}
	public static <T> T toObject(String string, Class<T> T) throws IOException {
		return mapper.readValue(string, T);
	}
}
