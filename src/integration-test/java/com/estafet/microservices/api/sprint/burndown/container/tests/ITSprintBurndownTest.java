package com.estafet.microservices.api.sprint.burndown.container.tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.HttpURLConnection;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class })
public class ITSprintBurndownTest {

	@Before
	public void before() throws Exception {
		RestAssured.baseURI = System.getenv("SPRINT_BURNDOWN_SERVICE_URI");
	}

	@After
	public void after() throws Exception {
	}

	@Test
	public void testGetAPI() {
		get("/api").then()
			.statusCode(HttpURLConnection.HTTP_OK)
			.body("id", is(1))
			.body("number", is(1))
			.body("noDays", is(1));
	}

	@Test
	@DatabaseSetup("ITSprintBurndownTest-data.xml")
	public void testGetSprintBurndown() {
		get("/sprint/1000/burndown").then()
			.statusCode(HttpURLConnection.HTTP_OK)
			.body("id", is(1000))
			.body("number", is(1))
			.body("sprintDays.id", hasItems(1000, 1001, 1002, 1003, 1004))
			.body("sprintDays.dayNo", hasItems(1, 2, 3, 4, 5))
			.body("sprintDays.hoursTotal", hasItems(50, 38, 20, 15, 3))
			.body("sprintDays.sprintDay", hasItems("2016-10-01 00:00:00", "2016-10-02 00:00:00", "2016-10-03 00:00:00", "2016-10-04 00:00:00", "2016-10-05 00:00:00"));
	}
	
	@Test
	@DatabaseSetup("ITSprintBurndownTest-data.xml")
	public void testNewSprintConsumer() {
		fail("Not yet implemented");
	}
	
	@Test
	@DatabaseSetup("ITSprintBurndownTest-data.xml")
	public void testNewStoryConsumer() {
		fail("Not yet implemented");
	}
	
	@Test
	@DatabaseSetup("ITSprintBurndownTest-data.xml")
	public void testNewTaskConsumer() {
		fail("Not yet implemented");
	}
	
	@Test
	@DatabaseSetup("ITSprintBurndownTest-data.xml")
	public void testUpdateStoryConsumer() {
		fail("Not yet implemented");
	}
	
	@Test
	@DatabaseSetup("ITSprintBurndownTest-data.xml")
	public void testUpdateTaskConsumer() {
		fail("Not yet implemented");
	}

}
