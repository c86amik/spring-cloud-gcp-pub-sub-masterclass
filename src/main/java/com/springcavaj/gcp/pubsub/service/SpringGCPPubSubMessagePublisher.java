/**
 * 
 */
package com.springcavaj.gcp.pubsub.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import com.google.cloud.spring.pubsub.core.PubSubTemplate;

/**
 * @author c86am
 *
 */
@Component
public class SpringGCPPubSubMessagePublisher {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SpringGCPPubSubMessagePublisher.class);
	
	private final PubSubTemplate pubSubTemplate;
	
	@Autowired
	public SpringGCPPubSubMessagePublisher(PubSubTemplate pubSubTemplate) {
		this.pubSubTemplate = pubSubTemplate;
	}
	
	public Boolean publishMessage(String topicName, String message) {
		LOGGER.info("SpringGCPPubSubMessagePublisher -> publishMessage() -> Message : {} before gets published in Topic : {}", message, topicName);
		ListenableFuture<String> future = this.pubSubTemplate.publish(topicName, message);
		if(null != future) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

}
