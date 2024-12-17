package project.stock.track.app.beans.pojos.business.issues;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class IssueManagerResumePojo {
	
	private Integer idIssue;

	private String description;
	
	private String initials;
	
	private Long historicalStartDate;
	
	private Integer idSector;
	
	private Integer idTypeStock;
	
	private Integer idStatusIssue;
	
	private Integer idStatusIssueQuick;
	
	private Integer idStatusIssueTrading;
	
	private Integer idIndex;
	
	private String descriptionSector;
	
	private String descriptionTypeStock;
	
	private String descriptionStatusIssue;
	
	private String descriptionStatusIssueQuick;
	
	private String descriptionStatusIssueTrading;
	
	private String descriptionIndex;
	
	private Boolean isInvest;
}
