package project.stock.track.app.beans.pojos.tuple;

import java.math.BigDecimal;

public class IssueTransactionsByDateTuplePojo {

	private Long date;
	
	private Long totalShares;
	
	private BigDecimal price;

	public IssueTransactionsByDateTuplePojo(Long date, Long totalShares, BigDecimal price) {
		super();
		this.date = date;
		this.totalShares = totalShares;
		this.price = price;
	}

	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
	}

	public Long getTotalShares() {
		return totalShares;
	}

	public void setTotalShares(Long totalShares) {
		this.totalShares = totalShares;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
}
