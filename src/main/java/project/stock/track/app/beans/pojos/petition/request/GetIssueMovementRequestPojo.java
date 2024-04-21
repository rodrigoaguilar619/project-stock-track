package project.stock.track.app.beans.pojos.petition.request;

import lib.base.backend.pojo.rest.security.UserRequestPojo;

public class GetIssueMovementRequestPojo extends UserRequestPojo {

	private Integer idIssueMovement;

	public Integer getIdIssueMovement() {
		return idIssueMovement;
	}

	public void setIdIssueMovement(Integer idIssueMovement) {
		this.idIssueMovement = idIssueMovement;
	}
}
