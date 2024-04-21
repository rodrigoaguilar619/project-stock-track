package project.stock.track.app.beans.rest.exchangetrade.service;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class IssueIexMainBean {
	
	private String ticker;

	private BigDecimal last;
	
	private BigDecimal open;
	
	private BigDecimal tngoLast;
	
	private BigDecimal volume;
	
	private BigDecimal prevClose;
	
	private BigDecimal high;
	
	private Date timestamp;
	
	private Date lastSaleTimestamp;

	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	public BigDecimal getLast() {
		return last;
	}

	public void setLast(BigDecimal last) {
		this.last = last;
	}

	public BigDecimal getOpen() {
		return open;
	}

	public void setOpen(BigDecimal open) {
		this.open = open;
	}

	public BigDecimal getTngoLast() {
		return tngoLast;
	}

	public void setTngoLast(BigDecimal tngoLast) {
		this.tngoLast = tngoLast;
	}

	public BigDecimal getVolume() {
		return volume;
	}

	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}

	public BigDecimal getPrevClose() {
		return prevClose;
	}

	public void setPrevClose(BigDecimal prevClose) {
		this.prevClose = prevClose;
	}

	public BigDecimal getHigh() {
		return high;
	}

	public void setHigh(BigDecimal high) {
		this.high = high;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public Date getLastSaleTimestamp() {
		return lastSaleTimestamp;
	}

	public void setLastSaleTimestamp(Date lastSaleTimestamp) {
		this.lastSaleTimestamp = lastSaleTimestamp;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
