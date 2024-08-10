package project.stock.track.app.beans.entity;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lib.base.backend.entity.generic.GenericStatusEntity;

@Entity
@Table(name = "catalog_status_issue_movement")
public class CatalogStatusIssueMovementEntity extends GenericStatusEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
}
