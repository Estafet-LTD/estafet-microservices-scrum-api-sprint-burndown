package com.estafet.microservices.api.sprint.burndown.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.estafet.microservices.api.sprint.burndown.dao.SprintBurndownDAO;
import com.estafet.microservices.api.sprint.burndown.model.Sprint;
import com.estafet.microservices.api.sprint.burndown.model.Story;
import com.estafet.microservices.api.sprint.burndown.services.TaskService;

@Component
public class UpdateStoryConsumer {

	@Autowired
	private SprintBurndownDAO sprintBurndownDAO;

	@Autowired
	private TaskService taskService;

	@Transactional
	@JmsListener(destination = "update.story.topic", containerFactory = "myFactory")
	public void onMessage(Story story) {
		if (story.getStatus().equals("In Progress")) {
			Sprint sprint = sprintBurndownDAO.getSprintBurndown(story.getSprintId());
			sprint.update(taskService.getTasks(story));
			sprintBurndownDAO.update(sprint);
		}
	}

}
