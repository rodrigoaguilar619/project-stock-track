package project.stock.track.services.exchangetrade.impl;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import lib.base.backend.utils.date.DateFormatUtil;
import lib.base.backend.utils.date.DateUtil;
import lombok.RequiredArgsConstructor;
import project.stock.track.app.beans.pojos.services.IssueHistoricQueryPojo;
import project.stock.track.app.beans.pojos.services.IssuesLastPriceQueryPojo;
import project.stock.track.app.beans.rest.exchangetrade.IssueHistoryDayBean;
import project.stock.track.app.beans.rest.exchangetrade.service.IssueHistoricMainBean;
import project.stock.track.app.beans.rest.exchangetrade.service.IssueIexMainBean;
import project.stock.track.app.beans.rest.exchangetrade.service.alphavantage.IssueHistoricAlphaVantageBean;
import project.stock.track.app.beans.rest.exchangetrade.service.alphavantage.ShareHistoryDayAlphaVantageBean;
import project.stock.track.app.repository.ConfigControlRepositoryImpl;
import project.stock.track.app.vo.catalogs.CatalogsStaticData;
import project.stock.track.app.vo.entities.ConfigControlEnum;
import project.stock.track.services.exchangetrade.IssueTrackService;

@RequiredArgsConstructor
public class IssueTrackAlphaVantageServiceImpl implements IssueTrackService {
	
	private DateFormatUtil dateFormatUtil = new DateFormatUtil();
	private DateUtil dateUtil = new DateUtil();
	
	private final RestTemplate restTemplate;
	private final ConfigControlRepositoryImpl configControlRepositoryImpl;

	public IssueHistoricMainBean getIssueHistoric(String token, IssueHistoricQueryPojo issueHistoricQueryPojo ) {
	    
	    MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
	    map.add("function", "TIME_SERIES_DAILY");
		map.add("symbol", issueHistoricQueryPojo.getIssue());
		map.add("apikey", configControlRepositoryImpl.getParameterValue(ConfigControlEnum.API_STOCK_ALPHA_TOKEN.getValue()).getValue());
		
		String envAlpha = configControlRepositoryImpl.getParameterValue(ConfigControlEnum.API_STOCK_ALPHA_URI.getValue()).getValue();
		if (envAlpha == null)
				envAlpha = "";
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(envAlpha)
				.queryParams(map);
		
		IssueHistoricAlphaVantageBean issueHistoricAlphaVantageBean = restTemplate.getForObject( builder.toUriString(), IssueHistoricAlphaVantageBean.class);
		
		if (issueHistoricAlphaVantageBean != null)
			issueHistoricAlphaVantageBean.setUri(builder.toUriString());
		
		Map<String, IssueHistoryDayBean> issueHistoricalDays = new LinkedHashMap<>();
		
		if (issueHistoricAlphaVantageBean != null) {
			
			for(Map.Entry<String, ShareHistoryDayAlphaVantageBean> entry : new TreeMap<String, ShareHistoryDayAlphaVantageBean>(issueHistoricAlphaVantageBean.getHistory()).entrySet()) {
				
				LocalDateTime dateKey = dateFormatUtil.formatLocalDateTime(entry.getKey(), CatalogsStaticData.ServiceTiingo.DATE_FORMAT_DEFAULT);

				if (issueHistoricQueryPojo.getDateFrom() == null || dateUtil.compareDatesNotTime(dateKey, issueHistoricQueryPojo.getDateFrom()) > 0) {
				
					IssueHistoryDayBean issueHistoyDayBean = new IssueHistoryDayBean();
					issueHistoyDayBean.setOpen(entry.getValue().getOpen());
					issueHistoyDayBean.setClose(entry.getValue().getClose());
					issueHistoyDayBean.setHigh(entry.getValue().getHigh());
					issueHistoyDayBean.setLow(entry.getValue().getLow());
					issueHistoyDayBean.setVolume(entry.getValue().getVolume());
					
					issueHistoricalDays.put(entry.getKey(), issueHistoyDayBean);
					
				}
			}
		}
		
		IssueHistoricMainBean issueHistoricMainBean = new IssueHistoricMainBean();
		issueHistoricMainBean.setHistory(issueHistoricalDays);
		issueHistoricMainBean.setUri(builder.toUriString());
		issueHistoricMainBean.setName(issueHistoricQueryPojo.getIssue());
	
		return issueHistoricMainBean;
	}

	@Override
	public Map<String, IssueIexMainBean> getIssuesLastPrice(String token,
			IssuesLastPriceQueryPojo issuesLastPriceQueryPojo) {
		return new LinkedHashMap<>();
	}

	@Override
	public IssueHistoricMainBean getCryptoHistoric(String token, IssueHistoricQueryPojo issueHistoricQueryPojo) {
		return null;
	}
}
