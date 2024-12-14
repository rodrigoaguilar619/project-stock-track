package project.stock.track.app.beans.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(DollarHistoricalPriceEntity.class)
public abstract class DollarHistoricalPriceEntity_ {

	public static SingularAttribute<DollarHistoricalPriceEntity, BigDecimal> price;
	public static SingularAttribute<DollarHistoricalPriceEntity, LocalDateTime> idDate;

	public static final String PRICE = "price";
	public static final String ID_DATE = "idDate";

}

