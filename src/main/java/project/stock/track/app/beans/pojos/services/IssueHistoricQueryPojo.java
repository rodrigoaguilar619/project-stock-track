package project.stock.track.app.beans.pojos.services;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class IssueHistoricQueryPojo {

	private String issue;
	
	private Date dateFrom;
	
	private Date dateTo;
	
	private Boolean isStandarPoors;
}
