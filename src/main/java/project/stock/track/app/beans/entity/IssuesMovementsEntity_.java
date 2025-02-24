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
	public static SingularAttribute<IssuesMovementsEntity, Integer> idBroker;
	public static ListAttribute<IssuesMovementsEntity, IssuesMovementsBuyEntity> issuesMovementsBuys;
	public static SingularAttribute<IssuesMovementsEntity, CatalogStatusIssueMovementEntity> catalogStatusIssueMovementEntity;
	public static SingularAttribute<IssuesMovementsEntity, BigDecimal> priceMovement;
	public static SingularAttribute<IssuesMovementsEntity, CatalogBrokerEntity> catalogBrokerEntity;
	public static SingularAttribute<IssuesMovementsEntity, IssuesManagerEntity> issuesManagerEntity;

}

