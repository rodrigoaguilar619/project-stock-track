package project.stock.track.app.beans.pojos.petition.data;

import java.util.ArrayList;
import java.util.List;

import project.stock.track.app.beans.pojos.business.issues.IssueMovementResumePojo;

public class GetIssuesMovementsListDataPojo {

	private List<IssueMovementResumePojo> issuesMovementsList = new ArrayList<>();

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
}
