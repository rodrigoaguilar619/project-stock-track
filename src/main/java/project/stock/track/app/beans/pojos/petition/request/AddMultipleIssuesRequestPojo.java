package project.stock.track.app.beans.pojos.petition.request;

import java.util.ArrayList;
import java.util.List;

import lib.base.backend.pojo.rest.security.UserRequestPojo;
import project.stock.track.app.beans.pojos.business.issues.IssuesElementMultiplePojo;

public class AddMultipleIssuesRequestPojo extends UserRequestPojo {
	
	private Long historicalStartDate;
	
	private Integer idTypeStock;
	
	private Integer idStatusIssue;
	
	private List<IssuesElementMultiplePojo> issues = new ArrayList<>();

	public Long getHistoricalStartDate() {
		return historicalStartDate;
	}

	public void setHistoricalStartDate(Long historicalStartDate) {
		this.historicalStartDate = historicalStartDate;
	}

	public Integer getIdTypeStock() {
		return idTypeStock;
	}

	public void setIdTypeStock(Integer idTypeStock) {
		this.idTypeStock = idTypeStock;
	}

	public Integer getIdStatusIssue() {
		return idStatusIssue;
	}

	public void setIdStatusIssue(Integer idStatusIssue) {
		this.idStatusIssue = idStatusIssue;
	}

	public List<IssuesElementMultiplePojo> getIssues() {
		return issues;
	}

	public void setIssues(List<IssuesElementMultiplePojo> issues) {
		this.issues = issues;
	}

}
