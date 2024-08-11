package project.stock.track.services.exchangetrade.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.LinkedHashMap;
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
import project.stock.track.app.beans.rest.exchangetrade.service.IssueHistoricMainBean;
import project.stock.track.app.beans.rest.exchangetrade.service.alphavantage.IssueHistoricAlphaVantageBean;
import project.stock.track.app.beans.rest.exchangetrade.service.alphavantage.ShareHistoryDayAlphaVantageBean;
import project.stock.track.app.repository.ConfigControlRepositoryImpl;

class IssueTrackAlphaVantageServiceImplTest {
	
	@InjectMocks
	IssueTrackAlphaVantageServiceImpl issueTrackAlphaVantageService;
	
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

	@SuppressWarnings({ "deprecation" })
	@Test
	void testGetIssueHistoric() throws BusinessException {
		
		String token = "Token test";
		
		ShareHistoryDayAlphaVantageBean shareHistoryDayAlphaVantageBean1 = new ShareHistoryDayAlphaVantageBean();
		shareHistoryDayAlphaVantageBean1.setClose("20");
		shareHistoryDayAlphaVantageBean1.setDate("2024-04-01");
		
		ShareHistoryDayAlphaVantageBean shareHistoryDayAlphaVantageBean2 = new ShareHistoryDayAlphaVantageBean();
		shareHistoryDayAlphaVantageBean2.setClose("25");
		shareHistoryDayAlphaVantageBean2.setDate("2024-04-02");
		
		ShareHistoryDayAlphaVantageBean[] issueHistoricDaysBean = new ShareHistoryDayAlphaVantageBean[2];
		issueHistoricDaysBean[0] = shareHistoryDayAlphaVantageBean1;
		issueHistoricDaysBean[1] = shareHistoryDayAlphaVantageBean2;
		
		Map<String, ShareHistoryDayAlphaVantageBean> map = new LinkedHashMap<>();
		map.put("2024-04-01", shareHistoryDayAlphaVantageBean1);
		
		IssueHistoricAlphaVantageBean issueHistoricAlphaVantageBean = new IssueHistoricAlphaVantageBean();
		issueHistoricAlphaVantageBean.setName("Name test");
		issueHistoricAlphaVantageBean.setHistory(map);
		issueHistoricAlphaVantageBean.setUri("https://www.alphavantage.co/query");
		
		when(restTemplate.getForObject(anyString(), eq(IssueHistoricAlphaVantageBean.class))).thenReturn(issueHistoricAlphaVantageBean);
		
		ConfigControlEntity configControlEntity = new ConfigControlEntity();
		configControlEntity.setId(6);
		configControlEntity.setValue("https://www.alphavantage.co/query");
		
		when(configControlRepository.getParameterValue(anyString())).thenReturn(configControlEntity);

		IssueHistoricQueryPojo issueHistoricQueryPojo = new IssueHistoricQueryPojo();
		issueHistoricQueryPojo.setDateTo(new Date(2024, 6, 1));
		issueHistoricQueryPojo.setIssue("AAL");
		
		IssueHistoricMainBean issueHistoricMainBean = issueTrackAlphaVantageService.getIssueHistoric(token, issueHistoricQueryPojo);
		
		assertNotNull(issueHistoricMainBean);
	}

}
