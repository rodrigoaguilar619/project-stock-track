package project.stock.track.app.beans.pojos.petition.data;

import java.util.ArrayList;
import java.util.List;

import project.stock.track.app.beans.pojos.business.portfolio.PortfolioResumePojo;

public class GetPortfolioListDataPojo {

	private List<PortfolioResumePojo> portfolioList = new ArrayList<>();

	public List<PortfolioResumePojo> getPortfolioList() {
		return portfolioList;
	}

	public void setPortfolioList(List<PortfolioResumePojo> portfolioList) {
		this.portfolioList = portfolioList;
	}
	
}
