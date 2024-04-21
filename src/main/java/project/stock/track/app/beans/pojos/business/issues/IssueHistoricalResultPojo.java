package project.stock.track.app.beans.pojos.business.issues;

import java.util.List;

public class IssueHistoricalResultPojo {

	private String issue;
	
	private List<IssueHistoricalDateResultPojo> issueDateHistoricalResultPojos;

	public String getIssue() {
		return issue;
	}

	public void setIssue(String issue) {
		this.issue = issue;
	}

	public List<IssueHistoricalDateResultPojo> getIssueDateHistoricalResultPojos() {
		return issueDateHistoricalResultPojos;
	}

	public void setIssueDateHistoricalResultPojos(List<IssueHistoricalDateResultPojo> issueDateHistoricalResultPojos) {
		this.issueDateHistoricalResultPojos = issueDateHistoricalResultPojos;
	}
}
