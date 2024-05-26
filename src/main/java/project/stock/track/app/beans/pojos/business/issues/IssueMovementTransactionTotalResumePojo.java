package project.stock.track.app.beans.pojos.business.issues;

import java.math.BigDecimal;

public class IssueMovementTransactionTotalResumePojo {

	private BigDecimal totalCurrentPrice;
	
	private BigDecimal totalBuyPrice;
	
	private BigDecimal performanceTotal;
	
	private BigDecimal performancePercentage;

	public BigDecimal getTotalCurrentPrice() {
		return totalCurrentPrice;
	}

	public void setTotalCurrentPrice(BigDecimal totalCurrentPrice) {
		this.totalCurrentPrice = totalCurrentPrice;
	}

	public BigDecimal getTotalBuyPrice() {
		return totalBuyPrice;
	}

	public void setTotalBuyPrice(BigDecimal totalBuyPrice) {
		this.totalBuyPrice = totalBuyPrice;
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
