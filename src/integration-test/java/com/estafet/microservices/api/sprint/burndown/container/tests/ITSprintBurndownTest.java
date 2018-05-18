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
			.body("noDays", is(5));
	}

	@Test
	@DatabaseSetup("ITSprintBurndownTest-data.xml")
	public void testGetSprintBurndown() {
		get("/sprint/1000").then()
			.statusCode(HttpURLConnection.HTTP_OK)
			.body("id", is(1))
			.body("number", is(1))
			.body("status", is("Not Started"));
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
