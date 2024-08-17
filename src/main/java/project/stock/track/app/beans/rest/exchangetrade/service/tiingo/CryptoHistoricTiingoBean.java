package project.stock.track.app.beans.rest.exchangetrade.service.tiingo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CryptoHistoricTiingoBean {

	private String name;
	
	private String uri;
	
	private CryptoTiingoBean data = new CryptoTiingoBean();
}
