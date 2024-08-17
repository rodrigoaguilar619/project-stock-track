package project.stock.track.app.beans.pojos.business.issues;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class IssueMovementTransactionPojo {

	private BigDecimal totalShares;
	
	private BigDecimal currentPriceByShare;
	
	private BigDecimal avgCostByShare;
	
	private BigDecimal performanceByShare;
	
	private BigDecimal performanceTotal;
	
	private BigDecimal performancePercentage;
}
