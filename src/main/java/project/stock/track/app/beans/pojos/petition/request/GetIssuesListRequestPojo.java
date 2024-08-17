package project.stock.track.app.beans.pojos.petition.request;

import lib.base.backend.pojo.rest.security.UserRequestPojo;
import lombok.Getter;
import lombok.Setter;
import project.stock.track.app.beans.pojos.business.issues.IssuesFiltersPojo;

@Getter @Setter
public class GetIssuesListRequestPojo extends UserRequestPojo {

	private IssuesFiltersPojo filters = new IssuesFiltersPojo();
}
