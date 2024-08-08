package project.stock.track.modules.controller.transactions;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import lib.base.backend.pojo.rest.GenericResponsePojo;
import lib.base.backend.test.assessment.Assessment;
import project.stock.track.ProjectIntegrationTest;
import project.stock.track.app.beans.pojos.petition.data.GetTransactionIssuestrackDataPojo;
import project.stock.track.app.beans.pojos.petition.request.GetTransactionIssuestrackRequestPojo;

@SuppressWarnings("unchecked")
class TransactionIssuesControllerTest extends ProjectIntegrationTest {

	@Autowired
	TransactionIssuesController transactionIssuesController;
	
	@Test
	void testGetTransactionIssuesTrack() {

		GetTransactionIssuestrackRequestPojo requestPojo = new GetTransactionIssuestrackRequestPojo();
		requestPojo.setUserName(userName);

		ResponseEntity<GenericResponsePojo<GetTransactionIssuestrackDataPojo>> response = transactionIssuesController.getTransactionIssuesTrack(requestPojo);
		
		Assessment.assertResponseData(response);
		Assessment.assertDataList(response.getBody().getData().getTransactionIssuesTrackList());
	}

}
