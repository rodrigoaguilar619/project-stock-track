package project.stock.track.app.beans.pojos.business.issues;

import java.math.BigDecimal;

public class IssueHistoricalTrackResumenPojo {

	private BigDecimal buyPrice;

	private BigDecimal sellPrice;
	
	private BigDecimal fairValue;
	
	private BigDecimal currentPrice;
	
	private Long currentPriceDate;
	
	private BigDecimal previousClosePrice;
	
	private Long previousClosePriceDate;

	public BigDecimal getBuyPrice() {
		return buyPrice;
	}

	public void setBuyPrice(BigDecimal buyPrice) {
		this.buyPrice = buyPrice;
	}

	public BigDecimal getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(BigDecimal sellPrice) {
		this.sellPrice = sellPrice;
	}

	public BigDecimal getFairValue() {
		return fairValue;
	}

	public void setFairValue(BigDecimal fairValue) {
		this.fairValue = fairValue;
	}

	public BigDecimal getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(BigDecimal currentPrice) {
		this.currentPrice = currentPrice;
	}

	public Long getCurrentPriceDate() {
		return currentPriceDate;
	}

	public void setCurrentPriceDate(Long currentPriceDate) {
		this.currentPriceDate = currentPriceDate;
	}

	public BigDecimal getPreviousClosePrice() {
		return previousClosePrice;
	}

	public void setPreviousClosePrice(BigDecimal previousClosePrice) {
		this.previousClosePrice = previousClosePrice;
	}

	public Long getPreviousClosePriceDate() {
		return previousClosePriceDate;
	}

	public void setPreviousClosePriceDate(Long previousClosePriceDate) {
		this.previousClosePriceDate = previousClosePriceDate;
	}
}
