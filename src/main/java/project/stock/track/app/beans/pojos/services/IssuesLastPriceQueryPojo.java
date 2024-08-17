package project.stock.track.app.beans.pojos.services;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class IssuesLastPriceQueryPojo {
	
	private List<String> issues = new ArrayList<>();
	
	private Boolean isStandardPoors;
}
