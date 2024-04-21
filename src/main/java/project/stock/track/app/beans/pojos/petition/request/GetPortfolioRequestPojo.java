package project.stock.track.app.beans.pojos.petition.request;

import lib.base.backend.pojo.rest.security.UserRequestPojo;

public class GetPortfolioRequestPojo extends UserRequestPojo {

	private Integer idBroker;

	public Integer getIdBroker() {
		return idBroker;
	}

	public void setIdBroker(Integer idBroker) {
		this.idBroker = idBroker;
	}
	
}
