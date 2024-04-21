package project.stock.track.services.exchangetrade.impl;

import java.text.ParseException;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import lib.base.backend.utils.date.DateFormatUtil;
import lib.base.backend.utils.date.DateUtil;
import project.stock.track.app.beans.pojos.services.IssueHistoricQueryPojo;
import project.stock.track.app.beans.pojos.services.IssuesLastPriceQueryPojo;
import project.stock.track.app.beans.rest.exchangetrade.IssueHistoryDayBean;
import project.stock.track.app.beans.rest.exchangetrade.service.IssueHistoricMainBean;
import project.stock.track.app.beans.rest.exchangetrade.service.IssueIexMainBean;
import project.stock.track.app.beans.rest.exchangetrade.service.alphavantage.IssueHistoricAlphaVantageBean;
import project.stock.track.app.beans.rest.exchangetrade.service.alphavantage.ShareHistoryDayAlphaVantageBean;
import project.stock.track.app.repository.ConfigControlRepositoryImpl;
import project.stock.track.app.vo.catalogs.CatalogsEntity;
import project.stock.track.app.vo.catalogs.CatalogsStaticData;
import project.stock.track.services.exchangetrade.IssueTrackService;

public class IssueTrackAlphaVantageServiceImpl implements IssueTrackService {
	
	@Autowired
	DateFormatUtil dateFormatUtil;
	
	@Autowired
	DateUtil dateUtil;
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	ConfigControlRepositoryImpl configControlRepositoryImpl;
	
	private static final Logger log = LoggerFactory.getLogger(IssueTrackAlphaVantageServiceImpl.class);

	public IssueHistoricMainBean getIssueHistoric(String token, IssueHistoricQueryPojo issueHistoricQueryPojo ) {
	    
	    MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
	    map.add("function", "TIME_SERIES_DAILY");
		map.add("symbol", issueHistoricQueryPojo.getIssue());
		map.add("outputsize", issueHistoricQueryPojo.getDateFrom() == null || ChronoUnit.DAYS.between(issueHistoricQueryPojo.getDateFrom().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(), new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()) > 100 ? "full" : "compact");
		map.add("apikey", configControlRepositoryImpl.getParameterValue(CatalogsEntity.ConfigControl.API_STOCK_ALPHA_TOKEN).getValue());
		
		String envAlpha = configControlRepositoryImpl.getParameterValue(CatalogsEntity.ConfigControl.API_STOCK_ALPHA_URI).getValue();
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
				
				Date dateKey = null;
				try 
				{
					dateKey = dateFormatUtil.formatDate(entry.getKey(), CatalogsStaticData.ServiceTiingo.DATE_FORMAT_DEFAULT);
				}
				catch(ParseException pe) {
					log.error("Error parsing date", pe);
					continue;
				}
				
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
