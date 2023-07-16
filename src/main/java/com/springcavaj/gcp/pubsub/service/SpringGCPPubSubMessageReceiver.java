/**
 * 
 */
package com.springcavaj.gcp.pubsub.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.cloud.pubsub.v1.AckReplyConsumer;
import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.pubsub.v1.PubsubMessage;


/**
 * @author c86am
 *
 */
@Component
public class SpringGCPPubSubMessageReceiver implements MessageReceiver {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SpringGCPPubSubMessageReceiver.class);

	@Override
	public void receiveMessage(PubsubMessage message, AckReplyConsumer consumer) {
		String payLoad = message.getData().toString();
		LOGGER.info("SpringGCPPubSubMessageReceiver -> receiveMessage() -> Message Payload : {}", payLoad);
		consumer.ack();
	}


}
