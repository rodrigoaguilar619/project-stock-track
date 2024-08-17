package project.stock.track.app.beans.pojos.petition.data;

import java.util.LinkedHashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import project.stock.track.app.beans.pojos.entity.IssueLastPriceTmpEntityPojo;

@Getter @Setter
public class GetTempIssuesLastPriceDataPojo {
	
	private Map<String, IssueLastPriceTmpEntityPojo> tempIssueLastPricesMap = new LinkedHashMap<>();
}
