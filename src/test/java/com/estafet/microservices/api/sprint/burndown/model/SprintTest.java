package com.estafet.microservices.api.sprint.burndown.model;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

public class SprintTest {

	Sprint sprint;
	Task task1, task2, task3;

	@Before
	public void before() {
		sprint = new Sprint().addDays(
				Arrays.asList(new String[] { "2017-10-25 00:00:00", "2017-10-26 00:00:00", "2017-10-27 00:00:00" }));
		task1 = new Task().setId(1).setInitialHours(5).setRemainingHours(5);
		task2 = new Task().setId(2).setInitialHours(8).setRemainingHours(8);
		task3 = new Task().setId(3).setInitialHours(3).setRemainingHours(3);
		sprint.update(task1);
		sprint.update(task2);
		sprint.update(task3);
	}

	@Test
	public void testUpdateTaskInitial() {
		assertEquals(16, sprint.recalculate().init().getSprintDays().get(0).getHoursTotal().intValue());
	}
	
	@Test
	public void testUpdateTaskDay1() {
		task1.setRemainingUpdated("2017-10-25 00:00:00");
		sprint.update(task1);
		
		task2.setRemainingUpdated("2017-10-25 00:00:00");
		sprint.update(task2);
		
		task3.setRemainingUpdated("2017-10-25 00:00:00");
		sprint.update(task3);
		
		sprint.recalculate().init();
		
		assertEquals(16, sprint.getSprintDays().get(0).getHoursTotal().intValue());
		assertEquals(16, sprint.getSprintDays().get(1).getHoursTotal().intValue());
	}
	
	@Test
	public void testUpdateTaskDay2() {
		task1.setRemainingUpdated("2017-10-25 00:00:00");
		sprint.update(task1);
		
		task2.setRemainingUpdated("2017-10-25 00:00:00");
		sprint.update(task2);
		
		task3.setRemainingUpdated("2017-10-25 00:00:00");
		sprint.update(task3);

		task1.setRemainingUpdated("2017-10-26 00:00:00");
		task1.setRemainingHours(3);
		sprint.update(task1);
		
		task2.setRemainingUpdated("2017-10-26 00:00:00");
		task2.setRemainingHours(3);
		sprint.update(task2);
		
		task3.setRemainingUpdated("2017-10-26 00:00:00");
		task3.setRemainingHours(1);
		sprint.update(task3);
		
		sprint.recalculate().init();
		
		assertEquals(16, sprint.getSprintDays().get(0).getHoursTotal().intValue());
		assertEquals(16, sprint.getSprintDays().get(1).getHoursTotal().intValue());
		assertEquals(7, sprint.getSprintDays().get(2).getHoursTotal().intValue());
		
	}

}
