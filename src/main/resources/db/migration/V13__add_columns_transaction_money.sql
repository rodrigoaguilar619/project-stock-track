ALTER TABLE transaction_money ADD id_issue int(11) NULL;
ALTER TABLE transaction_money ADD information varchar(500) NULL;
ALTER TABLE transaction_money CHANGE value_mxn amount_mxn decimal(15,4) NOT NULL;
ALTER TABLE transaction_money ADD CONSTRAINT fk_transaction_money_catalog_issues FOREIGN KEY (id_issue) REFERENCES catalog_issues(id);

INSERT INTO catalog_type_movement (id,description,is_active) VALUES (3,'dividend',1);
INSERT INTO catalog_type_movement (id,description,is_active) VALUES (4,'tax isr',1);
INSERT INTO catalog_type_movement (id,description,is_active) VALUES (5,'cash in lieu',1);
INSERT INTO catalog_type_movement (id,description,is_active) VALUES (6,'issue fee',1);
INSERT INTO catalog_type_movement (id,description,is_active) VALUES (7,'bank interest',1);
INSERT INTO catalog_type_movement (id,description,is_active) VALUES (8,'bank fee',1);