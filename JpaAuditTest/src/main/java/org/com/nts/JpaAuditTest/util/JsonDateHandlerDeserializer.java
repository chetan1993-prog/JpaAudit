/**
 * 
 */
package org.com.nts.JpaAuditTest.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

/**
 * 
 * @author chetan
 *
 */
public class JsonDateHandlerDeserializer extends StdDeserializer<Date> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected JsonDateHandlerDeserializer(Class<?> vc) {
		super(vc);

	}

	public JsonDateHandlerDeserializer() {
		this(null);
	}

	@Override
	public Date deserialize(JsonParser parser, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {

		String date = parser.getText();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		try {
			System.out.println("parse date");
			return dateFormat.parse(date);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("cant parse the date");
			// throw serializerException;
		}
		return null;

	}

}
