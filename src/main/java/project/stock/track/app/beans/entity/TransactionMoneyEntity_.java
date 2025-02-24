package project.stock.track.app.beans.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import lib.base.backend.modules.security.jwt.entity.UserEntity;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(TransactionMoneyEntity.class)
public abstract class TransactionMoneyEntity_ {

	public static SingularAttribute<TransactionMoneyEntity, Integer> id;
	public static SingularAttribute<TransactionMoneyEntity, BigDecimal> amountMxn;
	public static SingularAttribute<TransactionMoneyEntity, BigDecimal> amount;
	public static SingularAttribute<TransactionMoneyEntity, Integer> idBroker;
	public static SingularAttribute<TransactionMoneyEntity, Integer> idUser;
	public static SingularAttribute<TransactionMoneyEntity, Integer> idIssue;
	public static SingularAttribute<TransactionMoneyEntity, Integer> idTypeMovement;
	public static SingularAttribute<TransactionMoneyEntity, LocalDateTime> dateTransaction;
	public static SingularAttribute<TransactionMoneyEntity, CatalogBrokerEntity> catalogBrokerEntity;
	public static SingularAttribute<TransactionMoneyEntity, CatalogTypeMovementEntity> catalogTypeMovementEntity;
	public static SingularAttribute<TransactionMoneyEntity, UserEntity> userEntity;
	public static SingularAttribute<TransactionMoneyEntity, CatalogIssuesEntity> catalogIssueEntity;
	public static SingularAttribute<TransactionMoneyEntity, String> information;

}

