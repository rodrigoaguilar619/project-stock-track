package project.stock.track.app.beans.pojos.tuple;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class IssueTransactionsByDateTuplePojo {

	private Long date;
	
	private BigDecimal totalShares;
	
	private BigDecimal price;
	
	private String brokerDescription;

	public IssueTransactionsByDateTuplePojo(Long date, BigDecimal totalShares, BigDecimal price, String brokerDescription) {
		super();
		this.date = date;
		this.totalShares = totalShares;
		this.price = price;
		this.brokerDescription = brokerDescription;
	}
}
