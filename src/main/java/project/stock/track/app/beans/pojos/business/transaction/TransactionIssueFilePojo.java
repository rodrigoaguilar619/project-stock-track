package project.stock.track.app.beans.pojos.business.transaction;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TransactionIssueFilePojo implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String issue;
	
	private Long date;
	
	private String typeTransaction;
	
	private BigDecimal titles;
	
	private BigDecimal price;
	
	private BigDecimal comissionPercentage;
	
	private BigDecimal taxesPercentage;
	
	private Integer typeCurrency;
	
	private Boolean isSlice;
	
	private int broker;
}
