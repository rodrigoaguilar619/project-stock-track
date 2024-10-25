package project.stock.track.app.beans.entity;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(IssuesMovementsBuyEntity.class)
public abstract class IssuesMovementsBuyEntity_ {

	public static SingularAttribute<IssuesMovementsBuyEntity, IssuesMovementsBuyEntityPk> id;
	public static SingularAttribute<IssuesMovementsBuyEntity, BigDecimal> sellPrice;
	public static SingularAttribute<IssuesMovementsBuyEntity, BigDecimal> sellPriceMxn;
	public static SingularAttribute<IssuesMovementsBuyEntity, Date> sellDate;
	public static SingularAttribute<IssuesMovementsBuyEntity, BigDecimal> buyPrice;
	public static SingularAttribute<IssuesMovementsBuyEntity, BigDecimal> buyPriceMxn;
	public static SingularAttribute<IssuesMovementsBuyEntity, Date> buyDate;
	public static SingularAttribute<IssuesMovementsBuyEntity, BigDecimal> totalShares;
	public static SingularAttribute<IssuesMovementsBuyEntity, IssuesMovementsEntity> issuesMovementsEntity;

	public static final String ID = "id";
	public static final String SELL_PRICE = "sellPrice";
	public static final String SELL_PRICE_MXN = "sellPriceMxn";
	public static final String SELL_DATE = "sellDate";
	public static final String BUY_PRICE = "buyPrice";
	public static final String BUY_PRICE_MXN = "buyPriceMxn";
	public static final String BUY_DATE = "buyDate";
	public static final String TOTAL_SHARES = "totalShares";
	public static final String ISSUEs_MOVEMENTS_ENTITY = "issuesMovementsEntity";

}

