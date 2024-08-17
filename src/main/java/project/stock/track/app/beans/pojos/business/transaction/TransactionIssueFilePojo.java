package project.stock.track.app.beans.pojos.business.transaction;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TransactionIssueFilePojo {
	
	private String issue;
	
	private Long date;
	
	private String typeTransaction;
	
	private double titles;
	
	private BigDecimal price;
	
	private BigDecimal comissionPercentage;
	
	private BigDecimal taxesPercentage;
	
	private Integer typeCurrency;
	
	private Boolean isSlice;
	
	private int broker;
}
