package project.stock.track.app.beans.pojos.business.portfolio;

import java.math.BigDecimal;

public class PorfolioIssuePojo {

	private Integer idIssue;
	
	private String initials;
	
	private Integer idTypeCurrency;
	
	private Integer titles;
	
	private BigDecimal costAvgBuyPerTitle;
	
	private BigDecimal costAvgSellPerTitle;
	
	private BigDecimal costAvgSellPerTitleMxn;
	
	private BigDecimal costTotalBuy;
	
	private BigDecimal costTotalSell;
	
	private BigDecimal costTotalSellMxn;
	
	private BigDecimal totalYield;
	
	private Long lastUpdate;

	public Integer getIdIssue() {
		return idIssue;
	}

	public void setIdIssue(Integer idIssue) {
		this.idIssue = idIssue;
	}

	public String getInitials() {
		return initials;
	}

	public void setInitials(String initials) {
		this.initials = initials;
	}

	public Integer getIdTypeCurrency() {
		return idTypeCurrency;
	}

	public void setIdTypeCurrency(Integer idTypeCurrency) {
		this.idTypeCurrency = idTypeCurrency;
	}

	public Integer getTitles() {
		return titles;
	}

	public void setTitles(Integer titles) {
		this.titles = titles;
	}

	public BigDecimal getCostAvgBuyPerTitle() {
		return costAvgBuyPerTitle;
	}

	public void setCostAvgBuyPerTitle(BigDecimal costAvgBuyPerTitle) {
		this.costAvgBuyPerTitle = costAvgBuyPerTitle;
	}

	public BigDecimal getCostAvgSellPerTitle() {
		return costAvgSellPerTitle;
	}

	public void setCostAvgSellPerTitle(BigDecimal costAvgSellPerTitle) {
		this.costAvgSellPerTitle = costAvgSellPerTitle;
	}

	public BigDecimal getCostAvgSellPerTitleMxn() {
		return costAvgSellPerTitleMxn;
	}

	public void setCostAvgSellPerTitleMxn(BigDecimal costAvgSellPerTitleMxn) {
		this.costAvgSellPerTitleMxn = costAvgSellPerTitleMxn;
	}

	public BigDecimal getCostTotalBuy() {
		return costTotalBuy;
	}

	public void setCostTotalBuy(BigDecimal costTotalBuy) {
		this.costTotalBuy = costTotalBuy;
	}

	public BigDecimal getCostTotalSell() {
		return costTotalSell;
	}

	public void setCostTotalSell(BigDecimal costTotalSell) {
		this.costTotalSell = costTotalSell;
	}

	public BigDecimal getCostTotalSellMxn() {
		return costTotalSellMxn;
	}

	public void setCostTotalSellMxn(BigDecimal costTotalSellMxn) {
		this.costTotalSellMxn = costTotalSellMxn;
	}

	public BigDecimal getYield() {
		return totalYield;
	}

	public void setYield(BigDecimal totalYield) {
		this.totalYield = totalYield;
	}

	public Long getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Long lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
}
