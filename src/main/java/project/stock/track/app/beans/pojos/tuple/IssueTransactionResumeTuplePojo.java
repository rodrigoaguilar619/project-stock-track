package project.stock.track.app.beans.pojos.tuple;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class IssueTransactionResumeTuplePojo {

	private Integer idBroker;
	
	private String descriptionBroker;
	
	private Integer idTypeCurrency;
	
	private String descriptionTypeCurrency;
	
	private BigDecimal totalShares;
	
	private BigDecimal priceBuy;
	
	private BigDecimal sumPriceBuy;
	
	private Long buyDate;
	
	private Long sellDate;
	
	private BigDecimal priceSell;
	
	private BigDecimal sumPriceSell;
	
	private BigDecimal sellGainLossPercentage;
	
	private BigDecimal sellGainLossTotal;
}
