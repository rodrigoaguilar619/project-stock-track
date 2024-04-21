package project.stock.track.app.beans.pojos.petition.data;

import project.stock.track.app.beans.pojos.business.issues.IssueManagerTrackResumePojo;
import project.stock.track.app.beans.pojos.entity.CatalogIssuesEntityPojo;

public class GetIssueManagerDataPojo {

	private IssueManagerTrackResumePojo issueManagerTrack;
	
	private CatalogIssuesEntityPojo issue;

	public IssueManagerTrackResumePojo getIssueManagerTrack() {
		return issueManagerTrack;
	}

	public void setIssueManagerTrack(IssueManagerTrackResumePojo issueManagerTrack) {
		this.issueManagerTrack = issueManagerTrack;
	}

	public CatalogIssuesEntityPojo getIssue() {
		return issue;
	}

	public void setIssue(CatalogIssuesEntityPojo issue) {
		this.issue = issue;
	}
}
