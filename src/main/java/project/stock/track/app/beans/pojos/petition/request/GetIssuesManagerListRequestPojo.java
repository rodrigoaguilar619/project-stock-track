package project.stock.track.app.beans.pojos.petition.request;

import lib.base.backend.pojo.rest.security.UserRequestPojo;
import project.stock.track.app.beans.pojos.business.issues.IssuesManagerFiltersPojo;

public class GetIssuesManagerListRequestPojo extends UserRequestPojo {

	private IssuesManagerFiltersPojo filters = new IssuesManagerFiltersPojo();

	public IssuesManagerFiltersPojo getFilters() {
		return filters;
	}

	public void setFilters(IssuesManagerFiltersPojo filters) {
		this.filters = filters;
	}
}
