package project.stock.track.app.beans.entity;

import java.math.BigDecimal;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(IssuesMovementsEntity.class)
public abstract class IssuesMovementsEntity_ {

	public static SingularAttribute<IssuesMovementsEntity, Integer> id;
	public static SingularAttribute<IssuesMovementsEntity, Integer> idIssue;
	public static SingularAttribute<IssuesMovementsEntity, Integer> idUser;
	public static SingularAttribute<IssuesMovementsEntity, String> idStatus;
	public static ListAttribute<IssuesMovementsEntity, IssuesMovementsBuyEntity> issuesMovementsBuys;
	public static SingularAttribute<IssuesMovementsEntity, CatalogStatusIssueMovementEntity> catalogStatusIssueMovementEntity;
	public static SingularAttribute<IssuesMovementsEntity, BigDecimal> priceMovement;
	public static SingularAttribute<IssuesMovementsEntity, CatalogBrokerEntity> catalogBroker;
	public static SingularAttribute<IssuesMovementsEntity, IssuesManagerEntity> issuesManagerEntity;

	public static final String ID = "id";
	public static final String ID_ISSUE = "id_issue";
	public static final String ID_USER = "id_user";
	public static final String ID_STATUS = "idStatus";
	public static final String MOVEMENTS_ISSUE_BUYS = "issuesMovementsBuys";
	public static final String CATALOG_STATUS_ISSUE_MOVEMENT_ENTITY = "catalogStatusIssueMovementEntity";
	public static final String PRICE_MOVEMENT = "priceMovement";
	public static final String CATALOG_BROKER = "catalogBroker";
	public static final String ISSUES_MANAGER_ENTITY = "issuesManagerEntity";

}

