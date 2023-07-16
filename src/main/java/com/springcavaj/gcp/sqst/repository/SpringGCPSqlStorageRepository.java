package com.springcavaj.gcp.sqst.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springcavaj.gcp.sqst.model.UserSqlStorage;

public interface SpringGCPSqlStorageRepository extends JpaRepository<UserSqlStorage, Integer> {
	List<UserSqlStorage> findByProfilePhoto(String profilePhoto);
}
