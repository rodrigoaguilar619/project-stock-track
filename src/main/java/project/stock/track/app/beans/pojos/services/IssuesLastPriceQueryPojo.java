package project.stock.track.app.beans.pojos.services;

import java.util.List;

public class IssuesLastPriceQueryPojo {
	
	private List<String> issues;
	
	private Boolean isStandardPoors;

	public List<String> getIssues() {
		return issues;
	}

	public void setIssues(List<String> issues) {
		this.issues = issues;
	}

	public Boolean getIsStandardPoors() {
		return isStandardPoors;
	}

	public void setIsStandardPoors(Boolean isStandardPoors) {
		this.isStandardPoors = isStandardPoors;
	}
}
