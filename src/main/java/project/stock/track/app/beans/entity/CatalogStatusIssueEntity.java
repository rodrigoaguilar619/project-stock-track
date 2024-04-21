package project.stock.track.app.beans.entity;

import java.io.Serializable;

import javax.persistence.Entity;

import lib.base.backend.entity.generic.GenericStatusEntity;

@Entity(name = "catalog_status_issue")
public class CatalogStatusIssueEntity extends GenericStatusEntity implements Serializable {

	private static final long serialVersionUID = 1L;
}
