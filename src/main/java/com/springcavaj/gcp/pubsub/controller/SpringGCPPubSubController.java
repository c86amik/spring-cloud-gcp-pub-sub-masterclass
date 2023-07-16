/**
 * 
 */
package com.springcavaj.gcp.pubsub.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springcavaj.gcp.pubsub.model.TopicDetails;
import com.springcavaj.gcp.pubsub.model.TopicMessage;
import com.springcavaj.gcp.pubsub.service.SpringGCPPubSubService;


/**
 * @author c86am
 *
 */

@RestController
public class SpringGCPPubSubController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SpringGCPPubSubController.class);
	
	@Autowired
	private SpringGCPPubSubService gcpPubSubService;
	
	
	@GetMapping(value = "/createTopic/{topicName}/{subscriptionName}")
	public TopicDetails createTopicAndSubscription(@PathVariable(value = "topicName") String topicName, @PathVariable(value = "subscriptionName") String subscriptionName) {
		LOGGER.info("SpringGCPPubSubController -> createTopicAndSubscription() -> Topic Name : {}, Subscription Name : {}", topicName, subscriptionName);
		return gcpPubSubService.createTopicAndSubscription(topicName, subscriptionName);
	}
	
	@PostMapping(value = "/publishMessage")
	public TopicMessage publishMessageInTopic(@RequestBody String message) {
		LOGGER.info("SpringGCPPubSubController -> publishMessageInTopic() -> Message to be published in Topic : {}", message);
		return gcpPubSubService.publishMessageInTopic(message);
	}
	
}
