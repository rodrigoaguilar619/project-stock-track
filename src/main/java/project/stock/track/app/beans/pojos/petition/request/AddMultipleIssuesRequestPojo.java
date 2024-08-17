package project.stock.track.app.beans.pojos.petition.request;

import java.util.ArrayList;
import java.util.List;

import lib.base.backend.pojo.rest.security.UserRequestPojo;
import lombok.Getter;
import lombok.Setter;
import project.stock.track.app.beans.pojos.business.issues.IssuesElementMultiplePojo;

@Getter @Setter
public class AddMultipleIssuesRequestPojo extends UserRequestPojo {
	
	private Long historicalStartDate;
	
	private Integer idTypeStock;
	
	private Integer idStatusIssue;
	
	private List<IssuesElementMultiplePojo> issues = new ArrayList<>();
}
