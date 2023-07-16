/**
 * 
 */
package com.springcavaj.gcp.sqst.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.springcavaj.gcp.sqst.model.FileDto;
import com.springcavaj.gcp.sqst.model.UserSqlStorage;
import com.springcavaj.gcp.sqst.service.SpringGCPSqlStorageService;


/**
 * @author c86am
 *
 */

@RestController
public class SpringGCPSqlStorageController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SpringGCPSqlStorageController.class);
	
	@Autowired
	private SpringGCPSqlStorageService gcpSqlStorageService;
	
	@PostMapping(value = "/uploadFile")
	public FileDto uploadToGcp(@RequestParam("file") MultipartFile file, @RequestParam("name") String name, @RequestParam("fileName") String fileName) {
		FileDto dto = null;
		try {
			dto = gcpSqlStorageService.uploadFileInGCP(file, fileName, file.getContentType());
			LOGGER.info("SpringGCPSqlStorageController -> uploadToGcp() -> FileName : {} and FileMedia Url : {}", dto.getFileName(), dto.getFileUrl());
		}
		catch (Exception e) {
			LOGGER.error("SpringGCPSqlStorageController -> uploadToGcp() -> An error occurred : {}", e.getMessage());
		}
		return dto;
	}
	
	@GetMapping("/allUsers")
    public List<UserSqlStorage> getAllUserData() {
		LOGGER.info("SpringGCPSqlStorageController -> getAllUserData() -> All Data are fetched");
		return gcpSqlStorageService.getAllUsers();
    }
	
	@PostMapping("/saveUser")
    public UserSqlStorage saveUserData(@RequestBody UserSqlStorage user) {
        LOGGER.info("SpringGCPSqlStorageController -> saveUser() -> New Record of User saved");
        return gcpSqlStorageService.saveUser(user);
    }
	
	@PutMapping("/updateUser/{id}")
    public UserSqlStorage updateUserData(@PathVariable(value = "id") String id, @RequestBody UserSqlStorage user) {
        LOGGER.info("SpringGCPSqlStorageController -> updateUser() -> Update the existing User Record");
        return gcpSqlStorageService.updateUser(id, user);
    }

	@GetMapping("/getUser/{id}")
    public UserSqlStorage getUserDataById(@PathVariable(value = "id") String id) {
    	LOGGER.info("SpringGCPSqlStorageController -> getUserDataById() -> Fetch the User Detail by userId as : {}", id);
        return gcpSqlStorageService.getUserById(id);
    }
	
	@DeleteMapping("/deleteUser/{id}")
    public void deleteUser(@PathVariable(value = "id") String id) {
    	LOGGER.info("SpringGCPSqlStorageController -> deleteUser() -> Delete the User of userId : {}", id);
    	gcpSqlStorageService.deleteUserById(id);
    }

}
