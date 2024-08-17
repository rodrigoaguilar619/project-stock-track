package project.stock.track.app.beans.pojos.business.issues;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class IssueHistoricalTrackResumenPojo {

	private BigDecimal buyPrice;

	private BigDecimal sellPrice;
	
	private BigDecimal fairValue;
	
	private BigDecimal currentPrice;
	
	private Long currentPriceDate;
	
	private BigDecimal previousClosePrice;
	
	private Long previousClosePriceDate;
}
