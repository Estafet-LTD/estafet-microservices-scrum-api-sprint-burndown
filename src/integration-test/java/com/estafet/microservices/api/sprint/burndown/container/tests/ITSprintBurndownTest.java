package com.estafet.microservices.api.sprint.burndown.container.tests;

import org.junit.Before;
import org.junit.Test;

import java.net.HttpURLConnection;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

import io.restassured.RestAssured;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class })
public class ITSprintBurndownTest {

	@Before
	public void before() throws Exception {
		RestAssured.baseURI = System.getenv("SPRINT_BURNDOWN_SERVICE_URI");
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
		NewSprintTopicProducer.send("{\r\n" + 
				"    \"id\": 2000,\r\n" + 
				"    \"startDate\": \"2016-10-01 00:00:00\",\r\n" + 
				"    \"endDate\": \"2016-10-05 00:00:00\",\r\n" + 
				"    \"number\": 2,\r\n" + 
				"    \"status\": \"Active\",\r\n" + 
				"    \"projectId\": 1,\r\n" + 
				"    \"noDays\": 5\r\n" + 
				"}");
		
		get("/sprint/2000/burndown").then()
			.statusCode(HttpURLConnection.HTTP_OK)
			.body("id", is(2000))
			.body("number", is(2))
			.body("sprintDays.dayNo", hasItems(1, 2, 3, 4, 5))
			.body("sprintDays.hoursTotal", hasItems(0, 0, 0, 0, 0))
			.body("sprintDays.sprintDay", hasItems(null, "2016-10-03 00:00:00", "2016-10-04 00:00:00", "2016-10-05 00:00:00", "2016-10-06 00:00:00", "2016-10-07 00:00:00"));		
	}
	
	@Test
	@DatabaseSetup("ITSprintBurndownTest-data.xml")
	public void testNewTaskConsumer() {
		NewSprintTopicProducer.send("{\r\n" + 
				"    \"id\": 2000,\r\n" + 
				"    \"startDate\": \"2016-10-01 00:00:00\",\r\n" + 
				"    \"endDate\": \"2016-10-05 00:00:00\",\r\n" + 
				"    \"number\": 2,\r\n" + 
				"    \"status\": \"Active\",\r\n" + 
				"    \"projectId\": 1,\r\n" + 
				"    \"noDays\": 5\r\n" + 
				"}");
		
		NewStoryTopicProducer.send("{\r\n" + 
				"    \"id\": 1,\r\n" + 
				"    \"title\": \"some test story\",\r\n" + 
				"    \"description\": \"hghghg\",\r\n" + 
				"    \"storypoints\": 5,\r\n" + 
				"    \"sprintId\": 2000,\r\n" + 
				"    \"projectId\": 1,\r\n" + 
				"    \"status\": \"Not Started\"\r\n" + 
				"}");
		
		NewTaskTopicProducer.send("{\r\n" + 
				"    \"id\": 1,\r\n" + 
				"    \"title\": \"this is a task\",\r\n" + 
				"    \"description\": \"testing\",\r\n" + 
				"    \"initialHours\": 13,\r\n" + 
				"    \"remainingHours\": 13,\r\n" + 
				"    \"status\": \"Not Started\",\r\n" + 
				"    \"remainingUpdated\": \"2016-10-01 00:00:00\",\r\n" + 
				"    \"storyId\": 1\r\n" + 
				"}");
		
		NewTaskTopicProducer.send("{\r\n" + 
				"    \"id\": 2,\r\n" + 
				"    \"title\": \"this is a task\",\r\n" + 
				"    \"description\": \"testing\",\r\n" + 
				"    \"initialHours\": 20,\r\n" + 
				"    \"remainingHours\": 20,\r\n" + 
				"    \"status\": \"Not Started\",\r\n" + 
				"    \"remainingUpdated\": \"2016-10-01 00:00:00\",\r\n" + 
				"    \"storyId\": 1\r\n" + 
				"}");
		
		NewTaskTopicProducer.send("{\r\n" + 
				"    \"id\": 3,\r\n" + 
				"    \"title\": \"this is a task\",\r\n" + 
				"    \"description\": \"testing\",\r\n" + 
				"    \"initialHours\": 8,\r\n" + 
				"    \"remainingHours\": 8,\r\n" + 
				"    \"status\": \"Not Started\",\r\n" + 
				"    \"remainingUpdated\": \"2016-10-01 00:00:00\",\r\n" + 
				"    \"storyId\": 1\r\n" + 
				"}");
		
		get("/sprint/2000/burndown").then()
			.statusCode(HttpURLConnection.HTTP_OK)
			.body("id", is(2000))
			.body("number", is(2))
			.body("sprintDays.dayNo", hasItems(1, 2, 3, 4, 5));
		
	}

}
