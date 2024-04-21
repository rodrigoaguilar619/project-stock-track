package project.stock.track.app.beans.rest.dollarprice.service.bancomexico;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SerieBean {

	private String idSerie;
	
	private String titulo;
	
	private List<DatosBean> datos;

	public String getIdSerie() {
		return idSerie;
	}

	public void setIdSerie(String idSerie) {
		this.idSerie = idSerie;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public List<DatosBean> getDatos() {
		return datos;
	}

	public void setDatos(List<DatosBean> datos) {
		this.datos = datos;
	}
}
