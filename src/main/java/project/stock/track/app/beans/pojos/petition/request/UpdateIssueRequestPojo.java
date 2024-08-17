package project.stock.track.app.beans.pojos.petition.request;

import lib.base.backend.pojo.rest.security.UserRequestPojo;
import lombok.Getter;
import lombok.Setter;
import project.stock.track.app.beans.pojos.entity.CatalogIssuesEntityPojo;

@Getter @Setter
public class UpdateIssueRequestPojo extends UserRequestPojo {
	
	private CatalogIssuesEntityPojo issueData = new CatalogIssuesEntityPojo();
}
