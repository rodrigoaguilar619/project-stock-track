package project.stock.track.app.beans.rest.dollarprice.service.bancomexico;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class BmxBean {

	private List<SerieBean> series;
}
