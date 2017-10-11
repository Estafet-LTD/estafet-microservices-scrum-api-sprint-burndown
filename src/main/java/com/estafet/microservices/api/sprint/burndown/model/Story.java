package com.estafet.microservices.api.sprint.burndown.model;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Story {

	private Integer id;

	private String status;

	private Integer sprintId;

	public Integer getId() {
		return id;
	}

	public String getStatus() {
		return status;
	}

	public Integer getSprintId() {
		return sprintId;
	}
	
	public static Story fromJSON(String message) {
		try {
			return new ObjectMapper().readValue(message, Story.class);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
