CREATE TABLE `catalog_index` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `is_active` bit NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `catalog_index` VALUES (1,'s&p500',1),(2,'nasdaq',1),(3,'other',1);

ALTER TABLE catalog_issues ADD id_index INT(11) NULL;

UPDATE catalog_issues SET id_index=1 WHERE catalog_issues.is_sp_500=1 AND id_index IS NULL;
UPDATE catalog_issues SET id_index=3 WHERE id_index IS NULL;

ALTER TABLE catalog_issues DROP COLUMN is_sp_500;