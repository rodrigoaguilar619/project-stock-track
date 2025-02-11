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

	public static final String ID = "id";
	public static final String SELL_GAIN_LOSS_TOTAL = "sellGainLossTotal";
	public static final String SELL_GAIN_LOSS_TOTAL_MXN = "sellGainLossTotalMxn";
	public static final String CATALOG_BROKER_ENTITY = "catalogBrokerEntity";
	public static final String PRICE_BUY = "priceBuy";
	public static final String PRICE_BUY_MXN = "priceBuyMxn";
	public static final String BUY_DATE = "buyDate";
	public static final String ISSUES_LAST_PRICE_TMP_ENTITY = "issuesLastPriceTmpEntity";
	public static final String ISSUES_MANAGER_ENTITY = "issuesManagerEntity";
	public static final String COMMISION_PERCENTAGE = "commisionPercentage";
	public static final String PRICE_SELL = "priceSell";
	public static final String PRICE_SELL_MXN = "priceSellMxn";
	public static final String SELL_TAXES_PERCENTAGE = "sellTaxesPercentage";
	public static final String ID_ISSUE = "idIssue";
	public static final String ID_USER = "idUser";
	public static final String ID_BROKER = "idBroker";
	public static final String SELL_COMMISION_PERCENTAGE = "sellCommisionPercentage";
	public static final String ID_TYPE_CURRENCY = "idTypeCurrency";
	public static final String PRICE_TOTAL_BUY = "priceTotalBuy";
	public static final String PRICE_TOTAL_BUY_MXN = "priceTotalBuyMxn";
	public static final String SELL_DATE = "sellDate";
	public static final String PRICE_TOTAL_SELL = "priceTotalSell";
	public static final String PRICE_TOTAL_SELL_MXN = "priceTotalSellMxn";
	public static final String SELL_GAIN_LOSS_PERCENTAGE = "sellGainLossPercentage";
	public static final String TOTAL_SHARES = "totalShares";
	public static final String IS_SLICE = "isSlice";
	public static final String IS_SHORT_SELL = "isShortSell";

}

