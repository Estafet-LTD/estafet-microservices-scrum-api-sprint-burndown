package com.estafet.microservices.api.sprint.burndown.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.estafet.microservices.api.sprint.burndown.dao.SprintBurndownDAO;
import com.estafet.microservices.api.sprint.burndown.model.Sprint;
import com.estafet.microservices.api.sprint.burndown.model.Task;

@Component
public class UpdateTaskConsumer {

	@Autowired
	private SprintBurndownDAO sprintBurndownDAO;

	@Transactional
	@JmsListener(destination = "update.task.topic", containerFactory = "myFactory")
	public void onMessage(Task task) {
		if (task.getRemainingUpdated() != null) {
			int sprintId = task.getStory().getSprintId();
			Sprint sprint = sprintBurndownDAO.getSprintBurndown(sprintId).update(task);
			sprintBurndownDAO.update(sprint);
		}
	}

}
