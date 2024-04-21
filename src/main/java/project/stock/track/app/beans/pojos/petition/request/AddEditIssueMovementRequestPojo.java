package project.stock.track.app.beans.pojos.petition.request;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lib.base.backend.pojo.rest.security.UserRequestPojo;
import project.stock.track.app.beans.pojos.entity.IssueMovementBuyEntityPojo;

public class AddEditIssueMovementRequestPojo extends UserRequestPojo {

	private Integer idIssueMovement;
	
	private String issue;
	
	private Integer idBroker;
	
	private Integer idStatus;
	
	private BigDecimal priceMovement;
	
	private List<IssueMovementBuyEntityPojo> issueMovementBuysList = new ArrayList<>();

	public Integer getIdIssueMovement() {
		return idIssueMovement;
	}

	public void setIdIssueMovement(Integer idIssueMovement) {
		this.idIssueMovement = idIssueMovement;
	}

	public String getIssue() {
		return issue;
	}

	public void setIssue(String issue) {
		this.issue = issue;
	}

	public Integer getIdBroker() {
		return idBroker;
	}

	public void setIdBroker(Integer idBroker) {
		this.idBroker = idBroker;
	}

	public Integer getIdStatus() {
		return idStatus;
	}

	public void setIdStatus(Integer idStatus) {
		this.idStatus = idStatus;
	}

	public BigDecimal getPriceMovement() {
		return priceMovement;
	}

	public void setPriceMovement(BigDecimal priceMovement) {
		this.priceMovement = priceMovement;
	}

	public List<IssueMovementBuyEntityPojo> getIssueMovementBuysList() {
		return issueMovementBuysList;
	}

	public void setIssueMovementBuysList(List<IssueMovementBuyEntityPojo> issueMovementBuysList) {
		this.issueMovementBuysList = issueMovementBuysList;
	}
}
