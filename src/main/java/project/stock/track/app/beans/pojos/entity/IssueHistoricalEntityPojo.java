package project.stock.track.app.beans.pojos.entity;

import java.util.ArrayList;
import java.util.List;

import project.stock.track.app.beans.pojos.business.issues.IssueHistoricalTrackResumenPojo;
import project.stock.track.app.beans.pojos.tuple.IssueTransactionsByDateTuplePojo;

public class IssueHistoricalEntityPojo {
	
	private CatalogIssuesEntityDesPojo issueData;
	
	private IssueHistoricalTrackResumenPojo issueTrackProperties;
	
	private List<IssueTransactionsByDateTuplePojo> issueTransactionBuys = new ArrayList<>();
	
	private List<IssueHistoricalDayEntityPojo> issueHistorical;

	public CatalogIssuesEntityDesPojo getIssueData() {
		return issueData;
	}

	public void setIssueData(CatalogIssuesEntityDesPojo issueData) {
		this.issueData = issueData;
	}

	public List<IssueHistoricalDayEntityPojo> getIssueHistorical() {
		return issueHistorical;
	}

	public void setIssueHistorical(List<IssueHistoricalDayEntityPojo> issueHistorical) {
		this.issueHistorical = issueHistorical;
	}

	public IssueHistoricalTrackResumenPojo getIssueTrackProperties() {
		return issueTrackProperties;
	}

	public void setIssueTrackProperties(IssueHistoricalTrackResumenPojo issueTrackProperties) {
		this.issueTrackProperties = issueTrackProperties;
	}

	public List<IssueTransactionsByDateTuplePojo> getIssueTransactionBuys() {
		return issueTransactionBuys;
	}

	public void setIssueTransactionBuys(List<IssueTransactionsByDateTuplePojo> issueTransactionBuys) {
		this.issueTransactionBuys = issueTransactionBuys;
	}

}
