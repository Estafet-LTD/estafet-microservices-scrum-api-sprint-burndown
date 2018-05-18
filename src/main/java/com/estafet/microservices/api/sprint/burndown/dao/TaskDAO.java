package com.estafet.microservices.api.sprint.burndown.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.estafet.microservices.api.sprint.burndown.model.Story;
import com.estafet.microservices.api.sprint.burndown.model.Task;

@Repository
public class TaskDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	public Task update(Task task) {
		Story story = entityManager.find(Story.class, task.getStoryId());
		if (story != null) {
			task = story.add(task);
			entityManager.merge(story);	
		} else {
			
		}
		
		return task;
	}

}
