package com.estafet.microservices.api.sprint.burndown.model;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Task {

	private Integer id;

	private Integer remainingHours;

	private String remainingUpdated;

	private Integer storyId;

	public Integer getStoryId() {
		return storyId;
	}

	public Integer getId() {
		return id;
	}

	public Integer getRemainingHours() {
		return remainingHours;
	}

	public String getRemainingUpdated() {
		return remainingUpdated;
	}
	
	public static Task fromJSON(String message) {
		try {
			return new ObjectMapper().readValue(message, Task.class);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
