package project.stock.track.app.beans.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@EqualsAndHashCode
@Embeddable
public class IssuesMovementsBuyEntityPk implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Column(name="buy_transaction_number")
	private int buyTransactionNumber;

	@Column(name="id_issue_movement")
	private int idIssueMovement;
	
}