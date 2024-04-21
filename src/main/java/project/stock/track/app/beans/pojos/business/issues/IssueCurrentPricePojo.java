package project.stock.track.app.beans.pojos.business.issues;

import java.math.BigDecimal;

public class IssueCurrentPricePojo {

	private BigDecimal currentPrice;
	
	private Long date;
	
	public Long getDate() {
		return date;
	}
	
	public void setDate(Long date) {
		this.date = date;
	}
	
	public BigDecimal getCurrentPrice() {
		return currentPrice;
	}
	
	public void setCurrentPrice(BigDecimal currentPrice) {
		this.currentPrice = currentPrice;
	}
}
