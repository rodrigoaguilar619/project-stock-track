package project.stock.track.app.beans.rest.exchangetrade.service.tiingo;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CryptoTiingoBean {

	@JsonProperty("ticker")
	private String ticker;
	
	@JsonProperty("baseCurrency")
	private String baseCurrency;
	
	@JsonProperty("priceData")
	private List<ShareHistoryDayTiingoBean> history = new ArrayList<>();
}
