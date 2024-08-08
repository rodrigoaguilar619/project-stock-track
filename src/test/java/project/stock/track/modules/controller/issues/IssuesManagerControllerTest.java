package project.stock.track.modules.controller.issues;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import lib.base.backend.exception.data.BusinessException;
import lib.base.backend.pojo.rest.GenericResponsePojo;
import lib.base.backend.test.assessment.Assessment;
import project.stock.track.ProjectIntegrationTest;
import project.stock.track.app.beans.pojos.business.issues.UpdateIssueManagerPojo;
import project.stock.track.app.beans.pojos.petition.data.GetIssueManagerDataPojo;
import project.stock.track.app.beans.pojos.petition.data.GetIssuesManagerListDataPojo;
import project.stock.track.app.beans.pojos.petition.data.UpdateIssueManagerDataPojo;
import project.stock.track.app.beans.pojos.petition.request.GetIssueManagerRequestPojo;
import project.stock.track.app.beans.pojos.petition.request.GetIssuesManagerListRequestPojo;
import project.stock.track.app.beans.pojos.petition.request.UpdateIssueManagerRequestPojo;
import project.stock.track.app.vo.catalogs.CatalogsEntity.CatalogStatusQuick;
import project.stock.track.app.vo.catalogs.CatalogsEntity.CatalogStatusTrading;

@SuppressWarnings("unchecked")
class IssuesManagerControllerTest extends ProjectIntegrationTest {
	
	@Autowired
	IssuesManagerController issuesManagerController;

	@Test
	void testGetIssuesManagerList() {
		
		GetIssuesManagerListRequestPojo requestPojo = new GetIssuesManagerListRequestPojo();
		requestPojo.setUserName(userName);

		ResponseEntity<GenericResponsePojo<GetIssuesManagerListDataPojo>> response = issuesManagerController.getIssuesManagerList(requestPojo);
				
		Assessment.assertResponseData(response);
		Assessment.assertDataList(response.getBody().getData().getIssuesManagerList());
	}

	@Test
	void testUpdateIssueManagerData() throws BusinessException {

		UpdateIssueManagerPojo updateIssueManagerPojo = new UpdateIssueManagerPojo();
		updateIssueManagerPojo.setIdIssue(1);
		updateIssueManagerPojo.setIdStatusIssueQuick(CatalogStatusQuick.ACTIVE);
		updateIssueManagerPojo.setIdStatusIssueTrading(CatalogStatusTrading.ACTIVE);
		updateIssueManagerPojo.setFairValue(new BigDecimal("10"));

		UpdateIssueManagerRequestPojo requestPojo = new UpdateIssueManagerRequestPojo();
		requestPojo.setUserName(userName);
		requestPojo.setIssueManagerData(updateIssueManagerPojo);
		
		ResponseEntity<GenericResponsePojo<UpdateIssueManagerDataPojo>> response = issuesManagerController.updateIssueManagerData(requestPojo);
		
		Assessment.assertResponseData(response);
		assertNotNull(response.getBody().getData().getIdIssueManager());
	}

	@Test
	void testGetIssueManager() throws BusinessException {
		
		GetIssueManagerRequestPojo requestPojo = new GetIssueManagerRequestPojo();
		requestPojo.setUserName(userName);
		requestPojo.setIdIssue(1);
		
		ResponseEntity<GenericResponsePojo<GetIssueManagerDataPojo>> response = issuesManagerController.getIssueManager(requestPojo);
		
		Assessment.assertResponseData(response);
		assertNotNull(response.getBody().getData().getIssue());
	}

}
