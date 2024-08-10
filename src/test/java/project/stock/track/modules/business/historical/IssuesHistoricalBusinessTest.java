package project.stock.track.modules.business.historical;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import lib.base.backend.modules.security.jwt.entity.UserEntity;
import lib.base.backend.modules.security.jwt.repository.UserRepositoryImpl;
import lib.base.backend.persistance.GenericPersistence;
import project.stock.track.ProjectUnitTest;
import project.stock.track.app.beans.entity.CatalogIssuesEntity;
import project.stock.track.app.beans.entity.DollarHistoricalPriceEntity;
import project.stock.track.app.beans.entity.IssuesHistoricalEntity;
import project.stock.track.app.beans.entity.IssuesHistoricalEntityId;
import project.stock.track.app.beans.entity.IssuesLastPriceTmpEntity;
import project.stock.track.app.beans.entity.IssuesManagerEntity;
import project.stock.track.app.beans.entity.IssuesManagerEntityPk;
import project.stock.track.app.beans.pojos.entity.CatalogIssuesEntityDesPojo;
import project.stock.track.app.beans.pojos.entity.IssueHistoricalEntityPojo;
import project.stock.track.app.beans.pojos.petition.data.GetIssueHistoricalDataPojo;
import project.stock.track.app.beans.pojos.petition.request.GetIssueHistoricalRequestPojo;
import project.stock.track.app.beans.pojos.tuple.IssueTransactionResumeTuplePojo;
import project.stock.track.app.repository.DollarHistoricalPriceRepositoryImpl;
import project.stock.track.app.repository.IssuesHistoricalRepositoryImpl;
import project.stock.track.app.repository.IssuesManagerRepositoryImpl;
import project.stock.track.app.repository.TransactionIssueRepositoryImpl;
import project.stock.track.config.helpers.IssueHistoricalHelper;

@SuppressWarnings("unchecked")
class IssuesHistoricalBusinessTest extends ProjectUnitTest {

	@InjectMocks
    private IssuesHistoricalBusiness issuesHistoricalBusiness;
	
	@SuppressWarnings("rawtypes")
	@Mock
	private GenericPersistence genericPersistance;
	
	@Mock
	private UserRepositoryImpl userRepository;

    @Mock
    private IssueHistoricalHelper issueHistoricalHelper;

    @Mock
    private IssuesManagerRepositoryImpl issuesManagerRepository;

    @Mock
    private IssuesHistoricalRepositoryImpl issuesHistoricalRepository;

    @Mock
    private TransactionIssueRepositoryImpl transactionIssueRepository;

    @Mock
    private DollarHistoricalPriceRepositoryImpl dollarHistoricalPriceRespository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @SuppressWarnings({ "deprecation" })
	@Test
    void testExecuteGetIssueHistorical() {

        GetIssueHistoricalRequestPojo requestPojo = new GetIssueHistoricalRequestPojo();
        requestPojo.setUserName("ADMIN");
        requestPojo.setIdIssue(1);

        UserEntity userEntity = new UserEntity();
        userEntity.setId(1);
        when(userRepository.findByUserName(requestPojo.getUserName())).thenReturn(userEntity);

        IssuesLastPriceTmpEntity issuesLastPriceTmpEntity = new IssuesLastPriceTmpEntity();
        issuesLastPriceTmpEntity.setLast(new BigDecimal(20));
        
        CatalogIssuesEntity catalogIssuesEntity = new CatalogIssuesEntity(requestPojo.getIdIssue());
        catalogIssuesEntity.setTempIssuesLastPriceEntity(issuesLastPriceTmpEntity);
        IssuesManagerEntity issuesManagerEntity = new IssuesManagerEntity(requestPojo.getIdIssue(), userEntity.getId());
        issuesManagerEntity.setCatalogIssueEntity(catalogIssuesEntity);
        when(genericPersistance.findById(IssuesManagerEntity.class, new IssuesManagerEntityPk(requestPojo.getIdIssue(), userEntity.getId()))).thenReturn(issuesManagerEntity);

        DollarHistoricalPriceEntity dollarHistoricalPriceEntity = new DollarHistoricalPriceEntity();
        dollarHistoricalPriceEntity.setPrice(BigDecimal.valueOf(20.0));
        dollarHistoricalPriceEntity.setIdDate(new Date(1980, Calendar.JANUARY, 1));
        when(dollarHistoricalPriceRespository.findLastRecord()).thenReturn(dollarHistoricalPriceEntity);

        IssueHistoricalEntityPojo issueHistoricalEntityPojo = new IssueHistoricalEntityPojo();
        issueHistoricalEntityPojo.setIssueData(new CatalogIssuesEntityDesPojo());
        when(issueHistoricalHelper.buildIssueHistoricalData(eq(issuesManagerEntity), any(Date.class))).thenReturn(issueHistoricalEntityPojo);

        IssuesHistoricalEntity issuesHistoricalEntityLastRecord = new IssuesHistoricalEntity();
        issuesHistoricalEntityLastRecord.setIssuesHistoricalEntityId(new IssuesHistoricalEntityId(requestPojo.getIdIssue(), new Date()));
        when(issuesHistoricalRepository.findLastRecord(1)).thenReturn(issuesHistoricalEntityLastRecord);

        List<IssueTransactionResumeTuplePojo> issueTransactionResumeTuplePojos = new ArrayList<>();
        when(transactionIssueRepository.findIssueTransactionsResume(1, 1)).thenReturn(issueTransactionResumeTuplePojos);

        GetIssueHistoricalDataPojo result = issuesHistoricalBusiness.executeGetIssueHistorical(requestPojo);

        assertNotNull(result);
        assertEquals(issueHistoricalEntityPojo, result.getIssueHistoricalData());
        verify(userRepository, times(1)).findByUserName(requestPojo.getUserName());
        verify(issueHistoricalHelper, times(1)).buildIssueHistoricalData(eq(issuesManagerEntity), any(Date.class));
    }


}
