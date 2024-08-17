package project.stock.track.app.beans.pojos.business.issues;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class IssueCurrentPricePojo {

	private BigDecimal currentPrice;
	
	private Long date;
}
