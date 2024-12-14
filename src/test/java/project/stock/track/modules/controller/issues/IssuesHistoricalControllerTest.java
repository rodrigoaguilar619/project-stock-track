package project.stock.track.modules.controller.issues;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import lib.base.backend.exception.data.BusinessException;
import lib.base.backend.pojo.datatable.DataTablePojo;
import lib.base.backend.pojo.rest.GenericResponsePojo;
import lib.base.backend.test.assessment.Assessment;
import project.stock.track.ProjectIntegrationTest;
import project.stock.track.app.beans.entity.CatalogIssuesEntity;
import project.stock.track.app.beans.pojos.business.historical.IssuesHistoricalFilterPojo;
import project.stock.track.app.beans.pojos.petition.data.GetIssueHistoricalDataPojo;
import project.stock.track.app.beans.pojos.petition.data.GetIssuesHistoricalDataPojo;
import project.stock.track.app.beans.pojos.petition.data.UpdateIssuesHistoricalDataPojo;
import project.stock.track.app.beans.pojos.petition.request.GetIssueHistoricalRequestPojo;
import project.stock.track.app.beans.pojos.petition.request.GetIssuesHistoricalRequestPojo;
import project.stock.track.app.beans.pojos.services.IssueHistoricQueryPojo;
import project.stock.track.app.beans.rest.exchangetrade.IssueHistoryDayBean;
import project.stock.track.app.beans.rest.exchangetrade.service.IssueHistoricMainBean;
import project.stock.track.app.repository.IssuesRepositoryImpl;
import project.stock.track.app.vo.catalogs.CatalogsEntity.CatalogTypeStock;
import project.stock.track.services.exchangetrade.IssueTrackService;

@SuppressWarnings("unchecked")
class IssuesHistoricalControllerTest extends ProjectIntegrationTest {
	
	@Autowired
	@InjectMocks
	IssuesHistoricalController issuesHistoricalController;
	
	@MockBean
	IssuesRepositoryImpl issuesRepositoryImpl;
	
	@MockBean
	IssueTrackService issueTrackService;

	@Test
	void testGetIssueHistorical() {

		GetIssueHistoricalRequestPojo requestPojo = new GetIssueHistoricalRequestPojo();
		requestPojo.setIdIssue(1);
		requestPojo.setUserName(userName);
		
		ResponseEntity<GenericResponsePojo<GetIssueHistoricalDataPojo>> response = issuesHistoricalController.getIssueHistorical(requestPojo);
		
		Assessment.assertResponseData(response);
		assertNotNull(response.getBody().getData().getIssueCalculateResume());
	}

	@Test
	void testGetIssuesHistorical() {
		
		DataTablePojo<IssuesHistoricalFilterPojo> dataTablePojo = new DataTablePojo<>();
		dataTablePojo.setCurrentPage(1);
		dataTablePojo.setRowsPerPage(10);
		
		GetIssuesHistoricalRequestPojo requestPojo = new GetIssuesHistoricalRequestPojo();
		requestPojo.setDataTableConfig(dataTablePojo);
		requestPojo.setUserName(userName);
		
		ResponseEntity<GenericResponsePojo<GetIssuesHistoricalDataPojo>> response = issuesHistoricalController.getIssuesHistorical(requestPojo);
		
		Assessment.assertResponseData(response);
		Assessment.assertDataList(response.getBody().getData().getIssuesHistorical());
	}

	@Test
	void testUpdateIssueHistorical() throws BusinessException {
		
		IssueHistoryDayBean issueHistoryDayBean = new IssueHistoryDayBean();
		issueHistoryDayBean.setOpen("1");
		issueHistoryDayBean.setClose("2");
		
		Map<String, IssueHistoryDayBean> history = new LinkedHashMap<>();
		history.put("1979-01-01 00:00:00", issueHistoryDayBean);
		
		IssueHistoricMainBean issueHistoricMainBean = new IssueHistoricMainBean();
		issueHistoricMainBean.setHistory(history);
		issueHistoricMainBean.setUri("uri test");
		
		
		CatalogIssuesEntity catalogIssuesEntity = new CatalogIssuesEntity();
		catalogIssuesEntity.setId(1);
		catalogIssuesEntity.setInitials("A");
		catalogIssuesEntity.setIdTypeStock(CatalogTypeStock.ISSUE);
		
		List<CatalogIssuesEntity> catalogIssuesEntities = new ArrayList<>();
		catalogIssuesEntities.add(catalogIssuesEntity);
		
		when(issuesRepositoryImpl.findAllWithStatusActive()).thenReturn(catalogIssuesEntities);
		when(issueTrackService.getIssueHistoric(eq(null), any(IssueHistoricQueryPojo.class))).thenReturn(issueHistoricMainBean);
		
		ResponseEntity<GenericResponsePojo<UpdateIssuesHistoricalDataPojo>> response = issuesHistoricalController.updateIssueHistorical();
		
		Assessment.assertResponseData(response);
		Assessment.assertDataList(response.getBody().getData().getIssueHistoricalResult());
	}

}
