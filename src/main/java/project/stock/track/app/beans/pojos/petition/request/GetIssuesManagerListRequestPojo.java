package project.stock.track.app.beans.pojos.petition.request;

import lib.base.backend.pojo.rest.security.UserRequestPojo;
import lombok.Getter;
import lombok.Setter;
import project.stock.track.app.beans.pojos.business.issues.IssuesManagerFiltersPojo;

@Getter @Setter
public class GetIssuesManagerListRequestPojo extends UserRequestPojo {

	private IssuesManagerFiltersPojo filters = new IssuesManagerFiltersPojo();
}
