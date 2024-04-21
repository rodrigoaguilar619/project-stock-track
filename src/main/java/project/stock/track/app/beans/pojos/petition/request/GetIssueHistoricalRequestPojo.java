package project.stock.track.app.beans.pojos.petition.request;

import lib.base.backend.pojo.rest.security.UserRequestPojo;

public class GetIssueHistoricalRequestPojo extends UserRequestPojo {

	private Integer idIssue;

	public Integer getIdIssue() {
		return idIssue;
	}

	public void setIdIssue(Integer idIssue) {
		this.idIssue = idIssue;
	}
}
