package project.stock.track.app.beans.rest.exchangetrade.service.tiingo;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CryptoTiingoBean {

	@JsonProperty("ticker")
	private String ticker;
	
	@JsonProperty("baseCurrency")
	private String baseCurrency;
	
	@JsonProperty("priceData")
	private List<ShareHistoryDayTiingoBean> history = new ArrayList<>();

	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	public String getBaseCurrency() {
		return baseCurrency;
	}

	public void setBaseCurrency(String baseCurrency) {
		this.baseCurrency = baseCurrency;
	}

	public List<ShareHistoryDayTiingoBean> getHistory() {
		return history;
	}

	public void setHistory(List<ShareHistoryDayTiingoBean> history) {
		this.history = history;
	}
	
}
