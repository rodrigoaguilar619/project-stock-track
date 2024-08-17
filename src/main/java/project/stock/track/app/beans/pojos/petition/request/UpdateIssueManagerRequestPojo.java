package project.stock.track.app.beans.pojos.petition.request;

import lib.base.backend.pojo.rest.security.UserRequestPojo;
import lombok.Getter;
import lombok.Setter;
import project.stock.track.app.beans.pojos.business.issues.UpdateIssueManagerPojo;

@Getter @Setter
public class UpdateIssueManagerRequestPojo extends UserRequestPojo {

	private UpdateIssueManagerPojo issueManagerData = new UpdateIssueManagerPojo();
}
