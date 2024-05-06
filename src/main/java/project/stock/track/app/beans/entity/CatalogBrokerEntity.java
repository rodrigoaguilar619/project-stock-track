package project.stock.track.app.beans.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import lib.base.backend.entity.generic.GenericCatalogIntEntity;

@Entity(name = "catalog_broker")
public class CatalogBrokerEntity extends GenericCatalogIntEntity implements Serializable {	

	private static final long serialVersionUID = 1L;

	@Column(name = "id_type_currency")
	private Integer idTypeCurrency;
	
	@Column(name="acronym")
	private String acronym;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_type_currency", insertable = false, updatable = false)
	private CatalogTypeCurrencyEntity catalogTypeCurrencyEntity;
	
	public CatalogBrokerEntity() {
	}
	
	public CatalogBrokerEntity(int id) {
		super(id);
	}

	public Integer getIdTypeCurrency() {
		return idTypeCurrency;
	}

	public void setIdTypeCurrency(Integer idTypeCurrency) {
		this.idTypeCurrency = idTypeCurrency;
	}

	public String getAcronym() {
		return acronym;
	}

	public void setAcronym(String acronym) {
		this.acronym = acronym;
	}

	public CatalogTypeCurrencyEntity getCatalogTypeCurrencyEntity() {
		return catalogTypeCurrencyEntity;
	}

	public void setCatalogTypeCurrencyEntity(CatalogTypeCurrencyEntity catalogTypeCurrencyEntity) {
		this.catalogTypeCurrencyEntity = catalogTypeCurrencyEntity;
	}

}
