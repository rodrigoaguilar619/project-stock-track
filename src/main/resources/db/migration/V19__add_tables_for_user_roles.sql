CREATE TABLE `catalog_rol` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `is_active` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `user_rol` (
  `id_user` int(11) NOT NULL,
  `id_rol` int(11) NOT NULL,
  PRIMARY KEY (`id_user`, `id_rol`),
  CONSTRAINT `fk_user_rol_user_data` FOREIGN KEY (`id_user`) REFERENCES `user_data` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_rol_catalog_rol` FOREIGN KEY (`id_rol`) REFERENCES `catalog_rol` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `catalog_rol` VALUES (1,'Admin',1),(2,'Demo',1);
INSERT INTO `user_rol` VALUES (1,1);