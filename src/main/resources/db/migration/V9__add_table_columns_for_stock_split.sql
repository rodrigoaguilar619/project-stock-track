CREATE TABLE issues_split (
	id int(11) auto_increment NOT NULL,
	id_issue int(11) NOT NULL,
	is_regular_split bit NOT NULL,
	`date` DATETIME NOT NULL,
	ratio int(11) NOT NULL,
	is_applied bit DEFAULT 0 NOT NULL,
	CONSTRAINT pk_issues_split PRIMARY KEY (id),
	CONSTRAINT fk_issues_split_catalog_issue FOREIGN KEY (id) REFERENCES catalog_issues(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

ALTER TABLE transaction_issue ADD id_issue_split int(11) NULL;
ALTER TABLE transaction_issue ADD CONSTRAINT fk_transaction_issue_issues_split FOREIGN KEY (id_issue_split) REFERENCES issues_split(id);


INSERT INTO issues_split (id_issue,is_regular_split,`date`,ratio,is_applied,id) VALUES (4,1,'2020-08-28',4,1,1);
INSERT INTO issues_split (id_issue,is_regular_split,`date`,ratio,is_applied,id) VALUES (41,1,'2022-06-03',20,1,2);
INSERT INTO issues_split (id_issue,is_regular_split,`date`,ratio,is_applied,id) VALUES (607,1,'2022-06-15',20,1,3);
INSERT INTO issues_split (id_issue,is_regular_split,`date`,ratio,is_applied,id) VALUES (232,0,'2021-08-02',8,1,4);