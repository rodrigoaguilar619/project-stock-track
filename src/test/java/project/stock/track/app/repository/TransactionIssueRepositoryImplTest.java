package project.stock.track.app.repository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import lib.base.backend.test.assessment.Assessment;
import lib.base.backend.utils.ExecuteMethodUtil;
import project.stock.track.ProjectJpaTest;
import project.stock.track.app.beans.entity.TransactionIssueEntity;
import project.stock.track.app.beans.pojos.tuple.IssueTransactionResumeTuplePojo;
import project.stock.track.app.beans.pojos.tuple.IssueTransactionsByDateTuplePojo;
import project.stock.track.app.vo.catalogs.CatalogsEntity.CatalogBroker;

class TransactionIssueRepositoryImplTest extends ProjectJpaTest {

	@Autowired
	private TransactionIssueRepositoryImpl transactionIssueRepository;
	
	@Test
	void testFindIssueTransactionsBuys() throws Throwable {
		
		ExecuteMethodUtil.execute("EXECUTING testFindIssueTransactionsBuys", () -> {

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Integer idUser = 1;
			Integer idIssue = 4;
			Date date = sdf.parse("2021-02-18");
			
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
		Integer idBroker = CatalogBroker.CHARLES_SCHWAB;
		
		List<TransactionIssueEntity> result = transactionIssueRepository.findTransactionIssuesNotSoldLower(idUser, idIssue, idBroker, false);
		
		Assessment.assertDataList(result);
	}

}
