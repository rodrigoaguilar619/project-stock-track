package project.stock.track.app.beans.pojos.business.portfolio;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PortfolioResumePojo {
	
	private Integer idBroker;
	
	private String broker;

	private BigDecimal totalDeposits;
	
	private BigDecimal totalSecuritiesValue;
	
	private BigDecimal totalSecuritiesValueMxn;
	
	private BigDecimal yield;
	
	private Integer idTypeCurrency;
	
	private String typeCurrency;
}
