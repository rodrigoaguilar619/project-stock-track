package project.stock.track.app.beans.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(IssuesManagerEntity.class)
public abstract class IssuesManagerEntity_ {

	public static volatile SingularAttribute<IssuesManagerEntity, IssuesManagerEntityPk> id;
	public static volatile SingularAttribute<IssuesManagerEntity, String> idStatusIssueTrading;
	public static volatile SingularAttribute<IssuesManagerEntity, CatalogStatusIssueEntity> catalogStatusIssueTradingEntity;
	public static volatile SingularAttribute<IssuesManagerEntity, String> idStatusIssueQuick;
	public static volatile SingularAttribute<IssuesManagerEntity, CatalogIssuesEntity> catalogIssueEntity;
	public static volatile SingularAttribute<IssuesManagerEntity, CatalogStatusIssueEntity> catalogStatusIssueQuickEntity;
	public static volatile SingularAttribute<IssuesManagerEntity, IssuesManagerTrackPropertiesEntity> issuesManagerTrackPropertiesEntity;
	public static volatile ListAttribute<IssuesManagerEntity, TransactionIssueEntity> transactionIssueEntities;
	
	public static final String ID = "id";
	public static final String TRANSACTION_ISSUE_ENTITIES = "transactionIssueEntities";
	public static final String ID_STATUS_ISSUE_TRADING = "idStatusIssueTrading";
	public static final String CATALOG_STATUS_ISSUE_TRADING_ENTITY = "catalogStatusIssueTradingEntity";
	public static final String ID_STATUS_ISSUE_QUICK = "idStatusIssueQuick";
	public static final String CATALOG_ISSUES_ENTITY = "catalogIssueEntity";
	public static final String CATALOG_STATUS_ISSUE_QUICK_ENTITY = "catalogStatusIssueQuickEntity";
	public static final String ISSUES_MANAGER_TRACK_PROPERTIES_ENTITY = "issuesManagerTrackPropertiesEntity";

}

