package project.stock.track.app.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import lib.base.backend.test.assessment.Assessment;
import lib.base.backend.utils.ExecuteMethodUtil;
import project.stock.track.ProjectJpaTest;
import project.stock.track.app.beans.entity.TransactionIssueEntity;
import project.stock.track.app.beans.pojos.tuple.IssueTransactionResumeTuplePojo;
import project.stock.track.app.beans.pojos.tuple.IssueTransactionsByDateTuplePojo;
import project.stock.track.app.vo.entities.CatalogBrokerEnum;

class TransactionIssueRepositoryImplTest extends ProjectJpaTest {

	@Autowired
	private TransactionIssueRepositoryImpl transactionIssueRepository;
	
	@Test
	void testFindIssueTransactionsBuys() throws Throwable {
		
		ExecuteMethodUtil.execute("EXECUTING testFindIssueTransactionsBuys", () -> {

			Integer idUser = 1;
			Integer idIssue = 4;
			LocalDateTime date = LocalDateTime.of(2021, 2, 18, 0, 0);
			
			List<IssueTransactionsByDateTuplePojo> issueTransactionsByDateTuplePojos = transactionIssueRepository.findIssueTransactionsBuys(idUser, idIssue, date);
			
			Assessment.assertDataList(issueTransactionsByDateTuplePojos);
		});
	}
	
	@Test
	void testFindIssueTransactionsResumes() throws Throwable {
		
		ExecuteMethodUtil.executeAndCatch("EXECUTING testFindIssueTransactionsResumes", () -> {
			
			Integer idIssue = 4;
			Integer idUser = 1;
			List<IssueTransactionResumeTuplePojo> issueTransactionResumeTuplePojos = transactionIssueRepository.findIssueTransactionsResume(idUser, idIssue);
			
			Assessment.assertDataList(issueTransactionResumeTuplePojos);
		});
	}
	
	@Test
	void testFindTransactionIssuesNotSoldLower() {
		
		Integer idUser = 1;
		Integer idIssue = 102;
		Integer idBroker = CatalogBrokerEnum.CHARLES_SCHWAB.getValue();
		
		List<TransactionIssueEntity> result = transactionIssueRepository.findTransactionIssuesNotSoldLower(idUser, idIssue, idBroker, false);
		
		Assessment.assertDataList(result);
	}

}
