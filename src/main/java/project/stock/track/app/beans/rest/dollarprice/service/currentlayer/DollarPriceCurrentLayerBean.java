package project.stock.track.app.beans.rest.dollarprice.service.currentlayer;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class DollarPriceCurrentLayerBean {

	@JsonProperty("start_date")
	private String dateStart;
	
	@JsonProperty("end_date")
	private String dateEnd;
	
	private Map<String, QuoteBean> quotes;
}
