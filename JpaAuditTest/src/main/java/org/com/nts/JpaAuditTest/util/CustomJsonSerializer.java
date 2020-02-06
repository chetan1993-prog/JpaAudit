/**
 * 
 */
package org.com.nts.JpaAuditTest.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * 
 * @author chetan
 *
 */
public class CustomJsonSerializer extends JsonSerializer<String> {

	@Override
	public void serialize(String value, JsonGenerator jsonGenerator, SerializerProvider serializers)
			throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode actualObj = mapper.readTree(value);
		jsonGenerator.writeObject(actualObj);

	}

}
