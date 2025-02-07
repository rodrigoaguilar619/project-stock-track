package project.stock.track.modules.controller.issues;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import lib.base.backend.pojo.rest.GenericResponsePojo;
import lib.base.backend.test.assessment.Assessment;
import project.stock.track.ProjectIntegrationTest;
import project.stock.track.app.beans.pojos.petition.data.GetTempIssuesLastPriceDataPojo;
import project.stock.track.app.beans.pojos.services.IssuesLastPriceQueryPojo;
import project.stock.track.app.beans.rest.exchangetrade.service.IssueIexMainBean;
import project.stock.track.services.exchangetrade.IssueTrackService;

@SuppressWarnings("unchecked")
class IssuesLastPriceControllerTest extends ProjectIntegrationTest {
	
	@Autowired
	@InjectMocks
	IssuesLastPriceController issuesLastPriceController;
	
	@MockBean
	IssueTrackService issueTrackService;

	@Test
	void testGetIssuesLastPrice() {

		ResponseEntity<GenericResponsePojo<GetTempIssuesLastPriceDataPojo>> response = issuesLastPriceController.getIssuesLastPrice();
		
		Assessment.assertResponseData(response);
		assertNotNull(response.getBody().getData().getTempIssueLastPricesMap());
	}

	@Test
	void testUpdateTempLastPrices() {
		
		List<String> issuesSp500 = new ArrayList<>( Arrays.asList( "AAL" ) );
		List<String> issuesNotSp500 = new ArrayList<>( Arrays.asList( "AZN", "BYND" ) );
		
		IssuesLastPriceQueryPojo issuesLastPriceQuerySp500Pojo = new IssuesLastPriceQueryPojo();
		issuesLastPriceQuerySp500Pojo.setIsStandardPoors(true);
		issuesLastPriceQuerySp500Pojo.setIssues(issuesSp500);
		
		IssuesLastPriceQueryPojo issuesLastPriceQueryNotSp500Pojo = new IssuesLastPriceQueryPojo();
		issuesLastPriceQueryNotSp500Pojo.setIsStandardPoors(false);
		issuesLastPriceQueryNotSp500Pojo.setIssues(issuesNotSp500);
		
		IssueIexMainBean issueIexMainBean_AAL = new IssueIexMainBean();
		issueIexMainBean_AAL.setTngoLast(new BigDecimal(100));
		
		IssueIexMainBean issueIexMainBean_AZN = new IssueIexMainBean();
		issueIexMainBean_AZN.setTngoLast(new BigDecimal(200));
		
		IssueIexMainBean issueIexMainBean_BYND = new IssueIexMainBean();
		issueIexMainBean_BYND.setTngoLast(new BigDecimal(300));
		
		Map<String, IssueIexMainBean> mapSp500 = new LinkedHashMap<>();
		mapSp500.put("AAL", issueIexMainBean_AAL);
		
		Map<String, IssueIexMainBean> mapNotSp500 = new LinkedHashMap<>();
		mapNotSp500.put("AZN", issueIexMainBean_AZN);
		mapNotSp500.put("BYND", issueIexMainBean_BYND);
		
		when(issueTrackService.getIssuesLastPrice(eq(null), any(IssuesLastPriceQueryPojo.class))).thenReturn(mapNotSp500, mapSp500);
		
		ResponseEntity<GenericResponsePojo<String>> response = issuesLastPriceController.updateTempLastPrices();
		
		Assessment.assertResponseData(response);
	}

}
