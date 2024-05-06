package project.stock.track.app.beans.entity;

import java.io.Serializable;

import jakarta.persistence.Entity;

import lib.base.backend.entity.generic.GenericCatalogIntEntity;

@Entity(name = "catalog_sector")
public class CatalogSectorEntity extends GenericCatalogIntEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public CatalogSectorEntity() {
	}
	
	public CatalogSectorEntity(int id) {
		super(id);
	}
}
