package project.stock.track.app.beans.pojos.business.transaction;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import project.stock.track.app.beans.pojos.entity.IssueLastPriceTmpEntityPojo;

@Getter @Setter
public class TransactionIssueTrackPojo extends IssueLastPriceTmpEntityPojo {
	
	private String idTrack;

	private BigDecimal gainLossPercentage;
	
	private BigDecimal gainLossTotal;
	
	private BigDecimal commisionOutcomeEstimate;
	
	private BigDecimal issueSellEstimate;
	
	private BigDecimal taxesOutcomeEstimate;
	
	private BigDecimal totalIncomeEstimate;
	
	private Long date;
	
	private BigDecimal priceBuy;
	
	private BigDecimal commisionPercentage;
	
	private BigDecimal priceTotalBuy;
	
	private Long totalTitles;
	
	private BigDecimal last;
	
	private BigDecimal high;
	
	private Long timestamp;
	
	private Long lastSaleTimestamp;
	
	private String isDownPercentageFromBuyPrice;
	
	private String isUpPercentageFromBuyPrice;
	
	private String isDownPercentageFromCurrentPrice;
	
	private String isNearPriceBuy;
	
	private Integer typeCurrency;
	
	private String descriptionTypeCurrency;
	
	private BigDecimal trackBuyPriceMxn;
	
	private BigDecimal trackBuyPriceUsd;
}
