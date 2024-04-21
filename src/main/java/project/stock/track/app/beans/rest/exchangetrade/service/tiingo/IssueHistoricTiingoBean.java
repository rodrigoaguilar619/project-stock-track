package project.stock.track.app.beans.rest.exchangetrade.service.tiingo;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IssueHistoricTiingoBean {

	private String name;
	
	private String uri;
	
	@JsonFormat(shape = JsonFormat.Shape.ARRAY)
	private List<ShareHistoryDayTiingoBean> history = new ArrayList<>();

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

	public List<ShareHistoryDayTiingoBean> getHistory() {
		return history;
	}

	public void setHistory(List<ShareHistoryDayTiingoBean> history) {
		this.history = history;
	}
	
}
