ALTER TABLE config_control CHANGE value config_value varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL NULL;
RENAME TABLE `user` TO user_data;