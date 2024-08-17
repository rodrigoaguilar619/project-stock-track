package project.stock.track.app.beans.pojos.entity;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class IssueLastPriceTmpEntityPojo {

	private String issue;
	
	private Integer idIssue;
	
	private BigDecimal currentPrice;
	
	private BigDecimal open;
	
	private BigDecimal volume;
	
	private BigDecimal previousPriceClose;
	
	private BigDecimal highPrice;
	
	private Long datePrice;
	
	private Long currentDatePrice;
}
