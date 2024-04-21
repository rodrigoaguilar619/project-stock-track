package project.stock.track.app.beans.rest.exchangetrade.service.alphavantage;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ShareHistoryDayAlphaVantageBean {
	
	@JsonProperty("date")
	private String date;

	@JsonProperty("open")
	private String open;
	
	@JsonProperty("close")
	private String close;
	
	@JsonProperty("high")
	private String high;
	
	@JsonProperty("low")
	private String low;
	
	@JsonProperty("volume")
	private String volume;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getOpen() {
		return open;
	}

	public void setOpen(String open) {
		this.open = open;
	}

	public String getClose() {
		return close;
	}

	public void setClose(String close) {
		this.close = close;
	}

	public String getHigh() {
		return high;
	}

	public void setHigh(String high) {
		this.high = high;
	}

	public String getLow() {
		return low;
	}

	public void setLow(String low) {
		this.low = low;
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}
	
	
}
