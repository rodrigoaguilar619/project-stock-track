CREATE TABLE `issues_historical_earning` (
  `id_issue` int(11) NOT NULL,
  `id_date` date NOT NULL,
  `earning_estimate` decimal(15,4) DEFAULT NULL,
  `earning_real` decimal(15,4) DEFAULT NULL,
  PRIMARY KEY (`id_date`,`id_issue`),
  KEY `fk_issues_historial_earning_idx` (`id_issue`),
  CONSTRAINT `fk_issues_historical_earning_issue` FOREIGN KEY (`id_issue`) REFERENCES `catalog_issues` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;