package project.stock.track.app.beans.rest.exchangetrade.service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import org.apache.commons.lang3.builder.ToStringBuilder;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class IssueIexMainBean {
	
	private String ticker;

	private BigDecimal last;
	
	private BigDecimal open;
	
	private BigDecimal tngoLast;
	
	private BigDecimal volume;
	
	private BigDecimal prevClose;
	
	private BigDecimal high;
	
	private OffsetDateTime timestamp;
	
	private OffsetDateTime lastSaleTimestamp;
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
