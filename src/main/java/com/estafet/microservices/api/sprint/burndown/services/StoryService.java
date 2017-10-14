package com.estafet.microservices.api.sprint.burndown.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.estafet.microservices.api.sprint.burndown.dao.SprintBurndownDAO;
import com.estafet.microservices.api.sprint.burndown.model.Sprint;
import com.estafet.microservices.api.sprint.burndown.model.Story;
import com.estafet.microservices.api.sprint.burndown.model.Task;

@Service
public class StoryService {

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private SprintBurndownDAO sprintBurndownDAO;

	@Autowired
	private TaskService taskService;

	public Story getStory(Task task) {
		return restTemplate.getForObject(System.getenv("STORY_API_SERVICE_URI") + "/story/{id}", Story.class,
				task.getStoryId());
	}
	
	@Transactional
	public void updateStory(Story story) {
		if (story.getStatus().equals("In Progress")) {
			Sprint sprint = sprintBurndownDAO.getSprintBurndown(story.getSprintId());
			sprint.update(taskService.getTasks(story));
			sprintBurndownDAO.update(sprint);
		}
	}

}
