package project.stock.track.app.beans.pojos.business.historical;

import java.math.BigDecimal;

import project.stock.track.app.beans.pojos.tuple.IssueTransactionResumeTuplePojo;

public class IssueTransactionCalculatePojo extends IssueTransactionResumeTuplePojo {

	private BigDecimal gainLossPercentage;
	
	private BigDecimal gainLossTotal;
	
	private BigDecimal commisionOutcomeEstimate;
	
	private BigDecimal taxesOutcomeEstimate;
	
	private BigDecimal totalIncomeEstimate;
	
	private BigDecimal gainLossPercentageEstimate;
	
	private BigDecimal gainLossTotalEstimate;

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

	public BigDecimal getGainLossPercentageEstimate() {
		return gainLossPercentageEstimate;
	}

	public void setGainLossPercentageEstimate(BigDecimal gainLossPercentageEstimate) {
		this.gainLossPercentageEstimate = gainLossPercentageEstimate;
	}

	public BigDecimal getGainLossTotalEstimate() {
		return gainLossTotalEstimate;
	}

	public void setGainLossTotalEstimate(BigDecimal gainLossTotalEstimate) {
		this.gainLossTotalEstimate = gainLossTotalEstimate;
	}
}
