package project.stock.track.app.beans.pojos.petition.data;

import java.util.ArrayList;
import java.util.List;

import project.stock.track.app.beans.pojos.business.issues.IssueMovementResumePojo;
import project.stock.track.app.beans.pojos.business.issues.IssueMovementTransactionTotalResumePojo;

public class GetIssuesMovementsListDataPojo {

	private List<IssueMovementResumePojo> issuesMovementsList = new ArrayList<>();
	
	private IssueMovementTransactionTotalResumePojo issueMovementTransactionTotalNotSold;
	
	private IssueMovementTransactionTotalResumePojo issueMovementTransactionTotalSold;
	
	private IssueMovementTransactionTotalResumePojo issueMovementTransactionTotal;

	private Long totalIssuesMovements;

	public List<IssueMovementResumePojo> getIssuesMovementsList() {
		return issuesMovementsList;
	}

	public void setIssuesMovementsList(List<IssueMovementResumePojo> issuesMovementsList) {
		this.issuesMovementsList = issuesMovementsList;
	}

	public Long getTotalIssuesMovements() {
		return totalIssuesMovements;
	}

	public void setTotalIssuesMovements(Long totalIssuesMovements) {
		this.totalIssuesMovements = totalIssuesMovements;
	}

	public IssueMovementTransactionTotalResumePojo getIssueMovementTransactionTotalNotSold() {
		return issueMovementTransactionTotalNotSold;
	}

	public void setIssueMovementTransactionTotalNotSold(IssueMovementTransactionTotalResumePojo issueMovementTransactionTotalNotSold) {
		this.issueMovementTransactionTotalNotSold = issueMovementTransactionTotalNotSold;
	}

	public IssueMovementTransactionTotalResumePojo getIssueMovementTransactionTotalSold() {
		return issueMovementTransactionTotalSold;
	}

	public void setIssueMovementTransactionTotalSold(IssueMovementTransactionTotalResumePojo issueMovementTransactionTotalSold) {
		this.issueMovementTransactionTotalSold = issueMovementTransactionTotalSold;
	}

	public IssueMovementTransactionTotalResumePojo getIssueMovementTransactionTotal() {
		return issueMovementTransactionTotal;
	}

	public void setIssueMovementTransactionTotal(IssueMovementTransactionTotalResumePojo issueMovementTransactionTotal) {
		this.issueMovementTransactionTotal = issueMovementTransactionTotal;
	}
}
