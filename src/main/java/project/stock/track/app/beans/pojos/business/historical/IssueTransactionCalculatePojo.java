package project.stock.track.app.beans.pojos.business.historical;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import project.stock.track.app.beans.pojos.tuple.IssueTransactionResumeTuplePojo;

@Getter @Setter
public class IssueTransactionCalculatePojo extends IssueTransactionResumeTuplePojo {

	private BigDecimal gainLossPercentage;
	
	private BigDecimal gainLossTotal;
	
	private BigDecimal commisionOutcomeEstimate;
	
	private BigDecimal taxesOutcomeEstimate;
	
	private BigDecimal totalIncomeEstimate;
	
	private BigDecimal gainLossPercentageEstimate;
	
	private BigDecimal gainLossTotalEstimate;
}
