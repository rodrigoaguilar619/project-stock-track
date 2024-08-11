package project.stock.track.app.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import project.stock.track.ProjectJpaTest;
import project.stock.track.app.beans.entity.IssuesMovementsBuyEntity;

class IssuesMovementsRepositoryImplTest extends ProjectJpaTest {
	
	@Autowired
	IssuesMovementsRepositoryImpl issuesMovementsRepository;

	@Test
	void testGetIssueMovementBuy() {
		
		Integer idUser = 1;
		Integer idIssueMovement = 1;
		Integer idBuyTransaction = 1;

		IssuesMovementsBuyEntity issuesMovementsBuyEntity = issuesMovementsRepository.getIssueMovementBuy(idUser, idIssueMovement, idBuyTransaction);
		
		assertNotNull(issuesMovementsBuyEntity);
	}

}
