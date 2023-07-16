/**
 * 
 */
package com.springcavaj.gcp.sqst.service;

import java.io.File;
import java.io.FileOutputStream;
import java.rmi.ServerException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.springcavaj.gcp.sqst.excpetion.SpringGCPSqlStorageException;
import com.springcavaj.gcp.sqst.model.FileDto;
import com.springcavaj.gcp.sqst.model.UserSqlStorage;
import com.springcavaj.gcp.sqst.repository.SpringGCPSqlStorageRepository;



/**
 * @author c86am
 *
 */

@Service
public class SpringGCPSqlStorageService {
	
	@Value("${spring.cloud.gcp.project-id}")
	private String gcpProjectId;
	
	//@Value("${spring.cloud.gcp.bucket}")
	@Value("${spring.cloud.gcp.storage.bucket-name}")
	private String gcpBucketId;

	@Value("${spring.cloud.gcp.bucket.dir}")
	private String gcpBucketDirectoryName;
	
	@Autowired
	private SpringGCPSqlStorageRepository gcpSqlStorageRepository;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SpringGCPSqlStorageService.class);
	
	private final static String EMPTY_STRING = "";
	
	public FileDto uploadFileInGCP(MultipartFile file, String fileName, String contentType) throws ServerException {
		try {
			LOGGER.info("SpringGCPSqlStorageService -> uploadFileInGCP() method invoked");
			byte[] fileData = FileUtils.readFileToByteArray(convertFileToByteArray(file));
			StorageOptions options = StorageOptions.newBuilder().setProjectId(gcpProjectId).build();
			Storage storage = options.getService();
			Bucket bucket = storage.get(gcpBucketId, Storage.BucketGetOption.fields());
			Blob blob = bucket.create(gcpBucketDirectoryName + "/" + fileName, fileData, contentType);
			if(null != blob) {
				LOGGER.info("SpringGCPSqlStorageService -> uploadFileInGCP() -> File Successfully uyploaded in GCP Cloud Storage.");
				LOGGER.info("SpringGCPSqlStorageService -> uploadFileInGCP() -> Media File Name : {}, Media Link URL : {} and fileName : {}", 
						blob.getName(), blob.getMediaLink(), fileName);
				FileDto dto = new FileDto();
				dto.setFileName(blob.getName());
				dto.setFileUrl(blob.getMediaLink());
				return dto;
			}
		} catch (Exception e) {
			LOGGER.error("SpringGCPSqlStorageService -> uploadFileInGCP() -> Exception occurred : {}", e);
		}
		throw new ServerException("An exception occurred while storing media file in GCP Clod Storage");
	}
	
	public List<UserSqlStorage> getAllUsers() {
		return gcpSqlStorageRepository.findAll();
	}
	
	public UserSqlStorage saveUser(UserSqlStorage user) {
		validateProfilePhotoAndUrl(user);
		return gcpSqlStorageRepository.save(user);
	}

	private void validateProfilePhotoAndUrl(UserSqlStorage user) {
		String profilePhotoName = StringUtils.hasText(user.getProfilePhoto()) ? user.getProfilePhoto() : EMPTY_STRING;
		String profilPhotoURL = (StringUtils.hasText(profilePhotoName) && StringUtils.hasText(user.getProfileUrl())) ? user.getProfileUrl() : EMPTY_STRING;
		if(StringUtils.hasText(profilePhotoName) && StringUtils.hasText(profilPhotoURL)) {
			StorageOptions options = StorageOptions.newBuilder().setProjectId(gcpProjectId).build();
			Storage storage = options.getService();
			Blob blob = storage.get(gcpBucketId, user.getProfilePhoto());
			if(null != blob) {
				user.setProfilePhoto(blob.getName());
				user.setProfileUrl(blob.getMediaLink());
			} else {
				user.setProfilePhoto(EMPTY_STRING);
				user.setProfileUrl(EMPTY_STRING);
			}
		} else {
			user.setProfilePhoto(profilePhotoName);
			user.setProfileUrl(profilPhotoURL);
		}
	}
	
	public UserSqlStorage updateUser(String id, UserSqlStorage user) {
		Integer userId = Integer.parseInt(id);
		LOGGER.info("SpringGCPSqlStorageService -> updateUser() -> User Id is : {}", userId);
        UserSqlStorage model = gcpSqlStorageRepository.findById(userId)
        		.orElseThrow(() -> new SpringGCPSqlStorageException("User Record not found", id));
        if (null != model && model.getUserId() == user.getUserId()) {
        	validateProfilePhotoAndUrl(user);
        	return gcpSqlStorageRepository.save(user);
		}
        return model;
	}
	
	public UserSqlStorage getUserById(String id) {
		Integer userId = Integer.parseInt(id);
    	UserSqlStorage user = gcpSqlStorageRepository.findById(userId)
    			.orElseThrow(() -> new SpringGCPSqlStorageException("User Record not found", id));
    	LOGGER.info("SpringGCPSqlStorageService -> getUserById() -> Fetch the User Detail by userId as : {}", user);
        return user;
	}
	
	public void deleteUserById(String id) {
		Integer userId = Integer.parseInt(id);
		UserSqlStorage user = gcpSqlStorageRepository.findById(userId)
				.orElseThrow(() -> new SpringGCPSqlStorageException("User not found", id));
		String profilePhotoName = user.getProfilePhoto();
		StorageOptions options = StorageOptions.newBuilder().setProjectId(gcpProjectId).build();
		Storage storage = options.getService();
		Blob blob = storage.get(gcpBucketId, profilePhotoName);
		if(null != blob) {
			boolean deleteProfilePhoto = blob.delete();
			if(deleteProfilePhoto) {
				LOGGER.info("SpringGCPSqlStorageController -> deleteUser() -> ProfilePhoto : {} deleted for User : {}", profilePhotoName, user.getUserId());
			} else {
				LOGGER.info("SpringGCPSqlStorageController -> deleteUser() -> ProfilePhoto : {} not deleted for User : {}", profilePhotoName, user.getUserId());
			}
		}
		gcpSqlStorageRepository.delete(user);
		LOGGER.info("SpringGCPSqlStorageController -> deleteUser() -> Delete the same profilePhoto and profileURL for other users");
		List<UserSqlStorage> otherUsers = gcpSqlStorageRepository.findByProfilePhoto(profilePhotoName);
		otherUsers.stream().forEach(otherUser -> {
			otherUser.setProfilePhoto(EMPTY_STRING);
			otherUser.setProfileUrl(EMPTY_STRING);
			gcpSqlStorageRepository.save(otherUser);
			LOGGER.info("SpringGCPSqlStorageController -> deleteUser() -> User having same profilePhoto and URL updated : {}", otherUser);
		});
    	LOGGER.info("SpringGCPSqlStorageController -> deleteUser() -> Delete the User of userId : {}", user);
	}
	
	private File convertFileToByteArray(MultipartFile file) throws ServerException {
		try {
			if(!StringUtils.hasText(file.getOriginalFilename())) {
				throw new ServerException("Original File name is null");
			}
			File convertedFile = new File(file.getOriginalFilename());
			FileOutputStream fileOutputStream = new FileOutputStream(convertedFile);
			fileOutputStream.write(file.getBytes());
			fileOutputStream.close();
			LOGGER.info("SpringGCPSqlStorageService -> convertFileToByteArray() -> Converted File : {}", convertedFile);
			return convertedFile;
		} catch (Exception e) {
			throw new ServerException("An error occurred while converting the file to byte array");
		}
	}

}
