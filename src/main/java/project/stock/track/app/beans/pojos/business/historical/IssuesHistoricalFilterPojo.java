package project.stock.track.app.beans.pojos.business.historical;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class IssuesHistoricalFilterPojo {

	private Integer idStatusIssue;
	
	private Integer idSector;
	
	private Integer idTypeStock;
	
	private Integer idStatusQuick;
	
	private Integer idStatusTrading;
	
	private Integer idIndex;
	
	private Boolean isInvest;
	
	private Integer fairValuePriceOverPercentage;
}
