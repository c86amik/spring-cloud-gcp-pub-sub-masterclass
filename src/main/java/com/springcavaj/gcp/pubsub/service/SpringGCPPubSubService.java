/**
 * 
 */
package com.springcavaj.gcp.pubsub.service;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.util.concurrent.ListenableFuture;

import com.google.cloud.spring.pubsub.PubSubAdmin;
import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.cloud.spring.pubsub.support.AcknowledgeablePubsubMessage;
import com.google.pubsub.v1.Subscription;
import com.google.pubsub.v1.Topic;
import com.springcavaj.gcp.pubsub.excpetion.SpringGCPPubSubException;
import com.springcavaj.gcp.pubsub.model.TopicDetails;
import com.springcavaj.gcp.pubsub.model.TopicMessage;

/**
 * @author c86am
 *
 */
@Service
public class SpringGCPPubSubService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SpringGCPPubSubService.class);
	
	private static final String NOT_APPLICABLE = "NA";
	private static final String SUCCESS_STATUS = "SUCCESSFUL";
	private static final String FAILED_STATUS = "FAILED";
	private static final String TOPICS_DELETED_SUCCESS = "Deleted Successfully";
	
	private final PubSubAdmin pubSubAdmin;
	private final PubSubTemplate pubSubTemplate;
	
	@Autowired
	private SpringGCPPubSubMessagePublisher gcpPubSubMessagePublisher;
	
	@Autowired
	public SpringGCPPubSubService(PubSubAdmin pubSubAdmin, PubSubTemplate pubSubTemplate) {
		this.pubSubAdmin = pubSubAdmin;
		this.pubSubTemplate = pubSubTemplate;
	}
	
	public TopicDetails createTopicAndSubscription(String topicName, String subscriptionName) {
		TopicDetails topicDetails = null;
		Topic topic = this.pubSubAdmin.createTopic(topicName);
		if(null != topic && StringUtils.hasText(topic.getName())) {
			LOGGER.info("SpringGCPPubSubService -> createTopicAndSubscription() -> Topic created in GCP Console : {}", topic.getName());
			Subscription subscription = this.pubSubAdmin.createSubscription(subscriptionName, topicName);
			if(null != subscription && StringUtils.hasText(subscription.getName())) {
				LOGGER.info("SpringGCPPubSubService -> createTopicAndSubscription() -> Subscription created in GCP Console : {}", subscription.getName());
				topicDetails = createTopicDetails(topic.getName(), subscription.getName());
			}
		} else {
			topicDetails = createTopicDetails(NOT_APPLICABLE, NOT_APPLICABLE);
		}
		return topicDetails;
	}
	
	public TopicMessage publishMessageInTopic(String message) {
		TopicMessage topicMessage = null;
		String topicName = this.pubSubAdmin.listTopics().get(0).getName();
		LOGGER.info("SpringGCPPubSubService -> publishMessageInTopic() -> Topic Name from Google Cloud : {}", topicName);
		Boolean isMessagePublished = gcpPubSubMessagePublisher.publishMessage(topicName, message);
		if(Boolean.TRUE.equals(isMessagePublished)) {
			topicMessage = createTopicMessage(topicName, message);
		} else {
			topicMessage = createTopicMessage(NOT_APPLICABLE, NOT_APPLICABLE);
		}
		return topicMessage;
	}
	
	public List<String> pullMessagesFromATopic() {
		String subscriptionName = this.pubSubAdmin.listSubscriptions().get(0).getName();
		LOGGER.info("SpringGCPPubSubService -> pullMessagesFromATopic() -> Subscription Name from Google Cloud : {}", subscriptionName);
		Collection<AcknowledgeablePubsubMessage> messages = this.pubSubTemplate.pull(subscriptionName, 10, true);
		ListenableFuture<Void> ackFuture = this.pubSubTemplate.ack(messages);
		try {
			ackFuture.get();
		} catch (InterruptedException | ExecutionException e) {
			LOGGER.error("SpringGCPPubSubService -> pullMessagesFromATopic() -> Exception is : {}", e.getMessage());
			throw new SpringGCPPubSubException("Not able to Pull Messages from Subscription : " + subscriptionName, e.getMessage());
		}
		return messages.stream().map(m -> m.getPubsubMessage().getData().toString()).collect(Collectors.toList());
	}
	
	public String deleteTopic() {
		String topicName = this.pubSubAdmin.listTopics().get(0).getName();
		LOGGER.info("SpringGCPPubSubService -> deleteTopic() -> Topic to be deleted: {}", topicName);
		this.pubSubAdmin.deleteTopic(topicName);
		return topicName + " " + TOPICS_DELETED_SUCCESS;
	}
	
	public String deleteSubscription() {
		String subscscriptionName = this.pubSubAdmin.listSubscriptions().get(0).getName();
		LOGGER.info("SpringGCPPubSubService -> deleteSubscription() -> Subscription to be deleted: {}", subscscriptionName);
		this.pubSubAdmin.deleteSubscription(subscscriptionName);
		return subscscriptionName + " " + TOPICS_DELETED_SUCCESS;
	}
	
	private TopicMessage createTopicMessage(String topicName, String message) {
		TopicMessage topicMessage = new TopicMessage();
		topicMessage.setTopicName(topicName);
		topicMessage.setMessage(message);
		return topicMessage;
	}
	
	private TopicDetails createTopicDetails(String topicName, String subscriptionName) {
		TopicDetails details = new TopicDetails();
		details.setTopicName(topicName);
		details.setSubscriptionName(subscriptionName);
		details.setStatus((NOT_APPLICABLE.equalsIgnoreCase(topicName) && (NOT_APPLICABLE.equalsIgnoreCase(subscriptionName))) ? FAILED_STATUS : SUCCESS_STATUS);
		return details;
	}
	 
}
