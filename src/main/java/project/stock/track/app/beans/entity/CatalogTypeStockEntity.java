package project.stock.track.app.beans.entity;

import java.io.Serializable;

import javax.persistence.Entity;

import lib.base.backend.entity.generic.GenericCatalogIntEntity;

@Entity(name = "catalog_type_stock")
public class CatalogTypeStockEntity extends GenericCatalogIntEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public CatalogTypeStockEntity() {
	}
	
	public CatalogTypeStockEntity(int id) {
		super(id);
	}
}
