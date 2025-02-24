package project.stock.track.app.beans.entity;

import java.math.BigDecimal;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(IssuesHistoricalEntity.class)
public abstract class IssuesHistoricalEntity_ {

	public static SingularAttribute<IssuesHistoricalEntity, BigDecimal> volume;
	public static SingularAttribute<IssuesHistoricalEntity, IssuesHistoricalEntityPk> id;
	public static SingularAttribute<IssuesHistoricalEntity, BigDecimal> high;
	public static SingularAttribute<IssuesHistoricalEntity, BigDecimal> low;
	public static SingularAttribute<IssuesHistoricalEntity, BigDecimal> close;
	public static SingularAttribute<IssuesHistoricalEntity, BigDecimal> open;
	public static SingularAttribute<IssuesHistoricalEntity, CatalogIssuesEntity> catalogIssuesEntity;

}

