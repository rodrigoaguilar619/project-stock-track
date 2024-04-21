package project.stock.track.app.beans.entity;

import java.io.Serializable;

import javax.persistence.Entity;

import lib.base.backend.entity.generic.GenericCatalogIntEntity;

@Entity(name = "catalog_type_currency")
public class CatalogTypeCurrencyEntity extends GenericCatalogIntEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public CatalogTypeCurrencyEntity() {
	}
	
	public CatalogTypeCurrencyEntity(int id) {
		super(id);
	}
}
