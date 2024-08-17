package project.stock.track.app.beans.rest.dollarprice.service.bancomexico;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class DollarPriceBancoMexicoBean {

	private BmxBean bmx;
}
