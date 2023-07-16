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
public class TopicDetails implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6779939817971126651L;
	
	private String topicName;
	private String subscriptionName;
	private String status;
	
	public String getTopicName() {
		return topicName;
	}
	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}
	public String getSubscriptionName() {
		return subscriptionName;
	}
	public void setSubscriptionName(String subscriptionName) {
		this.subscriptionName = subscriptionName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public int hashCode() {
		return Objects.hash(status, subscriptionName, topicName);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof TopicDetails))
			return false;
		TopicDetails other = (TopicDetails) obj;
		return Objects.equals(status, other.status) && Objects.equals(subscriptionName, other.subscriptionName)
				&& Objects.equals(topicName, other.topicName);
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TopicDetails [topicName=");
		builder.append(topicName);
		builder.append(", subscriptionName=");
		builder.append(subscriptionName);
		builder.append(", status=");
		builder.append(status);
		builder.append("]");
		return builder.toString();
	}
}
