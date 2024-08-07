package project.stock.track.app.beans.entity;

import java.math.BigDecimal;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(IssuesHistoricalEntity.class)
public abstract class IssuesHistoricalEntity_ {

	public static SingularAttribute<IssuesHistoricalEntity, BigDecimal> volume;
	public static SingularAttribute<IssuesHistoricalEntity, IssuesHistoricalEntityId> issuesHistoricalEntityId;
	public static SingularAttribute<IssuesHistoricalEntity, BigDecimal> high;
	public static SingularAttribute<IssuesHistoricalEntity, BigDecimal> low;
	public static SingularAttribute<IssuesHistoricalEntity, BigDecimal> close;
	public static SingularAttribute<IssuesHistoricalEntity, BigDecimal> open;
	public static SingularAttribute<IssuesHistoricalEntity, CatalogIssuesEntity> catalogIssuesEntity;

	public static final String VOLUME = "volume";
	public static final String ISSUES_HISTORICAL_ENTITY_ID = "issuesHistoricalEntityId";
	public static final String HIGH = "high";
	public static final String LOW = "low";
	public static final String CLOSE = "close";
	public static final String OPEN = "open";
	public static final String CATALOG_ISSUES_ENTITY = "catalogIssuesEntity";

}

