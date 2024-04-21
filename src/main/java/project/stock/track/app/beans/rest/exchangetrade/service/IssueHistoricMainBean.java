package project.stock.track.app.beans.rest.exchangetrade.service;

import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import project.stock.track.app.beans.rest.exchangetrade.IssueHistoryDayBean;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IssueHistoricMainBean {
	
	private String name;
	
	private String uri;
	
	private Map<String, IssueHistoryDayBean> history = new LinkedHashMap<>();

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

	public Map<String, IssueHistoryDayBean> getHistory() {
		return history;
	}

	public void setHistory(Map<String, IssueHistoryDayBean> history) {
		this.history = history;
	}

}
