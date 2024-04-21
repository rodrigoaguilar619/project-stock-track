package project.stock.track.app.beans.pojos.entity;

import java.math.BigDecimal;

public class IssueHistoricalDayEntityPojo {
	
	private BigDecimal open;
	
	private BigDecimal close;
	
	private Long date;

	public BigDecimal getOpen() {
		return open;
	}

	public void setOpen(BigDecimal open) {
		this.open = open;
	}

	public BigDecimal getClose() {
		return close;
	}

	public void setClose(BigDecimal close) {
		this.close = close;
	}

	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
	}
	
}
