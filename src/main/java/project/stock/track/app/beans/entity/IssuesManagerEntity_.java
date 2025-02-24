package project.stock.track.app.beans.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(IssuesManagerEntity.class)
public abstract class IssuesManagerEntity_ {

	public static SingularAttribute<IssuesManagerEntity, IssuesManagerEntityPk> id;
	public static SingularAttribute<IssuesManagerEntity, String> idStatusIssueTrading;
	public static SingularAttribute<IssuesManagerEntity, CatalogStatusIssueEntity> catalogStatusIssueTradingEntity;
	public static SingularAttribute<IssuesManagerEntity, String> idStatusIssueQuick;
	public static SingularAttribute<IssuesManagerEntity, CatalogIssuesEntity> catalogIssueEntity;
	public static SingularAttribute<IssuesManagerEntity, CatalogStatusIssueEntity> catalogStatusIssueQuickEntity;
	public static SingularAttribute<IssuesManagerEntity, IssuesManagerTrackPropertiesEntity> issuesManagerTrackPropertiesEntity;
	public static ListAttribute<IssuesManagerEntity, TransactionIssueEntity> transactionIssueEntities;

}

