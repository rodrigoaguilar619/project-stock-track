package project.stock.track.app.repository;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import lib.base.backend.test.assessment.Assessment;
import project.stock.track.ProjectJpaTest;
import project.stock.track.app.beans.entity.IssuesManagerEntity;
import project.stock.track.app.beans.pojos.business.historical.IssuesHistoricalFilterPojo;
import project.stock.track.app.vo.catalogs.CatalogsEntity;
import project.stock.track.app.vo.catalogs.CatalogsEntity.CatalogStatusIssue;
import project.stock.track.app.vo.catalogs.CatalogsEntity.CatalogTypeStock;

class IssuesHistoricalRepositoryImplTest extends ProjectJpaTest {
	
	@Autowired
	IssuesHistoricalRepositoryImpl issuesHistoricalRepository;

	@Test
	void testFindAllWithStatusActive() {
		
		Integer idUser = 1;
		
		IssuesHistoricalFilterPojo filterPojo = new IssuesHistoricalFilterPojo();
		filterPojo.setIdSector(idUser);
		filterPojo.setIdStatusIssue(CatalogStatusIssue.ACTIVE);
		filterPojo.setFairValuePriceOverPercentage(5);
		filterPojo.setIdTypeStock(CatalogTypeStock.ISSUE);
		filterPojo.setIsInvest(false);
		filterPojo.setIdIndex(CatalogsEntity.CatalogIndex.SP500);

		List<IssuesManagerEntity> result = issuesHistoricalRepository.findAllWithStatusActive(idUser, 1, 10, filterPojo);
		
		Assessment.assertDataList(result);
	}

}
