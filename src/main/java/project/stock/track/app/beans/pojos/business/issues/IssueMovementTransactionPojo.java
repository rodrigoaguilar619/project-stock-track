package project.stock.track.app.beans.pojos.business.issues;

import java.math.BigDecimal;

public class IssueMovementTransactionPojo {

	private BigDecimal totalShares;
	
	private BigDecimal currentPriceByShare;
	
	private BigDecimal avgCostByShare;
	
	private BigDecimal performanceByShare;
	
	private BigDecimal performanceTotal;
	
	private BigDecimal performancePercentage;

	public BigDecimal getTotalShares() {
		return totalShares;
	}

	public void setTotalShares(BigDecimal totalShares) {
		this.totalShares = totalShares;
	}

	public BigDecimal getCurrentPriceByShare() {
		return currentPriceByShare;
	}

	public void setCurrentPriceByShare(BigDecimal currentPriceByShare) {
		this.currentPriceByShare = currentPriceByShare;
	}

	public BigDecimal getAvgCostByShare() {
		return avgCostByShare;
	}

	public void setAvgCostByShare(BigDecimal avgCostByShare) {
		this.avgCostByShare = avgCostByShare;
	}

	public BigDecimal getPerformanceByShare() {
		return performanceByShare;
	}

	public void setPerformanceByShare(BigDecimal performanceByShare) {
		this.performanceByShare = performanceByShare;
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
