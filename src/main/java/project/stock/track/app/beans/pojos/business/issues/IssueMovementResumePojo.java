package project.stock.track.app.beans.pojos.business.issues;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import project.stock.track.app.beans.pojos.entity.IssueMovementBuyEntityPojo;

@Getter @Setter
public class IssueMovementResumePojo {

	private Integer idIssueMovement;
	
	private Integer idIssue;
	
	private String issue;
	
	private Integer idStatus;
	
	private Integer idBroker;
	
	private Integer idSector;
	
	private String descriptionBroker;
	
	private String descriptionCurrency;
	
	private String descriptionStatus;
	
	private String descriptionSector;
	
	private BigDecimal currentPrice;
	
	private Long currentPriceDate;
	
	private String issuePerformance;
	
	private String alert;
	
	private String fairValue;
	
	private BigDecimal priceMovement;
	
	private List<IssueMovementBuyEntityPojo> issueMovementBuysList = new ArrayList<>();
	
	private IssueMovementTransactionPojo issueMovementTransactionNotSold;
	
	private IssueMovementTransactionPojo issueMovementTransactionSold;
}
