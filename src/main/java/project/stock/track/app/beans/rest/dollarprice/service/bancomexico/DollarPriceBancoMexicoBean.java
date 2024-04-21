package project.stock.track.app.beans.rest.dollarprice.service.bancomexico;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DollarPriceBancoMexicoBean {

	private BmxBean bmx;

	public BmxBean getBmx() {
		return bmx;
	}

	public void setBmx(BmxBean bmx) {
		this.bmx = bmx;
	}
	
}
