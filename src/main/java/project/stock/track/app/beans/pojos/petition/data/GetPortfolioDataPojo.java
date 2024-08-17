package project.stock.track.app.beans.pojos.petition.data;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import project.stock.track.app.beans.pojos.business.portfolio.PorfolioIssuePojo;
import project.stock.track.app.beans.pojos.business.portfolio.PortfolioResumePojo;

@Getter @Setter
public class GetPortfolioDataPojo {

	private PortfolioResumePojo portfolioResume;
	
	private List<PorfolioIssuePojo> portfolioIssues = new ArrayList<>();
}
