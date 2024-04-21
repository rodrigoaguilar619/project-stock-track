package project.stock.track.app.beans.pojos.petition.request;

import lib.base.backend.pojo.rest.security.UserRequestPojo;
import project.stock.track.app.beans.pojos.entity.CatalogIssuesEntityPojo;

public class UpdateIssueRequestPojo extends UserRequestPojo {
	
	private CatalogIssuesEntityPojo issueData = new CatalogIssuesEntityPojo();
	
	public CatalogIssuesEntityPojo getIssueData() {
		return issueData;
	}
	
	public void setIssueData(CatalogIssuesEntityPojo issueData) {
		this.issueData = issueData;
	}
}
