package project.stock.track.app.beans.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import lib.base.backend.entity.generic.GenericCatalogIntEntity_;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(CatalogBrokerEntity.class)
public abstract class CatalogBrokerEntity_ extends GenericCatalogIntEntity_ {

	public static SingularAttribute<CatalogBrokerEntity, String> acronym;
	public static SingularAttribute<CatalogBrokerEntity, String> idTypeCurrency;
	public static SingularAttribute<CatalogBrokerEntity, CatalogTypeCurrencyEntity> catalogTypeCurrencyEntity;

}

