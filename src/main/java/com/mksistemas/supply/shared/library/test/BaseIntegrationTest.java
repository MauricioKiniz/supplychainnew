package com.mksistemas.supply.shared.library.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;

public class BaseIntegrationTest {
	public String serializeCommand(Object command)
			throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		JsonMapper.builder().configure(SerializationFeature.WRAP_ROOT_VALUE,
				false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		return ow.writeValueAsString(command);
	}
}
