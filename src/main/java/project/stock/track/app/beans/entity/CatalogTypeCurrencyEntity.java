package project.stock.track.app.beans.entity;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lib.base.backend.entity.generic.GenericCatalogIntEntity;

@Entity
@Table(name = "catalog_type_currency")
public class CatalogTypeCurrencyEntity extends GenericCatalogIntEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public CatalogTypeCurrencyEntity() {
	}
	
	public CatalogTypeCurrencyEntity(int id) {
		super(id);
	}
}
