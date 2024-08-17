package project.stock.track.app.beans.pojos.petition.data;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import project.stock.track.app.beans.pojos.entity.IssueHistoricalEntityPojo;

@Getter @Setter
public class GetIssuesHistoricalDataPojo {
	
	private List<IssueHistoricalEntityPojo> issuesHistorical = new ArrayList<>();
	
	private Long totalIssues;
}
