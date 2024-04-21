package project.stock.track.app.beans.pojos.petition.data;

import java.util.ArrayList;
import java.util.List;

import project.stock.track.app.beans.pojos.business.portfolio.PorfolioIssuePojo;
import project.stock.track.app.beans.pojos.business.portfolio.PortfolioResumePojo;

public class GetPortfolioDataPojo {

	private PortfolioResumePojo portfolioResume;
	
	List<PorfolioIssuePojo> portfolioIssues = new ArrayList<>();

	public PortfolioResumePojo getPortfolioResume() {
		return portfolioResume;
	}

	public void setPortfolioResume(PortfolioResumePojo portfolioResume) {
		this.portfolioResume = portfolioResume;
	}

	public List<PorfolioIssuePojo> getPortfolioIssues() {
		return portfolioIssues;
	}

	public void setPortfolioIssues(List<PorfolioIssuePojo> portfolioIssues) {
		this.portfolioIssues = portfolioIssues;
	}
	
}
