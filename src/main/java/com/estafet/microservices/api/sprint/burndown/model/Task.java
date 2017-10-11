package com.estafet.microservices.api.sprint.burndown.model;

import java.io.Serializable;

import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Task implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6770664156876206101L;

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

	@JsonIgnore
	public Story getStory() {
		return new RestTemplate().getForObject(System.getenv("STORY_API_SERVICE_URI") + "/story/{id}", Story.class, storyId);
	}

}
