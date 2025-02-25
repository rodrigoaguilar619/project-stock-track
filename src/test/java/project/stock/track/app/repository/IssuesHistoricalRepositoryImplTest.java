package project.stock.track.app.repository;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import lib.base.backend.test.assessment.Assessment;
import project.stock.track.ProjectJpaTest;
import project.stock.track.app.beans.entity.IssuesManagerEntity;
import project.stock.track.app.beans.pojos.business.historical.IssuesHistoricalFilterPojo;
import project.stock.track.app.vo.entities.CatalogIndexEnum;
import project.stock.track.app.vo.entities.CatalogStatusIssueEnum;
import project.stock.track.app.vo.entities.CatalogTypeStockEnum;

class IssuesHistoricalRepositoryImplTest extends ProjectJpaTest {
	
	@Autowired
	IssuesHistoricalRepositoryImpl issuesHistoricalRepository;

	@Test
	void testFindAllWithStatusActive() {
		
		Integer idUser = 1;
		
		IssuesHistoricalFilterPojo filterPojo = new IssuesHistoricalFilterPojo();
		filterPojo.setIdSector(idUser);
		filterPojo.setIdStatusIssue(CatalogStatusIssueEnum.ACTIVE.getValue());
		filterPojo.setFairValuePriceOverPercentage(5);
		filterPojo.setIdTypeStock(CatalogTypeStockEnum.ISSUE.getValue());
		filterPojo.setIsInvest(false);
		filterPojo.setIdIndex(CatalogIndexEnum.SP500.getValue());

		List<IssuesManagerEntity> result = issuesHistoricalRepository.findAllWithStatusActive(idUser, 1, 10, filterPojo);
		
		Assessment.assertDataList(result);
	}

}
