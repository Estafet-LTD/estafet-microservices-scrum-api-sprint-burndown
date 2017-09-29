package com.estafet.microservices.api.sprint.burndown.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.estafet.microservices.api.sprint.burndown.model.Sprint;

@Repository
public class SprintBurndownDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional(readOnly = true)
	public Sprint getSprintBurndown(int sprintId) {
		return entityManager.find(Sprint.class, new Integer(sprintId));
	}
	
	@Transactional
	public void create(Sprint sprint) {
		entityManager.persist(sprint);
	}
	
	@Transactional
	public void update(Sprint sprint) {
		entityManager.merge(sprint.recalculate());
	}

}
