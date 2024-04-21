package project.stock.track.app.beans.pojos.petition.data;

import java.util.List;

import project.stock.track.app.beans.pojos.business.issues.IssueHistoricalResultPojo;

public class UpdateIssuesHistoricalDataPojo {

	private List<IssueHistoricalResultPojo> issueHistoricalResult;

	public List<IssueHistoricalResultPojo> getIssueHistoricalResult() {
		return issueHistoricalResult;
	}

	public void setIssueHistoricalResult(List<IssueHistoricalResultPojo> issueHistoricalResult) {
		this.issueHistoricalResult = issueHistoricalResult;
	}
	
}
