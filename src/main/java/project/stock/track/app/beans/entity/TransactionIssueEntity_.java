package project.stock.track.app.beans.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(TransactionIssueEntity.class)
public abstract class TransactionIssueEntity_ {

	public static SingularAttribute<TransactionIssueEntity, Integer> id;
	public static SingularAttribute<TransactionIssueEntity, BigDecimal> sellGainLossTotal;
	public static SingularAttribute<TransactionIssueEntity, BigDecimal> sellGainLossTotalMxn;
	public static SingularAttribute<TransactionIssueEntity, CatalogBrokerEntity> catalogBrokerEntity;
	public static SingularAttribute<TransactionIssueEntity, BigDecimal> priceBuy;
	public static SingularAttribute<TransactionIssueEntity, BigDecimal> priceBuyMxn;
	public static SingularAttribute<TransactionIssueEntity, LocalDateTime> buyDate;
	public static SingularAttribute<TransactionIssueEntity, IssuesLastPriceTmpEntity> issuesLastPriceTmpEntity;
	public static SingularAttribute<TransactionIssueEntity, IssuesManagerEntity> issuesManagerEntity;
	public static SingularAttribute<TransactionIssueEntity, BigDecimal> commisionPercentage;
	public static SingularAttribute<TransactionIssueEntity, BigDecimal> priceSell;
	public static SingularAttribute<TransactionIssueEntity, BigDecimal> priceSellMxn;
	public static SingularAttribute<TransactionIssueEntity, BigDecimal> sellTaxesPercentage;
	public static SingularAttribute<TransactionIssueEntity, Integer> idIssue;
	public static SingularAttribute<TransactionIssueEntity, Integer> idUser;
	public static SingularAttribute<TransactionIssueEntity, Integer> idBroker;
	public static SingularAttribute<TransactionIssueEntity, BigDecimal> sellCommisionPercentage;
	public static SingularAttribute<TransactionIssueEntity, String> idTypeCurrency;
	public static SingularAttribute<TransactionIssueEntity, BigDecimal> priceTotalBuy;
	public static SingularAttribute<TransactionIssueEntity, BigDecimal> priceTotalBuyMxn;
	public static SingularAttribute<TransactionIssueEntity, LocalDateTime> sellDate;
	public static SingularAttribute<TransactionIssueEntity, BigDecimal> priceTotalSell;
	public static SingularAttribute<TransactionIssueEntity, BigDecimal> priceTotalSellMxn;
	public static SingularAttribute<TransactionIssueEntity, BigDecimal> sellGainLossPercentage;
	public static SingularAttribute<TransactionIssueEntity, BigDecimal> totalShares;
	public static SingularAttribute<TransactionIssueEntity, Boolean> isSlice;
	public static SingularAttribute<TransactionIssueEntity, Boolean> isShortSell;

}

