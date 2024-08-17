package project.stock.track.app.beans.pojos.petition.data;

import lombok.Getter;
import lombok.Setter;
import project.stock.track.app.beans.pojos.business.issues.IssueManagerTrackResumePojo;
import project.stock.track.app.beans.pojos.entity.CatalogIssuesEntityPojo;

@Getter @Setter
public class GetIssueManagerDataPojo {

	private IssueManagerTrackResumePojo issueManagerTrack;
	
	private CatalogIssuesEntityPojo issue;
}
