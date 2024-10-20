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
	
	private BigDecimal priceSell;
	
	private String brokerDescription;

	public IssueTransactionsByDateTuplePojo(Long dateBuy, Long dateSell, BigDecimal totalShares, BigDecimal priceBuy, BigDecimal priceSell, String brokerDescription) {
		super();
		this.dateBuy = dateBuy;
		this.dateSell = dateSell;
		this.totalShares = totalShares;
		this.priceBuy = priceBuy;
		this.priceSell = priceSell;
		this.brokerDescription = brokerDescription;
	}
}
