package project.stock.track.app.beans.pojos.petition.data;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import project.stock.track.app.beans.pojos.business.issues.IssueHistoricalResultPojo;

@Getter @Setter
public class UpdateIssuesHistoricalDataPojo {

	private List<IssueHistoricalResultPojo> issueHistoricalResult = new ArrayList<>();
}
