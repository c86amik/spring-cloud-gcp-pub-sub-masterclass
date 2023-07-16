/**
 * 
 */
package com.springcavaj.gcp.pubsub.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author c86am
 *
 */
public class TopicMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5628925255437191078L;
	
	private String topicName;
	private String message;
	
	public String getTopicName() {
		return topicName;
	}
	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(message, topicName);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof TopicMessage))
			return false;
		TopicMessage other = (TopicMessage) obj;
		return Objects.equals(message, other.message) && Objects.equals(topicName, other.topicName);
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TopicMessage [topicName=");
		builder.append(topicName);
		builder.append(", message=");
		builder.append(message);
		builder.append("]");
		return builder.toString();
	}
}
