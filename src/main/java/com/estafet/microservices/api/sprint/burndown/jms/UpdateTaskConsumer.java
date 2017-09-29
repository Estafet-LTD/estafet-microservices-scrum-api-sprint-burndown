package com.estafet.microservices.api.sprint.burndown.jms;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.estafet.microservices.api.sprint.burndown.dao.SprintBurndownDAO;
import com.estafet.microservices.api.sprint.burndown.model.Sprint;
import com.estafet.microservices.api.sprint.burndown.model.Task;
import com.estafet.microservices.api.sprint.burndown.services.StoryService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component(value = "updateTaskConsumer")
public class UpdateTaskConsumer {

	@Autowired
	private SprintBurndownDAO sprintBurndownDAO;

	@Autowired
	private StoryService storyService;

	public void onMessage(String message) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			Task task = mapper.readValue(message, Task.class);
			if (task.getRemainingUpdated() != null) {
				int sprintId = storyService.getTaskStory(task.getId()).getSprintId();
				Sprint sprint = sprintBurndownDAO.getSprintBurndown(sprintId).update(task);
				sprintBurndownDAO.update(sprint);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
