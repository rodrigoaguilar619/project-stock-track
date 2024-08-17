package project.stock.track.app.beans.pojos.business.issues;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class IssueHistoricalResultPojo {

	private String issue;
	
	private List<IssueHistoricalDateResultPojo> issueDateHistoricalResultPojos = new ArrayList<>();
}
