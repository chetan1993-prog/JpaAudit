package org.com.nts.JpaAuditTest.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author chetan
 *
 */
public class CustomJsonDeserializer extends JsonDeserializer<String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {

		System.out.println("this is Custom Json Deserilizer");
		System.out.println("---");
		ObjectMapper mapper = (ObjectMapper) p.getCodec();
		JsonNode node = mapper.readTree(p);
		return mapper.writeValueAsString(node);
	}

}
