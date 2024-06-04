package project.stock.track.modules.controller.transactions;

import java.io.IOException;
import java.text.ParseException;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
public class LoadTransactionIssuesFileController {
	
	LoadTransactionIssuesFileBusiness loadTransactionIssuesFileBusiness;
	
	@Autowired
	public LoadTransactionIssuesFileController(LoadTransactionIssuesFileBusiness loadTransactionIssuesFileBusiness) {
		this.loadTransactionIssuesFileBusiness = loadTransactionIssuesFileBusiness;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping(path = CatalogsUri.API_TRANSACION_ISSUES_FILE_LOAD, consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity registerIssueTransactions(HttpServletResponse httpResponse, @ModelAttribute LoadTransactionIssuesFileRequestPojo requestPojo) throws IOException, BusinessException, ParseException {
		
		LoadTransactionIssuesFileDataPojo dataPojo = loadTransactionIssuesFileBusiness.executeRegisterTransactionIssuesFromFile(requestPojo);
		
		return new RestUtil().buildResponseSuccess(dataPojo, "File transaction issues loaded");
	}
}
