/**
 * 
 */
package com.springcavaj.gcp.sqst.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * @author c86am
 *
 */
@Entity
@Table(name="user")
@EntityListeners(AuditingEntityListener.class)
public class UserSqlStorage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8541080164244560842L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id", nullable = false)
	private Integer userId;
	@Column(name = "name", nullable = false)
	private String name;
	@Column(name = "mobile_no", nullable = false)
	private String mobileNo;
	@Column(name = "profile_photo", nullable = false)
	private String profilePhoto;
	@Column(name = "profile_url", nullable = false)
	private String profileUrl;
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getProfilePhoto() {
		return profilePhoto;
	}
	public void setProfilePhoto(String profilePhoto) {
		this.profilePhoto = profilePhoto;
	}
	public String getProfileUrl() {
		return profileUrl;
	}
	public void setProfileUrl(String profileUrl) {
		this.profileUrl = profileUrl;
	}
	@Override
	public int hashCode() {
		return Objects.hash(mobileNo, name, profilePhoto, profileUrl, userId);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof UserSqlStorage))
			return false;
		UserSqlStorage other = (UserSqlStorage) obj;
		return Objects.equals(mobileNo, other.mobileNo) && Objects.equals(name, other.name)
				&& Objects.equals(profilePhoto, other.profilePhoto) && Objects.equals(profileUrl, other.profileUrl) && Objects.equals(userId, other.userId);
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserSqlStorage [userId=");
		builder.append(userId);
		builder.append(", name=");
		builder.append(name);
		builder.append(", mobileNo=");
		builder.append(mobileNo);
		builder.append(", profilePhoto=");
		builder.append(profilePhoto);
		builder.append(", profileUrl=");
		builder.append(profileUrl);
		builder.append("]");
		return builder.toString();
	}
	
}
