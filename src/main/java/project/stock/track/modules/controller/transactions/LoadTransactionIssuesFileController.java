package project.stock.track.modules.controller.transactions;

import java.io.IOException;
import java.text.ParseException;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import lib.base.backend.exception.data.BusinessException;
import lib.base.backend.utils.RestUtil;
import project.stock.track.app.beans.pojos.petition.data.LoadTransactionIssuesFileDataPojo;
import project.stock.track.app.beans.pojos.petition.request.LoadTransactionIssuesFileRequestPojo;
import project.stock.track.app.vo.catalogs.CatalogsUri;
import project.stock.track.modules.business.transactions.LoadTransactionIssuesFileBusiness;
import project.stock.track.modules.business.transactions.LoadTransactionMoneyFileBusiness;

@RestController
public class LoadTransactionIssuesFileController {
	
	LoadTransactionIssuesFileBusiness loadTransactionIssuesFileBusiness;
	LoadTransactionMoneyFileBusiness loadTransactionMoneyFileBusiness;
	
	public LoadTransactionIssuesFileController(LoadTransactionIssuesFileBusiness loadTransactionIssuesFileBusiness, LoadTransactionMoneyFileBusiness loadTransactionMoneyFileBusiness) {
		this.loadTransactionIssuesFileBusiness = loadTransactionIssuesFileBusiness;
		this.loadTransactionMoneyFileBusiness = loadTransactionMoneyFileBusiness;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping(path = CatalogsUri.API_TRANSACION_ISSUES_FILE_LOAD, consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity registerIssueTransactions(@ModelAttribute LoadTransactionIssuesFileRequestPojo requestPojo) throws IOException, BusinessException, ParseException {
		
		LoadTransactionIssuesFileDataPojo dataPojo = loadTransactionIssuesFileBusiness.executeRegisterTransactionIssuesFromFile(requestPojo);
		
		return new RestUtil().buildResponseSuccess(dataPojo, "File transaction issues loaded");
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping(path = CatalogsUri.API_TRANSACION_MONEY_FILE_LOAD, consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity registerMoneyTransactions(@ModelAttribute LoadTransactionIssuesFileRequestPojo requestPojo) throws IOException, BusinessException, ParseException {
		
		LoadTransactionIssuesFileDataPojo dataPojo = loadTransactionMoneyFileBusiness.executeRegisterTransactionMoneyFromFile(requestPojo);
		
		return new RestUtil().buildResponseSuccess(dataPojo, "File transaction money loaded");
	}
}
