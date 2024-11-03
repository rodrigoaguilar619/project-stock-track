package project.stock.track.app.beans.pojos.business.transaction;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CurrencyValuesPojo {

	private BigDecimal valueMxn;
	
	private BigDecimal valueUsd;
}
