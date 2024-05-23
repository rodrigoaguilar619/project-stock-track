package project.stock.track.app.beans.pojos.business.issues;

import java.math.BigDecimal;

public class IssueMovementTransactionPojo {

	private Integer totalShares;
	
	private BigDecimal totalCost;
	
	private BigDecimal avgCostByShare;
	
	private BigDecimal performanceTotal;
	
	private BigDecimal performancePercentage;

	public Integer getTotalShares() {
		return totalShares;
	}

	public void setTotalShares(Integer totalShares) {
		this.totalShares = totalShares;
	}

	public BigDecimal getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(BigDecimal totalCost) {
		this.totalCost = totalCost;
	}

	public BigDecimal getAvgCostByShare() {
		return avgCostByShare;
	}

	public void setAvgCostByShare(BigDecimal avgCostByShare) {
		this.avgCostByShare = avgCostByShare;
	}

	public BigDecimal getPerformanceTotal() {
		return performanceTotal;
	}

	public void setPerformanceTotal(BigDecimal performanceTotal) {
		this.performanceTotal = performanceTotal;
	}

	public BigDecimal getPerformancePercentage() {
		return performancePercentage;
	}

	public void setPerformancePercentage(BigDecimal performancePercentage) {
		this.performancePercentage = performancePercentage;
	}
}
