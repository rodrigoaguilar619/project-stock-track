package project.stock.track.app.beans.entity;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

import lib.base.backend.modules.security.jwt.entity.UserEntity;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(TransactionMoneyEntity.class)
public abstract class TransactionMoneyEntity_ {

	public static volatile SingularAttribute<TransactionMoneyEntity, Integer> id;
	public static volatile SingularAttribute<TransactionMoneyEntity, BigDecimal> valueMxn;
	public static volatile SingularAttribute<TransactionMoneyEntity, BigDecimal> amount;
	public static volatile SingularAttribute<TransactionMoneyEntity, Integer> idBroker;
	public static volatile SingularAttribute<TransactionMoneyEntity, Integer> idUser;
	public static volatile SingularAttribute<TransactionMoneyEntity, Integer> idTypeMovement;
	public static volatile SingularAttribute<TransactionMoneyEntity, String> currency;
	public static volatile SingularAttribute<TransactionMoneyEntity, Date> dateTransaction;
	public static volatile SingularAttribute<TransactionMoneyEntity, CatalogBrokerEntity> catalogBrokerEntity;
	public static volatile SingularAttribute<TransactionMoneyEntity, CatalogTypeMovementEntity> catalogTypeMovementEntity;
	public static volatile SingularAttribute<TransactionMoneyEntity, UserEntity> userEntity;

	public static final String ID = "id";
	public static final String VALUE_MXN = "valueMxn";
	public static final String AMOUNT = "amount";
	public static final String ID_BROKER = "idBroker";
	public static final String ID_USER = "idUser";
	public static final String ID_TYPE_MOVEMENT = "idTypeMovement";
	public static final String CURRENCY = "currency";
	public static final String DATE_TRANSACTION = "dateTransaction";
	public static final String CATALOG_BROKER_ENTITY = "catalogBrokerEntity";
	public static final String CATALOG_TYPE_MOVEMENT_ENTITY = "catalogTypeMovementEntity";
	public static final String user_ENTITY = "USEREntity";

}

