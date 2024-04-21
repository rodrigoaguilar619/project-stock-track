package project.stock.track.app.beans.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(IssuesMovementsBuyEntity.class)
public abstract class IssuesMovementsBuyEntity_ {

	public static volatile SingularAttribute<IssuesMovementsBuyEntity, IssuesMovementsBuyEntityPk> id;
	public static volatile SingularAttribute<IssuesMovementsBuyEntity, BigDecimal> sellPrice;
	public static volatile SingularAttribute<IssuesMovementsBuyEntity, Date> sellDate;
	public static volatile SingularAttribute<IssuesMovementsBuyEntity, BigDecimal> buyPrice;
	public static volatile SingularAttribute<IssuesMovementsBuyEntity, Date> buyDate;
	public static volatile SingularAttribute<IssuesMovementsBuyEntity, IssuesMovementsEntity> issuesMovementsEntity;

	public static final String ID = "id";
	public static final String SELL_PRICE = "sellPrice";
	public static final String SELL_DATE = "sellDate";
	public static final String BUY_PRICE = "buyPrice";
	public static final String BUY_DATE = "buyDate";
	public static final String ISSUEs_MOVEMENTS_ENTITY = "issuesMovementsEntity";

}

