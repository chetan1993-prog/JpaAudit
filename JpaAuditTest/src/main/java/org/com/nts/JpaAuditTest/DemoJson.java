package org.com.nts.JpaAuditTest;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.com.nts.JpaAuditTest.entity.EmployeeEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;

public class DemoJson {

	public static void main(String[] args) {

		EmployeeEntity emEntity = new EmployeeEntity(1L, "chetan", "c.kings999@gmail.com", "pune");
		EmployeeEntity emEntity2 = new EmployeeEntity(1L, "chetan", "rose@gmail.com", "mumbai");

		ObjectMapper mapper = new ObjectMapper();

		TypeReference<HashMap<String, Object>> type = new TypeReference<HashMap<String, Object>>() {

		};

		String oldJson;

		String newJson;
		try {
			newJson = mapper.writeValueAsString(emEntity2);
			oldJson = mapper.writeValueAsString(emEntity);

			System.out.println("new Json" + newJson);
			System.out.println("old Json" + oldJson);

			Map<String, Object> leftMap = mapper.readValue(oldJson, type);
			Map<String, Object> rightMap = mapper.readValue(newJson, type);

			MapDifference<String, Object> difference = Maps.difference(leftMap, rightMap);

			Map<String, Object> newjsonMap = new LinkedHashMap<String, Object>();
			difference.entriesDiffering().entrySet().forEach(x -> {

				newjsonMap.put(x.getKey(), x.getValue().rightValue());
				System.out.println(x.getKey() + "--" + x.getValue().rightValue());
			});

			System.out.println("" + newjsonMap.toString());
			System.out.println("diffrence::" + difference.toString());

		} catch (JsonProcessingException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
