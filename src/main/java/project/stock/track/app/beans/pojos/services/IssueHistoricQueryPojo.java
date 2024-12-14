package project.stock.track.app.beans.pojos.services;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class IssueHistoricQueryPojo {

	private String issue;
	
	private LocalDateTime dateFrom;
	
	private LocalDateTime dateTo;
	
	private Boolean isStandarPoors;
}
