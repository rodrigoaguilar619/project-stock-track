package project.stock.track.modules.controller.issues;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lib.base.backend.exception.data.BusinessException;
import lib.base.backend.utils.RestUtil;
import project.stock.track.app.beans.pojos.petition.data.GetIssueDataPojo;
import project.stock.track.app.beans.pojos.petition.data.GetIssuesListDataPojo;
import project.stock.track.app.beans.pojos.petition.data.UpdateIssueDataPojo;
import project.stock.track.app.beans.pojos.petition.request.AddMultipleIssuesRequestPojo;
import project.stock.track.app.beans.pojos.petition.request.GetIssueRequestPojo;
import project.stock.track.app.beans.pojos.petition.request.GetIssuesListRequestPojo;
import project.stock.track.app.beans.pojos.petition.request.UpdateIssueRequestPojo;
import project.stock.track.app.vo.catalogs.CatalogsUri;
import project.stock.track.modules.business.issues.IssuesBusiness;

@RestController
public class IssuesController {

	IssuesBusiness issuesBusiness;
	
	public IssuesController(IssuesBusiness issuesBusiness) {
		this.issuesBusiness = issuesBusiness;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping(path = CatalogsUri.API_ISSUES_LIST_GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity getIssuesList(@RequestBody GetIssuesListRequestPojo requestPojo) {
		
		GetIssuesListDataPojo dataPojo = issuesBusiness.executeGetIssuesList(requestPojo);
		return new RestUtil().buildResponseSuccess(dataPojo, "Issues list getted");
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping(path = CatalogsUri.API_ISSUES_INDIVIDUAL_UPDATE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity updateIssueData(@RequestBody UpdateIssueRequestPojo requestPojo) throws BusinessException {
		
		UpdateIssueDataPojo dataPojo = issuesBusiness.executeUpdateIssue(requestPojo);
		return new RestUtil().buildResponseSuccess(dataPojo, "Issue update processed");
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping(path = CatalogsUri.API_ISSUES_MULTIPLE_ADD, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity addMultipleIssuesData(@RequestBody AddMultipleIssuesRequestPojo requestPojo) throws BusinessException {
		
		issuesBusiness.executeAddMultipleIssues(requestPojo);
		return new RestUtil().buildResponseSuccess(null, "Add multiple issues processed");
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping(path = CatalogsUri.API_ISSUES_INDIVIDUAL_GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity getIssue(@RequestBody GetIssueRequestPojo requestPojo) throws BusinessException {
		
		GetIssueDataPojo dataPojo = issuesBusiness.executeGetIssue(requestPojo);
		return new RestUtil().buildResponseSuccess(dataPojo, "Get issue processed");
	}
}
