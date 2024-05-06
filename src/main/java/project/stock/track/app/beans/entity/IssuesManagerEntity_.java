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
	
	public static final String ID = "id";
	public static final String TRANSACTION_ISSUE_ENTITIES = "transactionIssueEntities";
	public static final String ID_STATUS_ISSUE_TRADING = "idStatusIssueTrading";
	public static final String CATALOG_STATUS_ISSUE_TRADING_ENTITY = "catalogStatusIssueTradingEntity";
	public static final String ID_STATUS_ISSUE_QUICK = "idStatusIssueQuick";
	public static final String CATALOG_ISSUES_ENTITY = "catalogIssueEntity";
	public static final String CATALOG_STATUS_ISSUE_QUICK_ENTITY = "catalogStatusIssueQuickEntity";
	public static final String ISSUES_MANAGER_TRACK_PROPERTIES_ENTITY = "issuesManagerTrackPropertiesEntity";

}

