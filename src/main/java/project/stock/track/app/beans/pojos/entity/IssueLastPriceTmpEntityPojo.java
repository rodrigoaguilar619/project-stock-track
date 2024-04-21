package project.stock.track.app.beans.pojos.entity;

import java.math.BigDecimal;

public class IssueLastPriceTmpEntityPojo {

	private String issue;
	
	private Integer idIssue;
	
	private BigDecimal currentPrice;
	
	private BigDecimal open;
	
	private BigDecimal volume;
	
	private BigDecimal previousPriceClose;
	
	private BigDecimal highPrice;
	
	private Long datePrice;
	
	private Long currentDatePrice;

	public String getIssue() {
		return issue;
	}

	public void setIssue(String issue) {
		this.issue = issue;
	}

	public BigDecimal getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(BigDecimal currentPrice) {
		this.currentPrice = currentPrice;
	}

	public BigDecimal getOpen() {
		return open;
	}

	public void setOpen(BigDecimal open) {
		this.open = open;
	}

	public BigDecimal getVolume() {
		return volume;
	}

	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}

	public BigDecimal getPreviousPriceClose() {
		return previousPriceClose;
	}

	public void setPreviousPriceClose(BigDecimal previousPriceClose) {
		this.previousPriceClose = previousPriceClose;
	}

	public BigDecimal getHighPrice() {
		return highPrice;
	}

	public void setHighPrice(BigDecimal highPrice) {
		this.highPrice = highPrice;
	}

	public Long getDatePrice() {
		return datePrice;
	}

	public void setDatePrice(Long datePrice) {
		this.datePrice = datePrice;
	}

	public Long getCurrentDatePrice() {
		return currentDatePrice;
	}

	public void setCurrentDatePrice(Long currentDatePrice) {
		this.currentDatePrice = currentDatePrice;
	}

	public Integer getIdIssue() {
		return idIssue;
	}

	public void setIdIssue(Integer idIssue) {
		this.idIssue = idIssue;
	}
}
