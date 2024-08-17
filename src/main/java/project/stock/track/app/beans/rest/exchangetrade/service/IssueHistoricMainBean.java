package project.stock.track.app.beans.rest.exchangetrade.service;

import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import project.stock.track.app.beans.rest.exchangetrade.IssueHistoryDayBean;

@Getter @Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class IssueHistoricMainBean {
	
	private String name;
	
	private String uri;
	
	private Map<String, IssueHistoryDayBean> history = new LinkedHashMap<>();
}
