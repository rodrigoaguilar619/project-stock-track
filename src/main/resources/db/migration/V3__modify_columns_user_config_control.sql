ALTER TABLE config_control CHANGE `value` config_value varchar(250) DEFAULT NULL NULL;
ALTER TABLE `user` RENAME TO `user_data`;