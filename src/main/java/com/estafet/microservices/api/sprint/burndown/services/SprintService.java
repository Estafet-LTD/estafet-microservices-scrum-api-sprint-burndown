package com.estafet.microservices.api.sprint.burndown.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SprintService {

	@SuppressWarnings("unchecked")
	public List<String> getSprintDays(int sprintId) {
		return new RestTemplate().getForObject(System.getenv("SPRINT_API_SERVICE_URI") + "/sprint/{id}/days",
				List.class, sprintId);
	}
	
	public String getSprintDay(int sprintId) {
		return new RestTemplate().getForObject(System.getenv("SPRINT_API_SERVICE_URI") + "/sprint/{id}/day",
				String.class, sprintId);
	}
}
