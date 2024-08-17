package project.stock.track.app.beans.pojos.petition.data;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import project.stock.track.app.beans.pojos.business.issues.IssueMovementResumePojo;
import project.stock.track.app.beans.pojos.business.issues.IssueMovementTransactionTotalResumePojo;

@Getter @Setter
public class GetIssuesMovementsListDataPojo {

	private List<IssueMovementResumePojo> issuesMovementsList = new ArrayList<>();
	
	private IssueMovementTransactionTotalResumePojo issueMovementTransactionTotalNotSold;
	
	private IssueMovementTransactionTotalResumePojo issueMovementTransactionTotalSold;
	
	private IssueMovementTransactionTotalResumePojo issueMovementTransactionTotal;

	private Long totalIssuesMovements;
}
