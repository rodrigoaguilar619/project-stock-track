package project.stock.track.modules.controller.portfolio;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import lib.base.backend.pojo.rest.GenericResponsePojo;
import lib.base.backend.test.assessment.Assessment;
import project.stock.track.ProjectIntegrationTest;
import project.stock.track.app.beans.pojos.petition.data.GetPortfolioDataPojo;
import project.stock.track.app.beans.pojos.petition.data.GetPortfolioListDataPojo;
import project.stock.track.app.beans.pojos.petition.request.GetPortfolioListRequestPojo;
import project.stock.track.app.beans.pojos.petition.request.GetPortfolioRequestPojo;
import project.stock.track.app.vo.catalogs.CatalogsEntity.CatalogBroker;

@SuppressWarnings("unchecked")
class PortfolioControllerTest  extends ProjectIntegrationTest {
	
	@Autowired
	PortfolioController portfolioController;

	@Test
	void testGetPortfolioList() {

		GetPortfolioListRequestPojo requestPojo = new GetPortfolioListRequestPojo();
		requestPojo.setUserName(userName);
		
		ResponseEntity<GenericResponsePojo<GetPortfolioListDataPojo>> response = portfolioController.getPortfolioList(requestPojo);
		
		Assessment.assertResponseData(response);
		Assessment.assertDataList(response.getBody().getData().getPortfolioList());
	}

	@Test
	void testGetPortfolioData() {

		GetPortfolioRequestPojo requestPojo = new GetPortfolioRequestPojo();
		requestPojo.setUserName(userName);
		requestPojo.setIdBroker(CatalogBroker.CHARLES_SCHWAB);
		
		ResponseEntity<GenericResponsePojo<GetPortfolioDataPojo>> response = portfolioController.getPortfolioData(requestPojo);
		
		Assessment.assertResponseData(response);
		assertNotNull(response.getBody().getData().getPortfolioIssues());
	}

}
