package project.stock.track.app.beans.pojos.petition.request;

import lib.base.backend.pojo.rest.security.UserRequestPojo;
import project.stock.track.app.beans.pojos.business.issues.UpdateIssueManagerPojo;

public class UpdateIssueManagerRequestPojo extends UserRequestPojo {

	private UpdateIssueManagerPojo issueManagerData = new UpdateIssueManagerPojo();

	public UpdateIssueManagerPojo getIssueManagerData() {
		return issueManagerData;
	}

	public void setIssueManagerData(UpdateIssueManagerPojo issueManagerData) {
		this.issueManagerData = issueManagerData;
	}
}
