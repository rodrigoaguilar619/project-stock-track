package project.stock.track.app.beans.entity;

import java.io.Serializable;

import javax.persistence.Entity;

import lib.base.backend.entity.generic.GenericCatalogIntEntity;

@Entity(name = "catalog_type_movement")
public class CatalogTypeMovementEntity extends GenericCatalogIntEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public CatalogTypeMovementEntity() {
	}
	
	public CatalogTypeMovementEntity(int id) {
		super(id);
	}
}
