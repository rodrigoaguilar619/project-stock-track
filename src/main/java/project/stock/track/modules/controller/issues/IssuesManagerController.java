package project.stock.track.modules.controller.issues;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lib.base.backend.exception.data.BusinessException;
import lib.base.backend.utils.RestUtil;
import project.stock.track.app.beans.pojos.petition.data.GetIssueManagerDataPojo;
import project.stock.track.app.beans.pojos.petition.data.GetIssuesManagerListDataPojo;
import project.stock.track.app.beans.pojos.petition.data.UpdateIssueManagerDataPojo;
import project.stock.track.app.beans.pojos.petition.request.GetIssueManagerRequestPojo;
import project.stock.track.app.beans.pojos.petition.request.GetIssuesManagerListRequestPojo;
import project.stock.track.app.beans.pojos.petition.request.UpdateIssueManagerRequestPojo;
import project.stock.track.app.vo.catalogs.CatalogsUri;
import project.stock.track.modules.business.issues.IssuesManagerBusiness;

@RestController
public class IssuesManagerController {

	IssuesManagerBusiness issuesManagerBusiness;

	@Autowired
	public IssuesManagerController(IssuesManagerBusiness issuesManagerBusiness) {
		this.issuesManagerBusiness = issuesManagerBusiness;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping(path = CatalogsUri.API_ISSUES_MANAGER_LIST_GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity getIssuesManagerList(@RequestBody GetIssuesManagerListRequestPojo requestPojo) {
		
		GetIssuesManagerListDataPojo dataPojo = issuesManagerBusiness.executeGetIssuesManagerList(requestPojo);
		return new RestUtil().buildResponseSuccess(dataPojo, "Manager issues list getted");
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping(path = CatalogsUri.API_ISSUES_MANAGER_INDIVIDUAL_UPDATE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity updateIssueManagerData(@RequestBody UpdateIssueManagerRequestPojo requestPojo) throws BusinessException {
		
		UpdateIssueManagerDataPojo dataPojo = issuesManagerBusiness.executeUpdateIssueManager(requestPojo);
		return new RestUtil().buildResponseSuccess(dataPojo, "Manager issue update processed");
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping(path = CatalogsUri.API_ISSUES_MANAGER_INDIVIDUAL_GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity getIssueManager(@RequestBody GetIssueManagerRequestPojo requestPojo) throws BusinessException {
		
		GetIssueManagerDataPojo dataPojo = issuesManagerBusiness.executeGetIssueManager(requestPojo);
		return new RestUtil().buildResponseSuccess(dataPojo, "Get manager issue processed");
	}
}
