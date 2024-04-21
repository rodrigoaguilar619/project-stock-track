package project.stock.track.app.beans.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(DollarHistoricalPriceEntity.class)
public abstract class DollarHistoricalPriceEntity_ {

	public static volatile SingularAttribute<DollarHistoricalPriceEntity, BigDecimal> price;
	public static volatile SingularAttribute<DollarHistoricalPriceEntity, Date> idDate;

	public static final String PRICE = "price";
	public static final String ID_DATE = "idDate";

}

