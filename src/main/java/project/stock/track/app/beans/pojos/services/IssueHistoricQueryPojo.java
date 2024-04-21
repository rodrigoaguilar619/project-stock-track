package project.stock.track.app.beans.pojos.services;

import java.util.Date;

public class IssueHistoricQueryPojo {

	private String issue;
	
	private Date dateFrom;
	
	private Date dateTo;
	
	private Boolean isStandarPoors;

	public String getIssue() {
		return issue;
	}

	public void setIssue(String issue) {
		this.issue = issue;
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public Boolean getIsStandarPoors() {
		return isStandarPoors;
	}

	public void setIsStandarPoors(Boolean isStandarPoors) {
		this.isStandarPoors = isStandarPoors;
	}
	
}
