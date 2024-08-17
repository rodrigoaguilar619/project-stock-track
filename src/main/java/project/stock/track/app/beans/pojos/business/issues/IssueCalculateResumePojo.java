package project.stock.track.app.beans.pojos.business.issues;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class IssueCalculateResumePojo {

	private BigDecimal currentIssuePrice;
	
	private Long currentIssueDate;
	
	private BigDecimal currentDollarPrice;
	
	private Long currentDollarDate;
	
	private BigDecimal currentDollarPriceAfterDeprecate;
	
	private BigDecimal issueSellEstimate;
	
	private BigDecimal commisionSell;
	
	private BigDecimal taxesPercentage;
	
	private BigDecimal dollarPriceDeprecatePercentage;
}
