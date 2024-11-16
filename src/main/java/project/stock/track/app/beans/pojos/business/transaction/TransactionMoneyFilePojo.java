package project.stock.track.app.beans.pojos.business.transaction;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TransactionMoneyFilePojo {
	
	private String issue;
	
	private Long date;
	
	private String typeTransaction;
	
	private Integer typeCurrency;
	
	private BigDecimal amount;
	
	private int broker;
	
	private String information;
}
