package project.stock.track.modules.controller.issues;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lib.base.backend.exception.data.BusinessException;
import lib.base.backend.pojo.rest.security.UserRequestPojo;
import lib.base.backend.utils.RestUtil;
import project.stock.track.app.beans.pojos.petition.data.GetIssueHistoricalDataPojo;
import project.stock.track.app.beans.pojos.petition.data.GetIssuesHistoricalDataPojo;
import project.stock.track.app.beans.pojos.petition.data.UpdateIssuesHistoricalDataPojo;
import project.stock.track.app.beans.pojos.petition.request.GetIssueHistoricalRequestPojo;
import project.stock.track.app.beans.pojos.petition.request.GetIssuesHistoricalRequestPojo;
import project.stock.track.app.vo.catalogs.CatalogsUri;
import project.stock.track.modules.business.historical.IssuesHistoricalBusiness;
import project.stock.track.modules.business.historical.IssuesHistoricalUpdateBusiness;
import reactor.core.publisher.Flux;

@RestController
public class IssuesHistoricalController {
	
	IssuesHistoricalBusiness issuesHistoricalBusiness;
	
	IssuesHistoricalUpdateBusiness updateIssuesManagerHistoricalBusiness;
	
	public IssuesHistoricalController(IssuesHistoricalBusiness issuesHistoricalBusiness, IssuesHistoricalUpdateBusiness updateIssuesManagerHistoricalBusiness) {
		this.issuesHistoricalBusiness = issuesHistoricalBusiness;
		this.updateIssuesManagerHistoricalBusiness = updateIssuesManagerHistoricalBusiness;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping(path = CatalogsUri.API_ISSUES_HISTORICAL_INDVIDUAL_GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity getIssueHistorical(@RequestBody GetIssueHistoricalRequestPojo requestPojo) {
		
		GetIssueHistoricalDataPojo dataPojo = issuesHistoricalBusiness.executeGetIssueHistorical(requestPojo);
		return new RestUtil().buildResponseSuccess(dataPojo, "Issue historical data getted");
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping(path = CatalogsUri.API_ISSUES_HISTORICAL_LIST_GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
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
	
	@SuppressWarnings("rawtypes")
	@PostMapping(path = CatalogsUri.API_ISSUES_HISTORICAL_LIST_UPDATE_FLUX, produces = MediaType.APPLICATION_NDJSON_VALUE)
	public Flux<ResponseEntity> updateIssueHistoricalFlux(@RequestBody UserRequestPojo requestPojo) throws BusinessException {
		return updateIssuesManagerHistoricalBusiness.executeUpdateIssuesHistoricalsFlux(requestPojo);
	}
}
