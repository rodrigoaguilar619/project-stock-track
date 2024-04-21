package project.stock.track.app.beans.rest.dollarprice.service.bancomexico;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BmxBean {

	private List<SerieBean> series;

	public List<SerieBean> getSeries() {
		return series;
	}

	public void setSeries(List<SerieBean> series) {
		this.series = series;
	}
	
}
