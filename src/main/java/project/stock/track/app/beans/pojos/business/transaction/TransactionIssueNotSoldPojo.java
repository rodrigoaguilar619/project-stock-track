package project.stock.track.app.beans.pojos.business.transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TransactionIssueNotSoldPojo {

	private Integer idIssue;
	
	private String issue;
	
	private LocalDateTime idDate;
	
	private Integer idBroker;
	
	private BigDecimal priceTotalBuy;
	
	private BigDecimal priceBuy;
	
	private BigDecimal commisionPercentage;
	
	private Long totalTitles;
}
