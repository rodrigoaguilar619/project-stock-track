package project.stock.track.app.beans.pojos.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class IssueMovementEntityPojo {

	private Integer idIssueMovement;
	
	private Integer idIssue;
	
	private Integer idBroker;
	
	private Integer idStatus;
	
	private BigDecimal priceMovement;
	
	private List<IssueMovementBuyEntityPojo> issueMovementBuysList = new ArrayList<>();
}
