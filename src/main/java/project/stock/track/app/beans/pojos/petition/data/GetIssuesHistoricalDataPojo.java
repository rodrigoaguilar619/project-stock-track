package project.stock.track.app.beans.pojos.petition.data;

import java.util.List;

import project.stock.track.app.beans.pojos.entity.IssueHistoricalEntityPojo;

public class GetIssuesHistoricalDataPojo {
	
	List<IssueHistoricalEntityPojo> issuesHistorical;
	
	Long totalIssues;

	public List<IssueHistoricalEntityPojo> getIssuesHistorical() {
		return issuesHistorical;
	}

	public void setIssuesHistorical(List<IssueHistoricalEntityPojo> issuesHistorical) {
		this.issuesHistorical = issuesHistorical;
	}

	public Long getTotalIssues() {
		return totalIssues;
	}

	public void setTotalIssues(Long totalIssues) {
		this.totalIssues = totalIssues;
	}

}
