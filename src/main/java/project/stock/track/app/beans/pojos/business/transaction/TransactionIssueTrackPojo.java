package project.stock.track.app.beans.pojos.business.transaction;

import java.math.BigDecimal;

import project.stock.track.app.beans.pojos.entity.IssueLastPriceTmpEntityPojo;

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

	public BigDecimal getGainLossPercentage() {
		return gainLossPercentage;
	}

	public void setGainLossPercentage(BigDecimal gainLossPercentage) {
		this.gainLossPercentage = gainLossPercentage;
	}

	public BigDecimal getGainLossTotal() {
		return gainLossTotal;
	}

	public void setGainLossTotal(BigDecimal gainLossTotal) {
		this.gainLossTotal = gainLossTotal;
	}

	public BigDecimal getCommisionOutcomeEstimate() {
		return commisionOutcomeEstimate;
	}

	public void setCommisionOutcomeEstimate(BigDecimal commisionOutcomeEstimate) {
		this.commisionOutcomeEstimate = commisionOutcomeEstimate;
	}

	public BigDecimal getTaxesOutcomeEstimate() {
		return taxesOutcomeEstimate;
	}

	public void setTaxesOutcomeEstimate(BigDecimal taxesOutcomeEstimate) {
		this.taxesOutcomeEstimate = taxesOutcomeEstimate;
	}

	public BigDecimal getTotalIncomeEstimate() {
		return totalIncomeEstimate;
	}

	public void setTotalIncomeEstimate(BigDecimal totalIncomeEstimate) {
		this.totalIncomeEstimate = totalIncomeEstimate;
	}

	public BigDecimal getIssueSellEstimate() {
		return issueSellEstimate;
	}

	public void setIssueSellEstimate(BigDecimal issueSellEstimate) {
		this.issueSellEstimate = issueSellEstimate;
	}

	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
	}

	public BigDecimal getPriceBuy() {
		return priceBuy;
	}

	public void setPriceBuy(BigDecimal priceBuy) {
		this.priceBuy = priceBuy;
	}

	public BigDecimal getCommisionPercentage() {
		return commisionPercentage;
	}

	public void setCommisionPercentage(BigDecimal commisionPercentage) {
		this.commisionPercentage = commisionPercentage;
	}

	public BigDecimal getPriceTotalBuy() {
		return priceTotalBuy;
	}

	public void setPriceTotalBuy(BigDecimal priceTotalBuy) {
		this.priceTotalBuy = priceTotalBuy;
	}

	public Long getTotalTitles() {
		return totalTitles;
	}

	public void setTotalTitles(Long totalTitles) {
		this.totalTitles = totalTitles;
	}

	public BigDecimal getLast() {
		return last;
	}

	public void setLast(BigDecimal last) {
		this.last = last;
	}

	public BigDecimal getHigh() {
		return high;
	}

	public void setHigh(BigDecimal high) {
		this.high = high;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public Long getLastSaleTimestamp() {
		return lastSaleTimestamp;
	}

	public void setLastSaleTimestamp(Long lastSaleTimestamp) {
		this.lastSaleTimestamp = lastSaleTimestamp;
	}

	public String getIsDownPercentageFromBuyPrice() {
		return isDownPercentageFromBuyPrice;
	}

	public void setIsDownPercentageFromBuyPrice(String isDownPercentageFromBuyPrice) {
		this.isDownPercentageFromBuyPrice = isDownPercentageFromBuyPrice;
	}

	public String getIsUpPercentageFromBuyPrice() {
		return isUpPercentageFromBuyPrice;
	}

	public void setIsUpPercentageFromBuyPrice(String isUpPercentageFromBuyPrice) {
		this.isUpPercentageFromBuyPrice = isUpPercentageFromBuyPrice;
	}

	public String getIsDownPercentageFromCurrentPrice() {
		return isDownPercentageFromCurrentPrice;
	}

	public void setIsDownPercentageFromCurrentPrice(String isDownPercentageFromCurrentPrice) {
		this.isDownPercentageFromCurrentPrice = isDownPercentageFromCurrentPrice;
	}

	public String getIsNearPriceBuy() {
		return isNearPriceBuy;
	}

	public void setIsNearPriceBuy(String isNearPriceBuy) {
		this.isNearPriceBuy = isNearPriceBuy;
	}

	public Integer getTypeCurrency() {
		return typeCurrency;
	}

	public void setTypeCurrency(Integer typeCurrency) {
		this.typeCurrency = typeCurrency;
	}

	public String getDescriptionTypeCurrency() {
		return descriptionTypeCurrency;
	}

	public void setDescriptionTypeCurrency(String descriptionTypeCurrency) {
		this.descriptionTypeCurrency = descriptionTypeCurrency;
	}

	public BigDecimal getTrackBuyPriceMxn() {
		return trackBuyPriceMxn;
	}

	public void setTrackBuyPriceMxn(BigDecimal trackBuyPriceMxn) {
		this.trackBuyPriceMxn = trackBuyPriceMxn;
	}

	public BigDecimal getTrackBuyPriceUsd() {
		return trackBuyPriceUsd;
	}

	public void setTrackBuyPriceUsd(BigDecimal trackBuyPriceUsd) {
		this.trackBuyPriceUsd = trackBuyPriceUsd;
	}

	public String getIdTrack() {
		return idTrack;
	}

	public void setIdTrack(String idTrack) {
		this.idTrack = idTrack;
	}
}
