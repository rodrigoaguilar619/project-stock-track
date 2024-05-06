package project.stock.track.modules.controller.issues;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lib.base.backend.exception.data.BusinessException;
import lib.base.backend.utils.RestUtil;
import project.stock.track.app.beans.pojos.petition.data.GetIssueHistoricalDataPojo;
import project.stock.track.app.beans.pojos.petition.data.GetIssuesHistoricalDataPojo;
import project.stock.track.app.beans.pojos.petition.data.UpdateIssuesHistoricalDataPojo;
import project.stock.track.app.beans.pojos.petition.request.GetIssueHistoricalRequestPojo;
import project.stock.track.app.beans.pojos.petition.request.GetIssuesHistoricalRequestPojo;
import project.stock.track.app.vo.catalogs.CatalogsUri;
import project.stock.track.modules.business.historical.IssuesHistoricalBusiness;
import project.stock.track.modules.business.historical.IssuesHistoricalUpdateBusiness;

@RestController
public class IssuesHistoricalController {
	
	IssuesHistoricalBusiness issuesHistoricalBusiness;
	
	IssuesHistoricalUpdateBusiness updateIssuesManagerHistoricalBusiness;
	
	@Autowired
	public IssuesHistoricalController(IssuesHistoricalBusiness issuesHistoricalBusiness, IssuesHistoricalUpdateBusiness updateIssuesManagerHistoricalBusiness) {
		this.issuesHistoricalBusiness = issuesHistoricalBusiness;
		this.updateIssuesManagerHistoricalBusiness = updateIssuesManagerHistoricalBusiness;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping(path = CatalogsUri.API_ISSUES_HISTORICAL_INDVIDUAL_GET, consumes = "application/json", produces = "application/json")
	public ResponseEntity getIssueHistorical(@RequestBody GetIssueHistoricalRequestPojo requestPojo) {
		
		GetIssueHistoricalDataPojo dataPojo = issuesHistoricalBusiness.executeGetIssueHistorical(requestPojo);
		return new RestUtil().buildResponseSuccess(dataPojo, "Issue historical data getted");
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping(path = CatalogsUri.API_ISSUES_HISTORICAL_LIST_GET, consumes = "application/json", produces = "application/json")
	public ResponseEntity getIssuesHistorical(@RequestBody GetIssuesHistoricalRequestPojo requestPojo) {
		
		GetIssuesHistoricalDataPojo dataPojo = issuesHistoricalBusiness.executeGetIssuesHistorical(requestPojo);
		return new RestUtil().buildResponseSuccess(dataPojo, "Issues historical getted");
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping(CatalogsUri.API_ISSUES_HISTORICAL_LIST_UPDATE)
	public ResponseEntity updateIssueHistorical() throws BusinessException {
		
		UpdateIssuesHistoricalDataPojo dataPojo = updateIssuesManagerHistoricalBusiness.executeUpdateIssuesHistoricals();
		return new RestUtil().buildResponseSuccess(dataPojo, "Issues historical data updated");
	}
}
