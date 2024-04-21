package project.stock.track.app.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import lib.base.backend.test.assessment.Assessment;
import lib.base.backend.utils.ExecuteMethodUtil;
import project.stock.track.ProjectMainTest;
import project.stock.track.app.beans.entity.CatalogIssuesEntity;
import project.stock.track.app.beans.pojos.business.issues.IssuesFiltersPojo;

class IssuesRepositoryImplTest extends ProjectMainTest {
	
	@Autowired
	private IssuesRepositoryImpl issuesRepository;

	@Test
	void testGetIssues() throws Throwable {
		
		ExecuteMethodUtil.execute("EXECUTING testGetIssues", () -> {

			IssuesFiltersPojo filters = new IssuesFiltersPojo();
			filters.setIdSector(1);
			
			List<CatalogIssuesEntity> issues = issuesRepository.findIssues(filters);
			
			assertNotNull(issues);
			Assessment.assertDataList(issues);
		});
	}

}
