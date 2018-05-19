package com.estafet.microservices.api.sprint.burndown.container.tests;

public class NewTaskTopicProducer extends TopicProducer {

	public NewTaskTopicProducer() {
		super("new.task.topic");
	}
	
	public static void send(String message) {
		new NewTaskTopicProducer().sendMessage(message);
	}

}
