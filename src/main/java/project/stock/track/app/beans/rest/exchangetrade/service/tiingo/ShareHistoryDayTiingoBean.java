package project.stock.track.app.beans.rest.exchangetrade.service.tiingo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShareHistoryDayTiingoBean {
	
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
}
