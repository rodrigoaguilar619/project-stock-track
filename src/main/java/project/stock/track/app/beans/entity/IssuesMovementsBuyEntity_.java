package project.stock.track.app.beans.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(IssuesMovementsBuyEntity.class)
public abstract class IssuesMovementsBuyEntity_ {

	public static SingularAttribute<IssuesMovementsBuyEntity, IssuesMovementsBuyEntityPk> id;
	public static SingularAttribute<IssuesMovementsBuyEntity, BigDecimal> sellPrice;
	public static SingularAttribute<IssuesMovementsBuyEntity, BigDecimal> sellPriceMxn;
	public static SingularAttribute<IssuesMovementsBuyEntity, LocalDateTime> sellDate;
	public static SingularAttribute<IssuesMovementsBuyEntity, BigDecimal> buyPrice;
	public static SingularAttribute<IssuesMovementsBuyEntity, BigDecimal> buyPriceMxn;
	public static SingularAttribute<IssuesMovementsBuyEntity, LocalDateTime> buyDate;
	public static SingularAttribute<IssuesMovementsBuyEntity, BigDecimal> totalShares;
	public static SingularAttribute<IssuesMovementsBuyEntity, IssuesMovementsEntity> issuesMovementsEntity;

}

