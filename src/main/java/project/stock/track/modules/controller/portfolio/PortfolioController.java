package project.stock.track.modules.controller.portfolio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lib.base.backend.utils.RestUtil;
import project.stock.track.app.beans.pojos.petition.data.GetPortfolioDataPojo;
import project.stock.track.app.beans.pojos.petition.data.GetPortfolioListDataPojo;
import project.stock.track.app.beans.pojos.petition.request.GetPortfolioListRequestPojo;
import project.stock.track.app.beans.pojos.petition.request.GetPortfolioRequestPojo;
import project.stock.track.app.vo.catalogs.CatalogsUri;
import project.stock.track.modules.business.portfolio.PortfolioBusiness;

@RestController
public class PortfolioController {
	
	PortfolioBusiness portfolioBusiness;
	
	@Autowired
	public PortfolioController(PortfolioBusiness portfolioBusiness) {
		this.portfolioBusiness = portfolioBusiness;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping(path = CatalogsUri.API_PORTFOLIO_LIST_GET, consumes = "application/json", produces = "application/json")
	public ResponseEntity getPortfolioList(@RequestBody GetPortfolioListRequestPojo requestPojo) {
		
		GetPortfolioListDataPojo dataPojo = portfolioBusiness.executeGetPortfolioList(requestPojo);
		return new RestUtil().buildResponseSuccess(dataPojo, "portfolio list getted");
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping(path = CatalogsUri.API_PORTFOLIO_DATA_GET, consumes = "application/json", produces = "application/json")
	public ResponseEntity getPortfolioData(@RequestBody GetPortfolioRequestPojo requestPojo) {
		
		GetPortfolioDataPojo dataPojo = portfolioBusiness.executeGetPortfolioData(requestPojo);
		return new RestUtil().buildResponseSuccess(dataPojo, "portfolio data getted");
	}
}
