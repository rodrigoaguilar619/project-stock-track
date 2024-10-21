package project.stock.track.app.beans.rest.dollarprice.service.currentlayer;

import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class QuoteBean {

	@JsonProperty("USDMXN")
	private BigDecimal mxn;
}
