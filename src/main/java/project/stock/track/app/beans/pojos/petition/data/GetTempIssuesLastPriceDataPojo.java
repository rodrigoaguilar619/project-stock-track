package project.stock.track.app.beans.pojos.petition.data;

import java.util.LinkedHashMap;
import java.util.Map;

import project.stock.track.app.beans.pojos.entity.IssueLastPriceTmpEntityPojo;

public class GetTempIssuesLastPriceDataPojo {
	
	private Map<String, IssueLastPriceTmpEntityPojo> tempIssueLastPricesMap = new LinkedHashMap<>();

	public Map<String, IssueLastPriceTmpEntityPojo> getTempIssueLastPricesMap() {
		return tempIssueLastPricesMap;
	}

	public void setTempIssueLastPricesMap(Map<String, IssueLastPriceTmpEntityPojo> tempIssueLastPricesMap) {
		this.tempIssueLastPricesMap = tempIssueLastPricesMap;
	}
}
