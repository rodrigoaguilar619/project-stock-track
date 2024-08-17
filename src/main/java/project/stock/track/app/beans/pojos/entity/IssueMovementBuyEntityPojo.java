package project.stock.track.app.beans.pojos.entity;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class IssueMovementBuyEntityPojo {

	private Integer buyTransactionNumber;
	
	private BigDecimal buyPrice;
	
	private Long buyDate;
	
	private BigDecimal sellPrice;
	
	private Long sellDate;
	
	private BigDecimal totalShares;
}
