package project.stock.track.app.beans.rest.dollarprice.service.bancomexico;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class SerieBean {

	private String idSerie;
	
	private String titulo;
	
	private List<DatosBean> datos;
}
