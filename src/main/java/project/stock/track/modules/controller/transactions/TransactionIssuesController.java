package project.stock.track.modules.controller.transactions;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lib.base.backend.utils.RestUtil;
import project.stock.track.app.beans.pojos.petition.data.GetTransactionIssuestrackDataPojo;
import project.stock.track.app.beans.pojos.petition.request.GetTransactionIssuestrackRequestPojo;
import project.stock.track.app.vo.catalogs.CatalogsUri;
import project.stock.track.modules.business.transactions.TransactionIssuesBusiness;

@RestController
public class TransactionIssuesController {
	
	TransactionIssuesBusiness portfolioBusiness;
	
	public TransactionIssuesController(TransactionIssuesBusiness portfolioBusiness) {
		this.portfolioBusiness = portfolioBusiness;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping(path = CatalogsUri.API_TRANSACION_ISSUES_TRACK_GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity getTransactionIssuesTrack(@RequestBody GetTransactionIssuestrackRequestPojo requestPojo) {
		
		GetTransactionIssuestrackDataPojo dataPojo = portfolioBusiness.executeGetTransactionIssuesTrack(requestPojo);
		return new RestUtil().buildResponseSuccess(dataPojo, "transaction issues track getted");
	}
}
