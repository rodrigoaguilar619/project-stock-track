package project.stock.track.app.beans.pojos.tuple;

import java.math.BigDecimal;

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

	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
	}

	public BigDecimal getTotalShares() {
		return totalShares;
	}

	public void setTotalShares(BigDecimal totalShares) {
		this.totalShares = totalShares;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getBrokerDescription() {
		return brokerDescription;
	}

	public void setBrokerDescription(String brokerDescription) {
		this.brokerDescription = brokerDescription;
	}
}
