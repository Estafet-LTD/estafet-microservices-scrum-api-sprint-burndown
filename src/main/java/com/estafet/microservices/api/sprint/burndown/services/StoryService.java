package com.estafet.microservices.api.sprint.burndown.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.estafet.microservices.api.sprint.burndown.model.Story;
import com.estafet.microservices.api.sprint.burndown.model.Task;

@Service
public class StoryService {

	@Autowired
	private RestTemplate restTemplate;

	public Story getStory(Task task) {
		return restTemplate.getForObject(System.getenv("STORY_API_SERVICE_URI") + "/story/{id}", Story.class,
				task.getStoryId());
	}

}
