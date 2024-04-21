package project.stock.track.app.beans.rest.exchangetrade.service.alphavantage;

import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IssueHistoricAlphaVantageBean {

	private String name;
	
	private String uri;
	
	@JsonProperty("Time Series (Daily)")
	private Map<String, ShareHistoryDayAlphaVantageBean> history = new LinkedHashMap<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public Map<String, ShareHistoryDayAlphaVantageBean> getHistory() {
		return history;
	}

	public void setHistory(Map<String, ShareHistoryDayAlphaVantageBean> history) {
		this.history = history;
	}
	
}
