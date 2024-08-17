package project.stock.track.app.beans.pojos.business.issues;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class IssueManagerTrackResumePojo {

	private Integer idIssue;

	private Integer idStatusIssueQuick;
	
	private Integer idStatusIssueTrading;
	
	private Boolean isInvest;
	
	private BigDecimal trackBuyPrice;
	
	private BigDecimal trackSellPrice;
	
	private BigDecimal fairValue;
}
