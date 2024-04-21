package project.stock.track.app.beans.pojos.business.transaction;

import java.math.BigDecimal;

import project.stock.track.app.beans.pojos.tuple.IssueTransactionResumeTuplePojo;

public class TransactionIssueCalculatePojo extends IssueTransactionResumeTuplePojo {
	
	private BigDecimal gainLossPercentageEstimate;
	
	private BigDecimal gainLossTotalEstimate;
	
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
