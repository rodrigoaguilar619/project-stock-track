package project.stock.track.app.beans.pojos.business.transaction;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TransactionIssuePojo {
	
	private int id;
	
	private Integer idIssue;
	
	private String issue;
	
	private Long buyDate;
	
	private BigDecimal priceBuy;
	
	private BigDecimal commisionPercentage;
	
	private BigDecimal priceTotalBuy;
	
	private BigDecimal priceSell;
	
	private BigDecimal priceTotalSell;
	
	private BigDecimal sellGainLossPercentage;
	
	private BigDecimal sellGainLossTotal;
	
	private BigDecimal sellCommisionPercentage;
	
	private BigDecimal sellTaxesPercentage;
	
	private Long sellDate;
	
	private Integer typeCurrency;
	
	private String descriptionTypeCurrency;
}
