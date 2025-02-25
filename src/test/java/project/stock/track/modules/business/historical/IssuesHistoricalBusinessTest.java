package project.stock.track.modules.business.historical;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
import project.stock.track.app.beans.entity.IssuesHistoricalEntityPk;
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
import project.stock.track.app.vo.entities.CatalogBrokerEnum;
import project.stock.track.app.vo.entities.CatalogTypeCurrencyEnum;
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
        dollarHistoricalPriceEntity.setIdDate(LocalDate.of(2023, 11, 14));
        when(dollarHistoricalPriceRespository.findLastRecord()).thenReturn(dollarHistoricalPriceEntity);

        IssueHistoricalEntityPojo issueHistoricalEntityPojo = new IssueHistoricalEntityPojo();
        issueHistoricalEntityPojo.setIssueData(new CatalogIssuesEntityDesPojo());
        when(issueHistoricalHelper.buildIssueHistoricalData(eq(issuesManagerEntity), any(LocalDateTime.class))).thenReturn(issueHistoricalEntityPojo);

        IssuesHistoricalEntity issuesHistoricalEntityLastRecord = new IssuesHistoricalEntity();
        issuesHistoricalEntityLastRecord.setId(new IssuesHistoricalEntityPk(requestPojo.getIdIssue(), LocalDateTime.now()));
        when(issuesHistoricalRepository.findLastRecord(1)).thenReturn(issuesHistoricalEntityLastRecord);

        IssueTransactionResumeTuplePojo issueTransactionResumeTuplePojo = new IssueTransactionResumeTuplePojo();
        issueTransactionResumeTuplePojo.setBuyDate(System.currentTimeMillis());
        issueTransactionResumeTuplePojo.setDescriptionBroker("Broker test");
        issueTransactionResumeTuplePojo.setDescriptionTypeCurrency("currency test");
        issueTransactionResumeTuplePojo.setIdBroker(CatalogBrokerEnum.CHARLES_SCHWAB.getValue());
        issueTransactionResumeTuplePojo.setIdTypeCurrency(CatalogTypeCurrencyEnum.USD.getValue());
        issueTransactionResumeTuplePojo.setPriceBuy(new BigDecimal(10));
        issueTransactionResumeTuplePojo.setTotalShares(new BigDecimal(10));
        
        List<IssueTransactionResumeTuplePojo> issueTransactionResumeTuplePojos = new ArrayList<>();
		issueTransactionResumeTuplePojos.add(issueTransactionResumeTuplePojo);
        when(transactionIssueRepository.findIssueTransactionsResume(1, 1)).thenReturn(issueTransactionResumeTuplePojos);

        GetIssueHistoricalDataPojo result = issuesHistoricalBusiness.executeGetIssueHistorical(requestPojo);

        assertNotNull(result);
        assertEquals(issueHistoricalEntityPojo, result.getIssueHistoricalData());
        verify(userRepository, times(1)).findByUserName(requestPojo.getUserName());
        verify(issueHistoricalHelper, times(1)).buildIssueHistoricalData(eq(issuesManagerEntity), any(LocalDateTime.class));
    }


}
