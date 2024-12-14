package project.stock.track.services.exchangetrade.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import lib.base.backend.exception.data.BusinessException;
import lib.base.backend.modules.security.jwt.entity.ConfigControlEntity;
import project.stock.track.app.beans.pojos.services.IssueHistoricQueryPojo;
import project.stock.track.app.beans.pojos.services.IssuesLastPriceQueryPojo;
import project.stock.track.app.beans.rest.exchangetrade.service.IssueHistoricMainBean;
import project.stock.track.app.beans.rest.exchangetrade.service.IssueIexMainBean;
import project.stock.track.app.beans.rest.exchangetrade.service.tiingo.CryptoTiingoBean;
import project.stock.track.app.beans.rest.exchangetrade.service.tiingo.IssueIexDataTiingoBean;
import project.stock.track.app.beans.rest.exchangetrade.service.tiingo.ShareHistoryDayTiingoBean;
import project.stock.track.app.repository.ConfigControlRepositoryImpl;

class IssueTrackTiingoServiceImplTest {
	
	@InjectMocks
	IssueTrackTiingoServiceImpl issueTrackTiingoService;
	
	@Mock
	RestTemplate restTemplate;
	
	@Mock
	ConfigControlRepositoryImpl configControlRepository;
	
	@BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

	/*@Test
	void testGetDataQuery() {
		fail("Not yet implemented");
	}*/

	@SuppressWarnings({ "unchecked" })
	@Test
	void testGetIssueHistoric() throws BusinessException {
		
		String token = "Token test";
		
		ShareHistoryDayTiingoBean shareHistoryDayTiingoBean1 = new ShareHistoryDayTiingoBean();
		shareHistoryDayTiingoBean1.setClose("20");
		shareHistoryDayTiingoBean1.setDate("2024-04-01 00:00:00");
		
		ShareHistoryDayTiingoBean shareHistoryDayTiingoBean2 = new ShareHistoryDayTiingoBean();
		shareHistoryDayTiingoBean2.setClose("25");
		shareHistoryDayTiingoBean2.setDate("2024-04-02 00:00:00");
		
		ShareHistoryDayTiingoBean[] issueHistoricDaysBean = new ShareHistoryDayTiingoBean[2];
		issueHistoricDaysBean[0] = shareHistoryDayTiingoBean1;
		issueHistoricDaysBean[1] = shareHistoryDayTiingoBean2;
		
		when(restTemplate.getForObject(anyString(), any(Class.class))).thenReturn(issueHistoricDaysBean);
		
		ConfigControlEntity configControlEntity = new ConfigControlEntity();
		configControlEntity.setId(6);
		configControlEntity.setValue("https://api.tiingo.com/tiingo/daily/<ticker>/prices");
		
		when(configControlRepository.getParameterValue(anyString())).thenReturn(configControlEntity);

		IssueHistoricQueryPojo issueHistoricQueryPojo = new IssueHistoricQueryPojo();
		issueHistoricQueryPojo.setDateTo(LocalDateTime.of(2024, 6, 1, 0, 0, 0, 0));
		issueHistoricQueryPojo.setIssue("AAL");
		
		IssueHistoricMainBean issueHistoricMainBean = issueTrackTiingoService.getIssueHistoric(token, issueHistoricQueryPojo);
		
		assertNotNull(issueHistoricMainBean);
	}

	@Test
	void testGetIssuesLastPrice() {
		
		String token = "Token test";
		
		IssueIexDataTiingoBean issueIexDataTiingoBean1 = new IssueIexDataTiingoBean();
		issueIexDataTiingoBean1.setLast(new BigDecimal(10));
		issueIexDataTiingoBean1.setTicker("AAL");
		issueIexDataTiingoBean1.setLastSaleTimestamp(OffsetDateTime.now());
		
		IssueIexDataTiingoBean issueIexDataTiingoBean2 = new IssueIexDataTiingoBean();
		issueIexDataTiingoBean2.setLast(new BigDecimal(20));
		issueIexDataTiingoBean2.setTicker("CCL");
		issueIexDataTiingoBean2.setLastSaleTimestamp(OffsetDateTime.now());
		
		IssueIexDataTiingoBean[] issueIexDataTiingoBeans = new IssueIexDataTiingoBean[2];
		issueIexDataTiingoBeans[0] = issueIexDataTiingoBean1;
		issueIexDataTiingoBeans[1] = issueIexDataTiingoBean2;

		when(restTemplate.getForObject(anyString(), eq(IssueIexDataTiingoBean[].class))).thenReturn(issueIexDataTiingoBeans);
		
		ConfigControlEntity configControlEntity = new ConfigControlEntity();
		configControlEntity.setId(6);
		configControlEntity.setValue("https://api.tiingo.com/tiingo/daily/<ticker>/prices");
		
		when(configControlRepository.getParameterValue(anyString())).thenReturn(configControlEntity);
		
		List<String> issues = new ArrayList<>(Arrays.asList(issueIexDataTiingoBean1.getTicker(), issueIexDataTiingoBean2.getTicker()));
		
		IssuesLastPriceQueryPojo issuesLastPriceQueryPojo = new IssuesLastPriceQueryPojo();
		issuesLastPriceQueryPojo.setIssues(issues);
		
		Map<String, IssueIexMainBean> mapResult = issueTrackTiingoService.getIssuesLastPrice(token, issuesLastPriceQueryPojo);
		
		assertNotNull(mapResult);
	}

	@Test
	void testGetCryptoHistoric() {

		String token = "Token test";
		
		ShareHistoryDayTiingoBean shareHistoryDayTiingoBean1 = new ShareHistoryDayTiingoBean();
		shareHistoryDayTiingoBean1.setClose("20");
		shareHistoryDayTiingoBean1.setDate("2024-04-01 00:00:00");
		
		ShareHistoryDayTiingoBean shareHistoryDayTiingoBean2 = new ShareHistoryDayTiingoBean();
		shareHistoryDayTiingoBean2.setClose("25");
		shareHistoryDayTiingoBean2.setDate("2024-04-02 00:00:00");
		
		List<ShareHistoryDayTiingoBean> issueHistoricDaysBeans = new ArrayList<>(Arrays.asList(shareHistoryDayTiingoBean1, shareHistoryDayTiingoBean2));
		
		CryptoTiingoBean cryptoTiingoBean = new CryptoTiingoBean();
		cryptoTiingoBean.setBaseCurrency("MXN");
		cryptoTiingoBean.setTicker("BTC");
		cryptoTiingoBean.setHistory(issueHistoricDaysBeans);
		
		CryptoTiingoBean[] cryptoTiingoBeans = new CryptoTiingoBean[1];
		cryptoTiingoBeans[0] = cryptoTiingoBean;
		
		when(restTemplate.getForObject(anyString(), eq(CryptoTiingoBean[].class))).thenReturn(cryptoTiingoBeans);
		
		ConfigControlEntity configControlEntity = new ConfigControlEntity();
		configControlEntity.setId(6);
		configControlEntity.setValue("https://api.tiingo.com/tiingo/crypto/prices?tickers=<ticker>");
		
		when(configControlRepository.getParameterValue(anyString())).thenReturn(configControlEntity);

		IssueHistoricQueryPojo issueHistoricQueryPojo = new IssueHistoricQueryPojo();
		issueHistoricQueryPojo.setDateTo(LocalDateTime.of(2024, 6, 1, 0, 0, 0, 0));
		issueHistoricQueryPojo.setIssue("BTC");
		
		IssueHistoricMainBean issueHistoricMainBean = issueTrackTiingoService.getCryptoHistoric(token, issueHistoricQueryPojo);
		
		assertNotNull(issueHistoricMainBean);
	}

}
