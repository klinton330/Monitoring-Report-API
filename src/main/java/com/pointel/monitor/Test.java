package com.pointel.monitor;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Test {
	
	public static void main(String[] args) throws JsonMappingException, JsonProcessingException {
        // Your JSON string
        String jsonString = "{ \"date\": \"2023-12-19\", \"businessUnit\": \"ALL\", \"hourly\": [ { \"id\": 1, \"dateField\": \"2023-12-19\", \"time\": \"9:00 AM-10:00 AM\", \"businessUnit\": \"PGF\", \"outbound\": 78, \"inbound\": 102, \"offerred\": 100, \"abandoned\": 52, \"answered\": 48, \"transferred\": 5, \"apiExecution\": 552, \"sms\": 0, \"globalException\": 0, \"customerDisconnect\": 5, \"callflowDisconnect\": 50 }, { \"id\": 2, \"dateField\": \"2023-12-19\", \"time\": \"9:00 AM-10:00 AM\", \"businessUnit\": \"GIB\", \"outbound\": 78, \"inbound\": 102, \"offerred\": 100, \"abandoned\": 52, \"answered\": 48, \"transferred\": 5, \"apiExecution\": 552, \"sms\": 0, \"globalException\": 0, \"customerDisconnect\": 5, \"callflowDisconnect\": 50 }, { \"id\": 3, \"dateField\": \"2023-12-19\", \"time\": \"9:00 AM-10:00 AM\", \"businessUnit\": \"POJ\", \"outbound\": 78, \"inbound\": 102, \"offerred\": 100, \"abandoned\": 52, \"answered\": 48, \"transferred\": 5, \"apiExecution\": 552, \"sms\": 0, \"globalException\": 0, \"customerDisconnect\": 5, \"callflowDisconnect\": 50 } ] }";

        // Convert JSON string to Object
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode originalObject = objectMapper.readValue(jsonString, ObjectNode.class);

        // Extract "time" and "outbound" values
        ArrayNode hourlyArray = (ArrayNode) originalObject.get("hourly");
        List<String> times = new ArrayList<>();
        List<Integer> outbounds = new ArrayList<>();

        for (int i = 0; i < hourlyArray.size(); i++) {
            ObjectNode entry = (ObjectNode) hourlyArray.get(i);
            times.add(entry.get("time").asText());
            outbounds.add(entry.get("outbound").asInt());
        }

        // Add new arrays to the original object
        originalObject.put("times", objectMapper.valueToTree(times));
        originalObject.put("outbounds", objectMapper.valueToTree(outbounds));

        // Convert the modified object back to JSON string
        String modifiedJsonString = objectMapper.writeValueAsString(originalObject);
        System.out.println(modifiedJsonString);
    }

}
