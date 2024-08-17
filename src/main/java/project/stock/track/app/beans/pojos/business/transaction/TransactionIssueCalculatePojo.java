package project.stock.track.app.beans.pojos.business.transaction;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import project.stock.track.app.beans.pojos.tuple.IssueTransactionResumeTuplePojo;

@Getter @Setter
public class TransactionIssueCalculatePojo extends IssueTransactionResumeTuplePojo {
	
	private BigDecimal gainLossPercentageEstimate;
	
	private BigDecimal gainLossTotalEstimate;
}
