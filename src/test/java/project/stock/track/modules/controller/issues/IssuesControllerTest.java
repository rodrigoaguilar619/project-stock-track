package project.stock.track.modules.controller.issues;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import lib.base.backend.exception.data.BusinessException;
import lib.base.backend.pojo.rest.GenericResponsePojo;
import lib.base.backend.test.assessment.Assessment;
import project.stock.track.ProjectIntegrationTest;
import project.stock.track.app.beans.pojos.business.issues.IssuesElementMultiplePojo;
import project.stock.track.app.beans.pojos.entity.CatalogIssuesEntityPojo;
import project.stock.track.app.beans.pojos.petition.data.GetIssueDataPojo;
import project.stock.track.app.beans.pojos.petition.data.GetIssuesListDataPojo;
import project.stock.track.app.beans.pojos.petition.data.UpdateIssueDataPojo;
import project.stock.track.app.beans.pojos.petition.request.AddMultipleIssuesRequestPojo;
import project.stock.track.app.beans.pojos.petition.request.GetIssueRequestPojo;
import project.stock.track.app.beans.pojos.petition.request.GetIssuesListRequestPojo;
import project.stock.track.app.beans.pojos.petition.request.UpdateIssueRequestPojo;
import project.stock.track.app.vo.catalogs.CatalogsEntity;
import project.stock.track.app.vo.catalogs.CatalogsErrorMessage;

@SuppressWarnings("unchecked")
class IssuesControllerTest extends ProjectIntegrationTest {

	@Autowired
	IssuesController issuesController;

	@Test
	void testGetIssuesList() {
		
		GetIssuesListRequestPojo requestDto = new GetIssuesListRequestPojo();
		requestDto.setUserName(userName);
		
		ResponseEntity<GenericResponsePojo<GetIssuesListDataPojo>> response = issuesController.getIssuesList(requestDto);
		
		Assessment.assertResponseData(response);
		Assessment.assertDataList(response.getBody().getData().getIssues());
	}

	@Test
	void testUpdateIssueData() throws BusinessException {
		
		CatalogIssuesEntityPojo catalogIssuesEntityPojo = new CatalogIssuesEntityPojo();
		catalogIssuesEntityPojo.setIdSector(CatalogsEntity.CatalogSector.ENERGY);
		catalogIssuesEntityPojo.setIdIssue(2);
		catalogIssuesEntityPojo.setInitials("AAL1");
		catalogIssuesEntityPojo.setDescription("Test description");
		catalogIssuesEntityPojo.setIdStatusIssue(CatalogsEntity.CatalogStatusIssue.ACTIVE);
		catalogIssuesEntityPojo.setIdTypeStock(CatalogsEntity.CatalogTypeStock.ISSUE);
		catalogIssuesEntityPojo.setIdIndex(CatalogsEntity.CatalogIndex.SP500);
		
		UpdateIssueRequestPojo requestDto = new UpdateIssueRequestPojo();
		requestDto.setUserName(userName);
		requestDto.setIssueData(catalogIssuesEntityPojo);
		
		ResponseEntity<GenericResponsePojo<UpdateIssueDataPojo>> response = issuesController.updateIssueData(requestDto);
		
		Assessment.assertResponseData(response);
		assertNotNull(response.getBody().getData().getIdIssue());
	}

	@Test
	void testAddMultipleIssuesData() throws BusinessException {
		
		IssuesElementMultiplePojo issuesElementMultiplePojoA = new IssuesElementMultiplePojo();
		IssuesElementMultiplePojo issuesElementMultiplePojoB = new IssuesElementMultiplePojo();
		IssuesElementMultiplePojo issuesElementMultiplePojoC = new IssuesElementMultiplePojo();
		
		issuesElementMultiplePojoA.setDescription("Test description A");
		issuesElementMultiplePojoA.setIdSector(CatalogsEntity.CatalogSector.ENERGY);
		issuesElementMultiplePojoA.setInitials("AAA");
		issuesElementMultiplePojoA.setIdIndex(CatalogsEntity.CatalogIndex.SP500);
		
		issuesElementMultiplePojoB.setDescription("Test description B");
		issuesElementMultiplePojoB.setIdSector(CatalogsEntity.CatalogSector.ENERGY);
		issuesElementMultiplePojoB.setInitials("BBB");
		issuesElementMultiplePojoB.setIdIndex(CatalogsEntity.CatalogIndex.SP500);
		
		issuesElementMultiplePojoC.setDescription("Test description C");
		issuesElementMultiplePojoC.setIdSector(CatalogsEntity.CatalogSector.ENERGY);
		issuesElementMultiplePojoC.setInitials("CCC");
		issuesElementMultiplePojoC.setIdIndex(CatalogsEntity.CatalogIndex.SP500);

		List<IssuesElementMultiplePojo> issues = new ArrayList<>();
		issues.add(issuesElementMultiplePojoA);
		issues.add(issuesElementMultiplePojoB);
		issues.add(issuesElementMultiplePojoC);

		AddMultipleIssuesRequestPojo requestDto = new AddMultipleIssuesRequestPojo();
		requestDto.setUserName(userName);
		requestDto.setIssues(issues);
		requestDto.setIdStatusIssue(CatalogsEntity.CatalogStatusIssue.ACTIVE);
		requestDto.setIdTypeStock(CatalogsEntity.CatalogTypeStock.ISSUE);
		
		ResponseEntity<GenericResponsePojo<Void>> response = issuesController.addMultipleIssuesData(requestDto);
		
		Assessment.assertResponseData(response);
	}
	
	@Test
	void testAddMultipleIssuesData_ExceptionIssueExists() {
		
		IssuesElementMultiplePojo issuesElementMultiplePojoA = new IssuesElementMultiplePojo();
		IssuesElementMultiplePojo issuesElementMultiplePojoB = new IssuesElementMultiplePojo();
		
		issuesElementMultiplePojoA.setDescription("Test description AAL");
		issuesElementMultiplePojoA.setIdSector(CatalogsEntity.CatalogSector.ENERGY);
		issuesElementMultiplePojoA.setInitials("AAL");
		issuesElementMultiplePojoA.setIdIndex(CatalogsEntity.CatalogIndex.SP500);
		
		issuesElementMultiplePojoB.setDescription("Test description BBB");
		issuesElementMultiplePojoB.setIdSector(CatalogsEntity.CatalogSector.ENERGY);
		issuesElementMultiplePojoB.setInitials("BBB");
		issuesElementMultiplePojoB.setIdIndex(CatalogsEntity.CatalogIndex.SP500);

		List<IssuesElementMultiplePojo> issues = new ArrayList<>();
		issues.add(issuesElementMultiplePojoA);
		issues.add(issuesElementMultiplePojoB);

		AddMultipleIssuesRequestPojo requestDto = new AddMultipleIssuesRequestPojo();
		requestDto.setUserName(userName);
		requestDto.setIssues(issues);
		
		List<String> issuesRegitered = new ArrayList<>();
		issuesRegitered.add(issuesElementMultiplePojoA.getInitials());
		
		BusinessException exception = assertThrows(BusinessException.class, () -> {
			issuesController.addMultipleIssuesData(requestDto);
		});
		
		assertEquals(exception.getMessage(), CatalogsErrorMessage.getErrorMsgIssuesRegistered(String.join(",", issuesRegitered)));
	}

	@Test
	void testGetIssue() throws BusinessException {
		
		GetIssueRequestPojo requestDto = new GetIssueRequestPojo();
		requestDto.setUserName(userName);
		requestDto.setIdIssue(1);
		
		ResponseEntity<GenericResponsePojo<GetIssueDataPojo>> response = issuesController.getIssue(requestDto);
		
		Assessment.assertResponseData(response);
		assertNotNull(response.getBody().getData().getIssue());
	}
	
	@Test
	void testGetIssue_ExceptionIssueNotExists() {
		
		GetIssueRequestPojo requestDto = new GetIssueRequestPojo();
		requestDto.setUserName(userName);
		requestDto.setIdIssue(1000);
		
		BusinessException exception = assertThrows(BusinessException.class, () -> {
			issuesController.getIssue(requestDto);
		});
		
		assertEquals(exception.getMessage(), CatalogsErrorMessage.getErrorMsgIssueNotRegistered());
	}

}
