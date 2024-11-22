package project.stock.track.modules.controller.issues;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lib.base.backend.enumerators.CrudOptionsEnum;
import lib.base.backend.exception.data.BusinessException;
import lib.base.backend.utils.RestUtil;
import project.stock.track.app.beans.pojos.petition.data.GetIssueMovementDataPojo;
import project.stock.track.app.beans.pojos.petition.data.GetIssuesMovementsListDataPojo;
import project.stock.track.app.beans.pojos.petition.request.AddEditIssueMovementRequestPojo;
import project.stock.track.app.beans.pojos.petition.request.DeleteIssueMovementRequestPojo;
import project.stock.track.app.beans.pojos.petition.request.GetIssueMovementRequestPojo;
import project.stock.track.app.beans.pojos.petition.request.GetIssuesMovementsListRequestPojo;
import project.stock.track.app.vo.catalogs.CatalogsUri;
import project.stock.track.modules.business.issues.IssuesMovementsBusiness;
import project.stock.track.modules.business.issues.IssuesMovementsCrudBusiness;

@RestController
public class IssuesMovementsController {

	IssuesMovementsBusiness issuesMovementsBusiness;
	
	IssuesMovementsCrudBusiness issuesMovementsCrudBusiness;
	
	public IssuesMovementsController(IssuesMovementsBusiness issuesMovementsBusiness, IssuesMovementsCrudBusiness issuesMovementsCrudBusiness) {
		this.issuesMovementsBusiness = issuesMovementsBusiness;
		this.issuesMovementsCrudBusiness = issuesMovementsCrudBusiness;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping(path = CatalogsUri.API_ISSUES_MOVEMENTS_LIST_GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity getIssuesMovementsList(@RequestBody GetIssuesMovementsListRequestPojo requestPojo) throws BusinessException {
		
		GetIssuesMovementsListDataPojo dataPojo = issuesMovementsBusiness.executeGetIssuesMovements(requestPojo);
		return new RestUtil().buildResponseSuccess(dataPojo, "Issues movements list getted");
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping(path = CatalogsUri.API_ISSUES_MOVEMENTS_INDIVIDUAL_GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity getIssueMovement(@RequestBody GetIssueMovementRequestPojo requestPojo) {
		
		GetIssueMovementDataPojo dataPojo = issuesMovementsCrudBusiness.executeGetIssueMovement(requestPojo);
		return new RestUtil().buildResponseSuccess(dataPojo, "Issue movement data getted");
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping(path = CatalogsUri.API_ISSUES_MOVEMENTS_INDIVIDUAL_SAVE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity saveIssueMovement(@RequestBody AddEditIssueMovementRequestPojo requestPojo) throws BusinessException {
		
		issuesMovementsCrudBusiness.executeManageAddEditIssueMovement(requestPojo, CrudOptionsEnum.SAVE);
		return new RestUtil().buildResponseSuccess("", "Issue movement data saved");
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping(path = CatalogsUri.API_ISSUES_MOVEMENTS_INDIVIDUAL_UPDATE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity updateIssueMovement(@RequestBody AddEditIssueMovementRequestPojo requestPojo) throws BusinessException {
		
		issuesMovementsCrudBusiness.executeManageAddEditIssueMovement(requestPojo, CrudOptionsEnum.UPDATE);
		return new RestUtil().buildResponseSuccess("", "Issue movement data updated");
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping(path = CatalogsUri.API_ISSUES_MOVEMENTS_INDIVIDUAL_INACTIVATE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity inactivateIssueMovement(@RequestBody GetIssueMovementRequestPojo requestPojo) {
		
		issuesMovementsCrudBusiness.executeInactivateIssueMovement(requestPojo);
		return new RestUtil().buildResponseSuccess("", "Inactivate issue movement processed");
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping(path = CatalogsUri.API_ISSUES_MOVEMENTS_INDIVIDUAL_DELETE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity deleteIssueMovement(@RequestBody DeleteIssueMovementRequestPojo requestPojo) {
		
		issuesMovementsCrudBusiness.executeDeleteIssueMovement(requestPojo);
		return new RestUtil().buildResponseSuccess("", "Delete issue movement processed");
	}
}
