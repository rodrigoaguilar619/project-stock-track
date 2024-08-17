package project.stock.track.app.beans.pojos.petition.data;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import project.stock.track.app.beans.pojos.business.issues.IssueCalculateResumePojo;
import project.stock.track.app.beans.pojos.business.transaction.TransactionIssueCalculatePojo;
import project.stock.track.app.beans.pojos.entity.IssueHistoricalEntityPojo;

@Getter @Setter
public class GetIssueHistoricalDataPojo {

	private IssueHistoricalEntityPojo issueHistoricalData;
	
	private IssueCalculateResumePojo issueCalculateResume;
	
	private List<TransactionIssueCalculatePojo> issueTransactions = new ArrayList<>();
}
