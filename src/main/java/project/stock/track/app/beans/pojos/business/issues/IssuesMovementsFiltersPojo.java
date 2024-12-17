package project.stock.track.app.beans.pojos.business.issues;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class IssuesMovementsFiltersPojo {

	private Integer idStatusIssueMovement;
	
	private Integer idSector;
	
	private Integer idBroker;
	
	private Integer idIndex;
	
	private Integer year;	
	
	private Boolean isSold;
}
