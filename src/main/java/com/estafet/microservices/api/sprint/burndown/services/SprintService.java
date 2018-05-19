package com.estafet.microservices.api.sprint.burndown.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.estafet.microservices.api.sprint.burndown.dao.SprintBurndownDAO;
import com.estafet.microservices.api.sprint.burndown.dao.StoryDAO;
import com.estafet.microservices.api.sprint.burndown.dao.TaskDAO;
import com.estafet.microservices.api.sprint.burndown.model.Sprint;
import com.estafet.microservices.api.sprint.burndown.model.Story;
import com.estafet.microservices.api.sprint.burndown.model.Task;

@Service
public class SprintService {

	@Autowired
	private SprintBurndownDAO sprintBurndownDAO;
	
	@Autowired
	private TaskDAO taskDAO;
	
	@Autowired
	private StoryDAO storyDAO;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@SuppressWarnings("unchecked")
	public List<String> getSprintDays(int sprintId) {
		return restTemplate.getForObject(System.getenv("SPRINT_API_SERVICE_URI") + "/sprint/{id}/days",
				List.class, sprintId);
	}
		
	@Transactional(readOnly = true)
	public Sprint getSprintBurndown(int sprintId) {
		Sprint sprintBurndown = sprintBurndownDAO.getSprintBurndown(sprintId);
		return sprintBurndown.init();
	}
	
	@Transactional
	public void newSprint(Sprint sprint) {
		if (sprintBurndownDAO.getSprintBurndown(sprint.getId()) == null) {
			sprintBurndownDAO.create(sprint.addDays(getSprintDays(sprint.getId())));
		}
	}

	@Transactional
	public void updateStory(Story story) {
		story = storyDAO.update(story);
		if (story.getStatus().equals("In Progress")) {
			Sprint sprint = story.getStorySprint();
			sprint.update(story);
			sprintBurndownDAO.update(sprint);
		}
	}

	@Transactional
	public void newTask(Task task) {
		task = taskDAO.update(task);
		if (task.getTaskStory().getStorySprint() != null) {
			sprintBurndownDAO.update(task.getTaskStory().getStorySprint().update(task));
		}
	}

	@Transactional
	public void updateTask(Task task) {
		task = taskDAO.update(task);
		if (task.getRemainingUpdated() != null) {
			sprintBurndownDAO.update(task.getTaskStory().getStorySprint().update(task));
		}
	}

	@Transactional
	public void newStory(Story story) {
		storyDAO.create(story);
	}
	
}
