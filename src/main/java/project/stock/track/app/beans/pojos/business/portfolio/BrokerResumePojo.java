package project.stock.track.app.beans.pojos.business.portfolio;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BrokerResumePojo {
	
	private Integer idBroker;
	
	private String broker;

	private BigDecimal totalDeposits;
	
	private BigDecimal totalSecuritiesValue;
	
	private BigDecimal totalSecuritiesValueMxn;
	
	private BigDecimal totalYield;
	
	private Integer idTypeCurrency;
}
