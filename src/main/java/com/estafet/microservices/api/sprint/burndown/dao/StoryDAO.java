package com.estafet.microservices.api.sprint.burndown.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.estafet.microservices.api.sprint.burndown.model.Sprint;
import com.estafet.microservices.api.sprint.burndown.model.Story;

@Repository
public class StoryDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	public Story create(Story story) {
		if (story.getSprintId() != null) {
			Sprint sprint = entityManager.find(Sprint.class, story.getSprintId());
			story = sprint.add(story);
			entityManager.merge(sprint);
		} else {
			entityManager.persist(story);	
		}
		return story;
	}
	
	
	@Transactional
	public Story update(Story story) {
		if (story.getSprintId() != null) {
			Sprint sprint = entityManager.find(Sprint.class, story.getSprintId());
			story = sprint.add(story);
			entityManager.merge(story);
		}
		return story;
	}

}
