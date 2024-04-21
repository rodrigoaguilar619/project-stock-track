package project.stock.track.app.beans.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the issues_movements_buy database table.
 * 
 */
@Embeddable
public class IssuesMovementsBuyEntityPk implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Column(name="buy_transaction_number")
	private int buyTransactionNumber;

	@Column(name="id_issue_movement")
	private int idIssueMovement;

	public int getBuyTransactionNumber() {
		return this.buyTransactionNumber;
	}
	public void setBuyTransactionNumber(int buyTransactionNumber) {
		this.buyTransactionNumber = buyTransactionNumber;
	}

	public int getIdIssueMovement() {
		return idIssueMovement;
	}
	public void setIdIssueMovement(int idIssueMovement) {
		this.idIssueMovement = idIssueMovement;
	}
	
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof IssuesMovementsBuyEntityPk)) {
			return false;
		}
		IssuesMovementsBuyEntityPk castOther = (IssuesMovementsBuyEntityPk)other;
		return 
			(this.buyTransactionNumber == castOther.buyTransactionNumber)
			&& (this.idIssueMovement == castOther.idIssueMovement);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.buyTransactionNumber;
		hash = hash * prime + this.idIssueMovement;
		
		return hash;
	}
}