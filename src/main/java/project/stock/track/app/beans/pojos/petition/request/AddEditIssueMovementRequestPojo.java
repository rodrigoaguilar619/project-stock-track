package project.stock.track.app.beans.pojos.petition.request;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lib.base.backend.pojo.rest.security.UserRequestPojo;
import lombok.Getter;
import lombok.Setter;
import project.stock.track.app.beans.pojos.entity.IssueMovementBuyEntityPojo;

@Getter @Setter
public class AddEditIssueMovementRequestPojo extends UserRequestPojo {

	private Integer idIssueMovement;
	
	private Integer idTypeCurrency;
	
	private String issue;
	
	private Integer idBroker;
	
	private Integer idStatus;
	
	private BigDecimal priceMovement;
	
	private List<IssueMovementBuyEntityPojo> issueMovementBuysList = new ArrayList<>();
}
