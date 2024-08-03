--
-- Table structure for table `catalog_type_currency`
--

CREATE TABLE `catalog_type_currency` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `is_active` tinyint(4) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

--
-- Table structure for table `catalog_broker`
--

CREATE TABLE `catalog_broker` (
  `id` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `id_type_currency` int(11) DEFAULT NULL,
  `acronym` varchar(15) DEFAULT NULL,
  `is_active` tinyint(4) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_catalog_broker_type_currency` (`id_type_currency`),
  CONSTRAINT `fk_catalog_broker_type_currency` FOREIGN KEY (`id_type_currency`) REFERENCES `catalog_type_currency` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


--
-- Table structure for table `catalog_sector`
--

CREATE TABLE `catalog_sector` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `is_active` tinyint(4) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4;


--
-- Table structure for table `catalog_status_issue`
--

CREATE TABLE `catalog_status_issue` (
  `reference` varchar(45) NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(100) DEFAULT NULL,
  `is_active` tinyint(4) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;


--
-- Table structure for table `catalog_type_stock`
--

CREATE TABLE `catalog_type_stock` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `is_active` tinyint(4) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;


--
-- Table structure for table `catalog_status_issue_movement`
--

CREATE TABLE `catalog_status_issue_movement` (
  `reference` varchar(45) NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(100) DEFAULT NULL,
  `is_active` tinyint(4) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;


--
-- Table structure for table `catalog_type_movement`
--

CREATE TABLE `catalog_type_movement` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `is_active` tinyint(4) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;


--
-- Table structure for table `config_control`
--

CREATE TABLE `config_control` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `reference` varchar(250) DEFAULT NULL,
  `value` varchar(250) DEFAULT NULL,
  `description` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4;


--
-- Table structure for table `catalog_issues`
--

CREATE TABLE `catalog_issues` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `initials` varchar(8) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `historical_start_date` datetime DEFAULT NULL,
  `id_sector` int(11) DEFAULT NULL,
  `id_type_stock` int(11) NOT NULL,
  `is_sp_500` tinyint(4) DEFAULT NULL,
  `id_status_issue` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `initials_UNIQUE` (`initials`),
  KEY `fk_issues_manager_catalog_sector_idx` (`id_sector`),
  KEY `fk_issues_manager_catalog_status_issue_idx` (`id_status_issue`),
  KEY `fk_issues_manager_catalog_type_stock` (`id_type_stock`),
  CONSTRAINT `fk_catalog_issues_catalog_sector` FOREIGN KEY (`id_sector`) REFERENCES `catalog_sector` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_catalog_issues_status_issue` FOREIGN KEY (`id_status_issue`) REFERENCES `catalog_status_issue` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_catalog_issues_type_stock` FOREIGN KEY (`id_type_stock`) REFERENCES `catalog_type_stock` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=608 DEFAULT CHARSET=utf8mb4;


--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `password` varchar(90) NOT NULL,
  `user_name` varchar(45) NOT NULL,
  `is_active` tinyint(4) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


--
-- Table structure for table `config_auth`
--

CREATE TABLE `config_auth` (
  `id_user` int(11) NOT NULL,
  `token` varchar(255) NOT NULL,
  `date_login` datetime NOT NULL,
  `date_refresh` datetime NOT NULL,
  PRIMARY KEY (`id_user`),
  CONSTRAINT `fk_config_auth_id_user` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


--
-- Table structure for table `dollar_historical_price`
--

CREATE TABLE `dollar_historical_price` (
  `id_date` datetime NOT NULL,
  `price` double DEFAULT NULL,
  PRIMARY KEY (`id_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


--
-- Table structure for table `issues_historical`
--

CREATE TABLE `issues_historical` (
  `id_issue` int(11) NOT NULL,
  `id_date` datetime NOT NULL,
  `open` double DEFAULT NULL,
  `close` double DEFAULT NULL,
  `high` double DEFAULT NULL,
  `low` double DEFAULT NULL,
  `volume` double DEFAULT NULL,
  PRIMARY KEY (`id_date`,`id_issue`),
  KEY `fk_issues_historial_issue` (`id_issue`),
  CONSTRAINT `fk_issue_historial_issue` FOREIGN KEY (`id_issue`) REFERENCES `catalog_issues` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


--
-- Table structure for table `issues_last_price_tmp`
--

CREATE TABLE `issues_last_price_tmp` (
  `id_issue` int(11) NOT NULL,
  `last` double DEFAULT NULL,
  `open` double DEFAULT NULL,
  `volume` int(11) DEFAULT NULL,
  `prev_close` double DEFAULT NULL,
  `high` double DEFAULT NULL,
  `timestamp` datetime DEFAULT NULL,
  `last_sale_timestamp` datetime DEFAULT NULL,
  PRIMARY KEY (`id_issue`),
  CONSTRAINT `fk_issues_last_price_tmp_id_issue` FOREIGN KEY (`id_issue`) REFERENCES `catalog_issues` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


--
-- Table structure for table `issues_manager`
--

CREATE TABLE `issues_manager` (
  `id_issue` int(11) NOT NULL,
  `id_user` int(11) NOT NULL,
  `id_status_issue_quick` int(11) DEFAULT NULL,
  `id_status_issue_trading` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_issue`,`id_user`),
  KEY `fk_issues_manager_status_issue_trading_idx` (`id_status_issue_trading`),
  KEY `fk_issues_manager_status_issue_quick_idx` (`id_status_issue_quick`),
  CONSTRAINT `fk_issues_manager_catalog_issues` FOREIGN KEY (`id_issue`) REFERENCES `catalog_issues` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_issues_manager_status_issue_quick` FOREIGN KEY (`id_status_issue_quick`) REFERENCES `catalog_status_issue` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_issues_manager_status_issue_trading` FOREIGN KEY (`id_status_issue_trading`) REFERENCES `catalog_status_issue` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


--
-- Table structure for table `issues_manager_track_properties`
--

CREATE TABLE `issues_manager_track_properties` (
  `id_issue` int(11) NOT NULL,
  `id_user` int(11) NOT NULL,
  `track_buy_price` double DEFAULT NULL,
  `track_sell_price` double DEFAULT NULL,
  `track_fair_value` double DEFAULT NULL,
  `is_invest` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id_issue`,`id_user`),
  CONSTRAINT `fk_issues_manager_track_properties` FOREIGN KEY (`id_issue`, `id_user`) REFERENCES `issues_manager` (`id_issue`, `id_user`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


--
-- Table structure for table `issues_movements`
--

CREATE TABLE `issues_movements` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_issue` int(11) NOT NULL,
  `id_user` int(11) NOT NULL,
  `id_status` int(11) NOT NULL,
  `id_broker` int(11) DEFAULT NULL,
  `price_movement` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_issues_movements_id_broker_idx` (`id_broker`),
  KEY `fk_issues_movements_id_issue_idx` (`id_issue`),
  KEY `fk_issues_movements_id_status_idx` (`id_status`),
  KEY `FK_issues_movements_issues_manager` (`id_issue`,`id_user`),
  CONSTRAINT `FK_issues_movements_id_broker` FOREIGN KEY (`id_broker`) REFERENCES `catalog_broker` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_issues_movements_id_catalog_status` FOREIGN KEY (`id_status`) REFERENCES `catalog_status_issue_movement` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_issues_movements_issues_manager` FOREIGN KEY (`id_issue`, `id_user`) REFERENCES `issues_manager` (`id_issue`, `id_user`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8mb4;


--
-- Table structure for table `issues_movements_buy`
--

CREATE TABLE `issues_movements_buy` (
  `buy_transaction_number` int(11) NOT NULL,
  `id_issue_movement` int(11) NOT NULL,
  `buy_price` double DEFAULT NULL,
  `buy_date` datetime DEFAULT NULL,
  `sell_price` double DEFAULT NULL,
  `sell_date` datetime DEFAULT NULL,
  `total_shares` double DEFAULT NULL,
  PRIMARY KEY (`buy_transaction_number`,`id_issue_movement`),
  KEY `FK_issues_movements_buy_id_movement_issue_idx` (`id_issue_movement`),
  CONSTRAINT `FK_issues_movements_buy_id_issue_movement` FOREIGN KEY (`id_issue_movement`) REFERENCES `issues_movements` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


--
-- Table structure for table `transaction_issue`
--

CREATE TABLE `transaction_issue` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_user` int(11) NOT NULL,
  `id_issue` int(11) NOT NULL,
  `id_date` datetime DEFAULT NULL,
  `price_buy` double DEFAULT NULL,
  `commision_percentage` double DEFAULT NULL,
  `price_total_buy` double DEFAULT NULL,
  `price_sell` double DEFAULT NULL,
  `sell_commision_percentage` double DEFAULT NULL,
  `sell_taxes_percentage` double DEFAULT NULL,
  `price_total_sell` double DEFAULT NULL,
  `sell_gain_loss_percentage` double DEFAULT NULL,
  `sell_gain_loss_total` double DEFAULT NULL,
  `sell_date` datetime DEFAULT NULL,
  `id_broker` int(11) DEFAULT NULL,
  `total_shares` double DEFAULT NULL,
  `is_slice` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_transaction_issue_id_broker_idx` (`id_broker`),
  KEY `fk_transaction_issue_issue_manager` (`id_issue`,`id_user`),
  CONSTRAINT `fk_transaction_issue_id_broker` FOREIGN KEY (`id_broker`) REFERENCES `catalog_broker` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_transaction_issue_issue_manager` FOREIGN KEY (`id_issue`, `id_user`) REFERENCES `issues_manager` (`id_issue`, `id_user`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=7088 DEFAULT CHARSET=utf8mb4;


--
-- Table structure for table `transaction_money`
--

CREATE TABLE `transaction_money` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_user` int(11) NOT NULL,
  `id_type_currency` int(11) NOT NULL,
  `id_broker` int(11) NOT NULL,
  `amount` float NOT NULL,
  `value_mxn` float NOT NULL,
  `id_type_movement` int(11) NOT NULL,
  `date_transaction` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_transaction_money_catalog_broker_idx` (`id_broker`),
  KEY `fk_transaction_money_type_currency` (`id_type_currency`),
  KEY `fk_transaction_money_type_movement` (`id_type_movement`),
  KEY `fk_transaction_money_id_user` (`id_user`),
  CONSTRAINT `fk_transaction_money_catalog_broker` FOREIGN KEY (`id_broker`) REFERENCES `catalog_broker` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_transaction_money_id_user` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_transaction_money_type_currency` FOREIGN KEY (`id_type_currency`) REFERENCES `catalog_type_currency` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_transaction_money_type_movement` FOREIGN KEY (`id_type_movement`) REFERENCES `catalog_type_movement` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8mb4;