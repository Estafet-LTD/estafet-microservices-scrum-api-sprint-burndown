package com.estafet.microservices.api.sprint.burndown.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.type.TypeReference;
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

	@SuppressWarnings({ "rawtypes" })
	@JsonIgnore
	public List<Task> getTasks() {
		List objects = new RestTemplate().getForObject(System.getenv("TASK_API_SERVICE_URI") + "/story/{id}/tasks", List.class,
				id);
		List<Task> tasks = new ArrayList<Task>();
		ObjectMapper mapper = new ObjectMapper();
		for (Object object : objects) {
			Task task = mapper.convertValue(object, new TypeReference<Task>() {
			});
			tasks.add(task);
		}
		return tasks;
	}

}
