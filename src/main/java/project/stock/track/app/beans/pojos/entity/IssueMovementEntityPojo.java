package project.stock.track.app.beans.pojos.entity;

import java.math.BigDecimal;
import java.util.List;

public class IssueMovementEntityPojo {

	private Integer idIssueMovement;
	
	private Integer idIssue;
	
	private Integer idBroker;
	
	private Integer idStatus;
	
	private BigDecimal priceMovement;
	
	private List<IssueMovementBuyEntityPojo> issueMovementBuysList;

	public Integer getIdIssueMovement() {
		return idIssueMovement;
	}

	public void setIdIssueMovement(Integer idIssueMovement) {
		this.idIssueMovement = idIssueMovement;
	}

	public Integer getIdIssue() {
		return idIssue;
	}

	public void setIdIssue(Integer idIssue) {
		this.idIssue = idIssue;
	}

	public Integer getIdBroker() {
		return idBroker;
	}

	public void setIdBroker(Integer idBroker) {
		this.idBroker = idBroker;
	}

	public BigDecimal getPriceMovement() {
		return priceMovement;
	}

	public void setPriceMovement(BigDecimal priceMovement) {
		this.priceMovement = priceMovement;
	}

	public Integer getIdStatus() {
		return idStatus;
	}

	public void setIdStatus(Integer idStatus) {
		this.idStatus = idStatus;
	}

	public List<IssueMovementBuyEntityPojo> getIssueMovementBuysList() {
		return issueMovementBuysList;
	}

	public void setIssueMovementBuysList(List<IssueMovementBuyEntityPojo> issueMovementBuysList) {
		this.issueMovementBuysList = issueMovementBuysList;
	}
}
