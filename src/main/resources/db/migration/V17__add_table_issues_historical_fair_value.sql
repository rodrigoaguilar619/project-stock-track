CREATE TABLE `issues_historical_fair_value` (
  `id_issue` int(11) NOT NULL,
  `id_date` date NOT NULL,
  `fair_value` decimal(15,4) DEFAULT NULL,
  PRIMARY KEY (`id_date`,`id_issue`),
  KEY `fk_issues_historial_fair_value_idx` (`id_issue`),
  CONSTRAINT `fk_issues_historical_fair_value_issue` FOREIGN KEY (`id_issue`) REFERENCES `catalog_issues` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;