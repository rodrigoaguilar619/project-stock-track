package project.stock.track.app.beans.pojos.entity;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import project.stock.track.app.beans.pojos.business.issues.IssueHistoricalTrackResumenPojo;
import project.stock.track.app.beans.pojos.tuple.IssueTransactionsByDateTuplePojo;

@Getter @Setter
public class IssueHistoricalEntityPojo {
	
	private CatalogIssuesEntityDesPojo issueData;
	
	private IssueHistoricalTrackResumenPojo issueTrackProperties;
	
	private List<IssueTransactionsByDateTuplePojo> issueTransactionBuys = new ArrayList<>();
	
	private List<IssueHistoricalDayEntityPojo> issueHistorical = new ArrayList<>();
}
