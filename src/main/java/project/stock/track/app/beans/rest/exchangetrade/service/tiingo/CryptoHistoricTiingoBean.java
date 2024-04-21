package project.stock.track.app.beans.rest.exchangetrade.service.tiingo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CryptoHistoricTiingoBean {

	private String name;
	
	private String uri;
	
	private CryptoTiingoBean data = new CryptoTiingoBean();

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

	public CryptoTiingoBean getData() {
		return data;
	}

	public void setData(CryptoTiingoBean data) {
		this.data = data;
	}
	
}
