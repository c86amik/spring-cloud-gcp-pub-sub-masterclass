CREATE TABLE `user` (
	`user_id` INT NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(50) NOT NULL,
	`mobile_no` VARCHAR(10) NOT NULL,
	`profile_photo` VARCHAR(600) NOT NULL,
	`profile_url` VARCHAR(6000) NOT NULL,
	PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;