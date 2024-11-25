package project.stock.track.modules.controller.issues;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.joda.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import lib.base.backend.exception.data.BusinessException;
import lib.base.backend.pojo.datatable.DataTablePojo;
import lib.base.backend.pojo.rest.GenericResponsePojo;
import lib.base.backend.test.assessment.Assessment;
import project.stock.track.ProjectIntegrationTest;
import project.stock.track.app.beans.pojos.business.issues.IssuesMovementsFiltersPojo;
import project.stock.track.app.beans.pojos.entity.IssueMovementBuyEntityPojo;
import project.stock.track.app.beans.pojos.petition.data.GetIssueMovementDataPojo;
import project.stock.track.app.beans.pojos.petition.data.GetIssuesMovementsListDataPojo;
import project.stock.track.app.beans.pojos.petition.request.AddEditIssueMovementRequestPojo;
import project.stock.track.app.beans.pojos.petition.request.GetIssueMovementRequestPojo;
import project.stock.track.app.beans.pojos.petition.request.GetIssuesMovementsListRequestPojo;
import project.stock.track.app.vo.catalogs.CatalogsEntity.CatalogBroker;
import project.stock.track.app.vo.catalogs.CatalogsEntity.CatalogStatusIssueMovement;
import project.stock.track.app.vo.catalogs.CatalogsEntity.CatalogTypeCurrency;

@SuppressWarnings("unchecked")
class IssuesMovementsControllerTest extends ProjectIntegrationTest {
	
	@Autowired
	IssuesMovementsController issuesMovementsController;

	@Test
	void testGetIssuesMovementsList() throws BusinessException {
		
		DataTablePojo<IssuesMovementsFiltersPojo> dataTablePojo = new DataTablePojo<>();
		dataTablePojo.setCurrentPage(1);
		dataTablePojo.setRowsPerPage(10);
		
		GetIssuesMovementsListRequestPojo requestPojo = new GetIssuesMovementsListRequestPojo();
		requestPojo.setUserName(userName);
		requestPojo.setDataTableConfig(dataTablePojo);
		requestPojo.setIdTypeCurrency(CatalogTypeCurrency.USD);
		
		ResponseEntity<GenericResponsePojo<GetIssuesMovementsListDataPojo>> response = issuesMovementsController.getIssuesMovementsList(requestPojo);
		
		Assessment.assertResponseData(response);
		Assessment.assertDataList(response.getBody().getData().getIssuesMovementsList());
	}

	@Test
	void testGetIssueMovement() {
		
		GetIssueMovementRequestPojo requestPojo = new GetIssueMovementRequestPojo();
		requestPojo.setUserName(userName);
		requestPojo.setIdIssueMovement(1);
		requestPojo.setIdTypeCurrency(CatalogTypeCurrency.USD);
		
		ResponseEntity<GenericResponsePojo<GetIssueMovementDataPojo>> response = issuesMovementsController.getIssueMovement(requestPojo);
		
		Assessment.assertResponseData(response);
		assertNotNull(response.getBody().getData().getIssueMovement());
	}

	@Test
	void testSaveIssueMovement() throws BusinessException {

		IssueMovementBuyEntityPojo issueMovementBuyEntityPojo1 = new IssueMovementBuyEntityPojo();
		issueMovementBuyEntityPojo1.setBuyDate(new LocalDate(2024, 1, 2).toDate().getTime());
		issueMovementBuyEntityPojo1.setBuyPrice(new BigDecimal("190"));
		issueMovementBuyEntityPojo1.setBuyTransactionNumber(1);
		issueMovementBuyEntityPojo1.setTotalShares(new BigDecimal("5"));
		
		IssueMovementBuyEntityPojo issueMovementBuyEntityPojo2 = new IssueMovementBuyEntityPojo();
		issueMovementBuyEntityPojo2.setBuyDate(new LocalDate(2024, 1, 2).toDate().getTime());
		issueMovementBuyEntityPojo2.setBuyPrice(new BigDecimal("170"));
		issueMovementBuyEntityPojo2.setBuyTransactionNumber(2);
		issueMovementBuyEntityPojo2.setTotalShares(new BigDecimal("15"));
		
		List<IssueMovementBuyEntityPojo> issueMovementBuysList = 
				new ArrayList<>(Arrays.asList(issueMovementBuyEntityPojo1, issueMovementBuyEntityPojo2));
		

		AddEditIssueMovementRequestPojo requestPojo = new AddEditIssueMovementRequestPojo();
		requestPojo.setUserName(userName);
		requestPojo.setIdBroker(CatalogBroker.CHARLES_SCHWAB);
		requestPojo.setIdIssueMovement(null);
		requestPojo.setIdStatus(CatalogStatusIssueMovement.ACTIVE);
		requestPojo.setIssue("AAL");
		requestPojo.setIssueMovementBuysList(issueMovementBuysList);
		requestPojo.setIdTypeCurrency(CatalogTypeCurrency.USD);
		
		ResponseEntity<GenericResponsePojo<Void>> response = issuesMovementsController.saveIssueMovement(requestPojo);
		
		Assessment.assertResponseData(response);
	}

	@Test
	void testUpdateIssueMovement() throws BusinessException {
		
		IssueMovementBuyEntityPojo issueMovementBuyEntityPojo1 = new IssueMovementBuyEntityPojo();
		issueMovementBuyEntityPojo1.setBuyDate(new LocalDate(2024, 1, 2).toDate().getTime());
		issueMovementBuyEntityPojo1.setBuyPrice(new BigDecimal("190"));
		issueMovementBuyEntityPojo1.setBuyTransactionNumber(1);
		issueMovementBuyEntityPojo1.setTotalShares(new BigDecimal("5"));
		
		IssueMovementBuyEntityPojo issueMovementBuyEntityPojo2 = new IssueMovementBuyEntityPojo();
		issueMovementBuyEntityPojo2.setBuyDate(new LocalDate(2024, 1, 2).toDate().getTime());
		issueMovementBuyEntityPojo2.setBuyPrice(new BigDecimal("170"));
		issueMovementBuyEntityPojo2.setBuyTransactionNumber(2);
		issueMovementBuyEntityPojo2.setTotalShares(new BigDecimal("15"));
		
		List<IssueMovementBuyEntityPojo> issueMovementBuysList = 
				new ArrayList<>(Arrays.asList(issueMovementBuyEntityPojo1, issueMovementBuyEntityPojo2));
		

		AddEditIssueMovementRequestPojo requestPojo = new AddEditIssueMovementRequestPojo();
		requestPojo.setUserName(userName);
		requestPojo.setIdBroker(CatalogBroker.CHARLES_SCHWAB);
		requestPojo.setIdIssueMovement(1);
		requestPojo.setIdStatus(CatalogStatusIssueMovement.ACTIVE);
		requestPojo.setIssue("AAL");
		requestPojo.setIssueMovementBuysList(issueMovementBuysList);
		requestPojo.setIdTypeCurrency(CatalogTypeCurrency.USD);
		
		ResponseEntity<GenericResponsePojo<Void>> response = issuesMovementsController.updateIssueMovement(requestPojo);
		
		Assessment.assertResponseData(response);
	}

	
	@Test
	void testInactivateIssueMovement() {

		GetIssueMovementRequestPojo requestPojo = new GetIssueMovementRequestPojo();
		requestPojo.setUserName(userName);
		requestPojo.setIdIssueMovement(1);
		
		ResponseEntity<GenericResponsePojo<Void>> response = issuesMovementsController.inactivateIssueMovement(requestPojo);
		
		Assessment.assertResponseData(response);
	}

}
