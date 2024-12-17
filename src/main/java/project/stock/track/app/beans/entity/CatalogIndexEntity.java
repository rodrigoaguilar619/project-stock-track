package project.stock.track.app.beans.entity;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lib.base.backend.entity.generic.GenericCatalogIntEntity;

@Entity
@Table(name = "catalog_index")
public class CatalogIndexEntity extends GenericCatalogIntEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public CatalogIndexEntity() {
	}
	
	public CatalogIndexEntity(int id) {
		super(id);
	}
}
