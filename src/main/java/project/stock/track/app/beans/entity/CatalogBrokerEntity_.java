package project.stock.track.app.beans.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(CatalogBrokerEntity.class)
public abstract class CatalogBrokerEntity_ {

	public static SingularAttribute<CatalogBrokerEntity, Integer> id;
	public static SingularAttribute<CatalogBrokerEntity, String> description;
	public static SingularAttribute<CatalogBrokerEntity, String> acronym;
	public static SingularAttribute<CatalogBrokerEntity, String> idTypeCurrency;
	public static SingularAttribute<CatalogBrokerEntity, CatalogTypeCurrencyEntity> catalogTypeCurrencyEntity;

	public static final String ID = "id";
	public static final String DESCRIPTION = "description";
	public static final String ACRONYM = "acronym";
	public static final String ID_TYPE_CURRENCY = "idTypeCurrency";
	public static final String CATALOG_TYPE_CURRENCY_ENTITY = "catalogTypeCurrencyEntity";

}

