package project.stock.track.app.beans.pojos.petition.request;

import lib.base.backend.pojo.rest.security.UserRequestPojo;
import project.stock.track.app.beans.pojos.business.issues.IssuesFiltersPojo;

public class GetIssuesListRequestPojo extends UserRequestPojo {

	private IssuesFiltersPojo filters = new IssuesFiltersPojo();

	public IssuesFiltersPojo getFilters() {
		return filters;
	}

	public void setFilters(IssuesFiltersPojo filters) {
		this.filters = filters;
	}
}
