package com.estafet.microservices.api.sprint.burndown.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.estafet.microservices.api.sprint.burndown.model.Story;

@Service
public class StoryService {

	public Story getTaskStory(int taskId) {
		return new RestTemplate().getForObject(System.getenv("STORY_API_SERVICE_URI") + "/task/{id}/story",
				Story.class, taskId);
	}
	
}
