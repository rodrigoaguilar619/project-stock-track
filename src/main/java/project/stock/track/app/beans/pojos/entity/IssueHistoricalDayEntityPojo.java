package project.stock.track.app.beans.pojos.entity;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class IssueHistoricalDayEntityPojo {
	
	private BigDecimal open;
	
	private BigDecimal close;
	
	private Long date;
}
