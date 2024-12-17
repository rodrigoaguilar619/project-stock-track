package project.stock.track.app.beans.pojos.entity;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CatalogIssuesEntityPojo {

	private Integer idIssue;
	
	private String description;
	
	private String initials;
	
	private Long historicalStartDate;
	
	private Integer idSector;
	
	private Integer idTypeStock;
	
	private Integer idStatusIssue;
	
	private Integer idIndex;
}
