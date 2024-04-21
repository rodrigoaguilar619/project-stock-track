package project.stock.track.app.beans.pojos.petition.data;

import java.util.List;

import project.stock.track.app.beans.pojos.business.issues.IssueCalculateResumePojo;
import project.stock.track.app.beans.pojos.business.transaction.TransactionIssueCalculatePojo;
import project.stock.track.app.beans.pojos.entity.IssueHistoricalEntityPojo;

public class GetIssueHistoricalDataPojo {

	private IssueHistoricalEntityPojo issueHistoricalData;
	
	private IssueCalculateResumePojo issueCalculateResume;
	
	List<TransactionIssueCalculatePojo> issueTransactions = new java.util.ArrayList<>();

	public IssueHistoricalEntityPojo getIssueHistoricalData() {
		return issueHistoricalData;
	}

	public void setIssueHistoricalData(IssueHistoricalEntityPojo issueHistoricalData) {
		this.issueHistoricalData = issueHistoricalData;
	}

	public IssueCalculateResumePojo getIssueCalculateResume() {
		return issueCalculateResume;
	}

	public void setIssueCalculateResume(IssueCalculateResumePojo issueCalculateResume) {
		this.issueCalculateResume = issueCalculateResume;
	}

	public List<TransactionIssueCalculatePojo> getIssueTransactions() {
		return issueTransactions;
	}

	public void setIssueTransactions(List<TransactionIssueCalculatePojo> issueTransactions) {
		this.issueTransactions = issueTransactions;
	}
	
	
}
