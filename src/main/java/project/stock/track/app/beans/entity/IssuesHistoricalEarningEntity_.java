package project.stock.track.app.beans.entity;

import java.math.BigDecimal;
import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(IssuesHistoricalEarningEntity.class)
public abstract class IssuesHistoricalEarningEntity_ {

	public static volatile SingularAttribute<IssuesHistoricalEarningEntity, IssuesHistoricalEarningEntityPk> id;
	public static volatile SingularAttribute<IssuesHistoricalEarningEntity, BigDecimal> earningEstimate;
	public static volatile SingularAttribute<IssuesHistoricalEarningEntity, BigDecimal> earningReal;
	public static volatile SingularAttribute<IssuesHistoricalEarningEntity, CatalogIssuesEntity> catalogIssuesEntity;

}

