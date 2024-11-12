package project.stock.track.app.beans.pojos.business.portfolio;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PorfolioIssuePojo {

	private Integer idIssue;
	
	private String initials;
	
	private Integer idTypeCurrency;
	
	private BigDecimal titles;
	
	private BigDecimal costAvgBuyPerTitle;
	
	private BigDecimal costAvgSellPerTitle;
	
	private BigDecimal costTotalBuy;
	
	private BigDecimal costTotalSell;
	
	private BigDecimal totalGainLoss;
	
	private BigDecimal totalYield;
	
	private Long lastUpdate;
}
