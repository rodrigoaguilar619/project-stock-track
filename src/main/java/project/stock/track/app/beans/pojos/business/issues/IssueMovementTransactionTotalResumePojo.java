package project.stock.track.app.beans.pojos.business.issues;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class IssueMovementTransactionTotalResumePojo {

	private BigDecimal totalCurrentPrice;
	
	private BigDecimal totalBuyPrice;
	
	private BigDecimal performanceTotal;
	
	private BigDecimal performancePercentage;
}
