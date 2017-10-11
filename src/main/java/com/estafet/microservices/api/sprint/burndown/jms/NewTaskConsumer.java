package com.estafet.microservices.api.sprint.burndown.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.estafet.microservices.api.sprint.burndown.dao.SprintBurndownDAO;
import com.estafet.microservices.api.sprint.burndown.model.Sprint;
import com.estafet.microservices.api.sprint.burndown.model.Task;
import com.estafet.microservices.api.sprint.burndown.services.StoryService;

@Component
public class NewTaskConsumer {

	@Autowired
	private SprintBurndownDAO sprintBurndownDAO;
	
	@Autowired
	private StoryService storyService;

	@Transactional
	@JmsListener(destination = "new.task.topic", containerFactory = "myFactory")
	public void onMessage(String message) {
		Task task = Task.fromJSON(message);
		Integer sprintId = storyService.getStory(task).getSprintId();
		if (sprintId != null) {
			Sprint sprint = sprintBurndownDAO.getSprintBurndown(sprintId).update(task);
			sprintBurndownDAO.update(sprint);
		}
	}

}
