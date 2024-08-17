package project.stock.track.app.beans.rest.exchangetrade.service.alphavantage;

import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class IssueHistoricAlphaVantageBean {

	private String name;
	
	private String uri;
	
	@JsonProperty("Time Series (Daily)")
	private Map<String, ShareHistoryDayAlphaVantageBean> history = new LinkedHashMap<>();
}
