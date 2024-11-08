package project.stock.track.app.beans.pojos.tuple;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class IssueTransactionsByDateTuplePojo {

	private Long dateBuy;
	
	private Long dateSell;
	
	private BigDecimal totalShares;
	
	private BigDecimal priceBuy;
	
	private BigDecimal priceTotalBuy;
	
	private BigDecimal priceSell;
	
	private BigDecimal priceTotalSell;
	
	private String brokerDescription;
	
	private String typeCurrencyDescription;

	public IssueTransactionsByDateTuplePojo(Long dateBuy, Long dateSell, BigDecimal totalShares, BigDecimal priceBuy, BigDecimal priceTotalBuy, BigDecimal priceSell, BigDecimal priceTotalSell, String brokerDescription, String typeCurrencyDescription) {
		super();
		this.dateBuy = dateBuy;
		this.dateSell = dateSell;
		this.totalShares = totalShares;
		this.priceBuy = priceBuy;
		this.priceTotalBuy = priceTotalBuy;
		this.priceSell = priceSell;
		this.priceTotalSell = priceTotalSell;
		this.brokerDescription = brokerDescription;
		this.typeCurrencyDescription = typeCurrencyDescription;
	}
}
