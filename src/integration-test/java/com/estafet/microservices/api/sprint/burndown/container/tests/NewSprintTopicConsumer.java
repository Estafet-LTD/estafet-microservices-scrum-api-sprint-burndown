package com.estafet.microservices.api.sprint.burndown.container.tests;

import com.estafet.microservices.api.sprint.burndown.model.Sprint;

public class NewSprintTopicConsumer extends TopicConsumer {

	public NewSprintTopicConsumer() {
		super("new.sprint.topic");
	}

	public Sprint consume() {
		return super.consume(Sprint.class);
	}

}
