package project.stock.track.services.exchangetrade.impl;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import lib.base.backend.exception.data.BusinessException;
import lib.base.backend.utils.date.DateFormatUtil;
import lib.base.backend.utils.date.DateUtil;
import lombok.RequiredArgsConstructor;
import project.stock.track.app.beans.pojos.services.IssueHistoricQueryPojo;
import project.stock.track.app.beans.pojos.services.IssuesLastPriceQueryPojo;
import project.stock.track.app.beans.rest.exchangetrade.IssueHistoryDayBean;
import project.stock.track.app.beans.rest.exchangetrade.service.IssueHistoricMainBean;
import project.stock.track.app.beans.rest.exchangetrade.service.IssueIexMainBean;
import project.stock.track.app.beans.rest.exchangetrade.service.tiingo.CryptoTiingoBean;
import project.stock.track.app.beans.rest.exchangetrade.service.tiingo.IssueHistoricTiingoBean;
import project.stock.track.app.beans.rest.exchangetrade.service.tiingo.IssueIexDataTiingoBean;
import project.stock.track.app.beans.rest.exchangetrade.service.tiingo.ShareHistoryDayTiingoBean;
import project.stock.track.app.repository.ConfigControlRepositoryImpl;
import project.stock.track.app.utils.DateFinantialUtil;
import project.stock.track.app.vo.catalogs.CatalogsEntity;
import project.stock.track.app.vo.catalogs.CatalogsErrorMessage;
import project.stock.track.app.vo.catalogs.CatalogsStaticData;
import project.stock.track.services.exchangetrade.IssueTrackService;

@RequiredArgsConstructor
public class IssueTrackTiingoServiceImpl implements IssueTrackService {
	
	private static final Logger log = LoggerFactory.getLogger(IssueTrackTiingoServiceImpl.class);
	
	private DateFormatUtil dateFormatUtil = new DateFormatUtil();
	private DateUtil dateUtil = new DateUtil();
	private DateFinantialUtil dateFinantialUtil = new DateFinantialUtil();
	
	private final RestTemplate restTemplate;
	private final ConfigControlRepositoryImpl configControlRepositoryImpl;
	
	private boolean validateRangeDate(String dateFrom, String dateTo) throws ParseException {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(CatalogsStaticData.ServiceTiingo.DATE_FORMAT_RANGE);
		LocalDate dFrom = LocalDate.parse(dateFrom, formatter);
        LocalDate dTo = LocalDate.parse(dateTo, formatter);

        return dFrom.isBefore(dTo);	 
	}
	
	private static final String MAP_KEY_START_DATE = "startDate";
	private static final String MAP_KEY_END_DATE = "endDate";
	
	public MultiValueMap<String, String> getDataQuery(IssueHistoricQueryPojo issueHistoricQueryPojo, String defaultDate) {
		
		LocalDateTime dateTo = issueHistoricQueryPojo.getDateTo() != null ? issueHistoricQueryPojo.getDateTo() : LocalDateTime.now();
				
		while (dateFinantialUtil.isWeekend(dateTo))
			dateTo = dateTo.minus(1, ChronoUnit.DAYS);
		
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

		map.add(MAP_KEY_START_DATE, issueHistoricQueryPojo.getDateFrom() != null ? dateFormatUtil.formatLocalDate(issueHistoricQueryPojo.getDateFrom().toLocalDate(), CatalogsStaticData.ServiceTiingo.DATE_FORMAT_RANGE) : defaultDate);
		map.add(MAP_KEY_END_DATE, dateFormatUtil.formatLocalDate(dateTo.toLocalDate(), CatalogsStaticData.ServiceTiingo.DATE_FORMAT_RANGE));
		
		return map;
	}
	
	private Map<String, Object> getIssueHistorical(MultiValueMap<String, String> map, IssueHistoricQueryPojo issueHistoricQueryPojo, String token) {
		
		map.add("token", token);
		String envTiingo = configControlRepositoryImpl.getParameterValue(CatalogsEntity.ConfigControl.API_STOCK_TIINGO_HISTORICAL_ISSUE).getValue();
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(envTiingo != null ? envTiingo.replace("<ticker>", issueHistoricQueryPojo.getIssue()) : "")
				.queryParams(map);
		
		if (log.isInfoEnabled())
			log.info(String.format("ISSUETRACKTIINGO. uri: %s", builder.toUriString()));
		
		ShareHistoryDayTiingoBean[] issueHistoricDaysBean = restTemplate.getForObject(builder.toUriString(), ShareHistoryDayTiingoBean[].class);
		
		Map<String, Object> mapResult = new LinkedHashMap<>();
		mapResult.put("uri", builder.toUriString());
		mapResult.put("data", issueHistoricDaysBean);
		
		return mapResult;
	}
	
	private String getTokenForStockType(Boolean isStandardPoors) {
		
		if (Boolean.TRUE.equals(isStandardPoors))
			return configControlRepositoryImpl.getParameterValue(CatalogsEntity.ConfigControl.API_STOCK_TIINGO_TOKEN_ONE).getValue();
		else
			return configControlRepositoryImpl.getParameterValue(CatalogsEntity.ConfigControl.API_STOCK_TIINGO_TOKEN_TWO).getValue();
	}
	
	private  Map<String, Object> getIssueHistorical(MultiValueMap<String, String> map, IssueHistoricQueryPojo issueHistoricQueryPojo) {
		
		String token = getTokenForStockType(issueHistoricQueryPojo.getIsStandarPoors());
		
		return getIssueHistorical(map, issueHistoricQueryPojo, token);
	}

	@Override
	public IssueHistoricMainBean getIssueHistoric(String token, IssueHistoricQueryPojo issueHistoricQueryPojo ) throws BusinessException {
	    
	    MultiValueMap<String, String> map = getDataQuery(issueHistoricQueryPojo, "2018-01-01");

	    try 
	    {
			if (!validateRangeDate(map.get(MAP_KEY_START_DATE).get(0), (map.get(MAP_KEY_END_DATE).get(0))))
					return null;
		} catch (ParseException e) {
			throw new BusinessException(CatalogsErrorMessage.getErrorMsgExchangeTradeBadRangeDate(issueHistoricQueryPojo.getIssue(), map.get(MAP_KEY_START_DATE).get(0), map.get(MAP_KEY_END_DATE).get(0)));
		}
	    
	    Map<String, Object> mapIssuesData = getIssueHistorical(map, issueHistoricQueryPojo);
	    ShareHistoryDayTiingoBean[] issueHistoricDaysBean = (ShareHistoryDayTiingoBean[]) mapIssuesData.get("data");
		String uri = (String) mapIssuesData.get("uri");
		
		IssueHistoricTiingoBean issueHistoricTiingoBean = new IssueHistoricTiingoBean();
		issueHistoricTiingoBean.setUri(uri);
		issueHistoricTiingoBean.setName(issueHistoricQueryPojo.getIssue());
		issueHistoricTiingoBean.setHistory(Arrays.asList(issueHistoricDaysBean));
		
		Map<String, IssueHistoryDayBean> issueHistoricalDays = new LinkedHashMap<>();
		
		for (ShareHistoryDayTiingoBean issueHistoryDayTiingoBean: issueHistoricTiingoBean.getHistory()) {
			
			LocalDateTime dateTime = LocalDateTime.parse(issueHistoryDayTiingoBean.getDate().replace("Z", ""));
			String date = dateFormatUtil.formatLocalDateTime(dateTime, CatalogsStaticData.ServiceTiingo.DATE_FORMAT_DEFAULT);
			
			LocalDateTime dateKey = dateFormatUtil.formatLocalDateTime(date, CatalogsStaticData.ServiceTiingo.DATE_FORMAT_DEFAULT);
		
			if (issueHistoricQueryPojo.getDateFrom() == null || dateUtil.compareDatesNotTime(dateKey, issueHistoricQueryPojo.getDateFrom()) >= 0) {
				
				IssueHistoryDayBean issueHistoyDayBean = new IssueHistoryDayBean();
				issueHistoyDayBean.setOpen(issueHistoryDayTiingoBean.getOpen());
				issueHistoyDayBean.setClose(issueHistoryDayTiingoBean.getClose());
				issueHistoyDayBean.setHigh(issueHistoryDayTiingoBean.getHigh());
				issueHistoyDayBean.setLow(issueHistoryDayTiingoBean.getLow());
				issueHistoyDayBean.setVolume(issueHistoryDayTiingoBean.getVolume());
				
				issueHistoricalDays.put(date, issueHistoyDayBean);
				
			}
		}
		
		IssueHistoricMainBean issueHistoricMainBean = new IssueHistoricMainBean();
		issueHistoricMainBean.setHistory(issueHistoricalDays);
		issueHistoricMainBean.setUri(uri);
		issueHistoricMainBean.setName(issueHistoricQueryPojo.getIssue());
	
		return issueHistoricMainBean;
	}
	
	private List<IssueIexDataTiingoBean> getIssudeDataBeans(MultiValueMap<String, String> map, List<String> issues) {
		
		String envTiingo = configControlRepositoryImpl.getParameterValue(CatalogsEntity.ConfigControl.API_STOCK_TIINGO_IEX).getValue();
		
		if (envTiingo == null)
				envTiingo = "";
		
		int totalSubList = 300;
		int limitSublist = 0;
		int startSublist = 0;
		boolean breakWhile = false;
		
		List<List<String>> issuesSubLists = new ArrayList<>();
		List<IssueIexDataTiingoBean> issuesBeanList = new ArrayList<>();
		
		do {
			
			if ((startSublist + totalSubList) >= issues.size()) {
				limitSublist = issues.size();
				breakWhile = true;
			}
			else
				limitSublist += totalSubList;
			
			issuesSubLists.add(issues.subList(startSublist, limitSublist));
			
			startSublist += totalSubList;
			
		}while(!breakWhile);
		
		for (List<String> issuesSubList: issuesSubLists) {
			
			map.remove("tickers");
			map.add("tickers", String.join(",", issuesSubList));
			
			UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(envTiingo).queryParams(map);
			
			if (log.isInfoEnabled())
				log.info(String.format("ISSUES LAST PRICE. uri: %s", builder.toUriString()));
			
			IssueIexDataTiingoBean[] issueIexDataTiingoBeans = restTemplate.getForObject(builder.toUriString(), IssueIexDataTiingoBean[].class);
			Collections.addAll(issuesBeanList, issueIexDataTiingoBeans);
		}
		
		return issuesBeanList;
	}
	
	@Override
	public Map<String, IssueIexMainBean> getIssuesLastPrice(String token, IssuesLastPriceQueryPojo issuesLastPriceQueryPojo ) {
		
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("token", getTokenForStockType(issuesLastPriceQueryPojo.getIsStandardPoors()));
		
		List<IssueIexDataTiingoBean> issueIexDataTiingoBeans = getIssudeDataBeans(map, issuesLastPriceQueryPojo.getIssues());
		
		Map<String, IssueIexMainBean> issueIexDataTiingoMap = new LinkedHashMap<>();
		
		for (IssueIexDataTiingoBean issueIexDataTiingoBean: issueIexDataTiingoBeans) {
			
			IssueIexMainBean issueIexMainBean = new IssueIexMainBean();
			issueIexMainBean.setHigh(issueIexDataTiingoBean.getHigh());
			issueIexMainBean.setTngoLast(issueIexDataTiingoBean.getTngoLast());
			issueIexMainBean.setLastSaleTimestamp(issueIexDataTiingoBean.getLastSaleTimestamp());
			issueIexMainBean.setOpen(issueIexDataTiingoBean.getOpen());
			issueIexMainBean.setPrevClose(issueIexDataTiingoBean.getPrevClose());
			issueIexMainBean.setTicker(issueIexDataTiingoBean.getTicker());
			issueIexMainBean.setTimestamp(issueIexDataTiingoBean.getTimestamp());
			issueIexMainBean.setTngoLast(issueIexDataTiingoBean.getTngoLast());
			issueIexMainBean.setVolume(issueIexDataTiingoBean.getVolume());
			
			issueIexDataTiingoMap.put(issueIexDataTiingoBean.getTicker(), issueIexMainBean);
		}
		
		return issueIexDataTiingoMap;
	}

	@Override
	public IssueHistoricMainBean getCryptoHistoric(String token, IssueHistoricQueryPojo issueHistoricQueryPojo) {

		MultiValueMap<String, String> map = getDataQuery(issueHistoricQueryPojo, "2018-01-01");
		map.add("resampleFreq", "1day");
		
		String envTiingo = configControlRepositoryImpl.getParameterValue(CatalogsEntity.ConfigControl.API_STOCK_TIINGO_HISTORICAL_CRYPTO).getValue();
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(envTiingo != null ? envTiingo.replace("<ticker>", issueHistoricQueryPojo.getIssue()) : "")
				.queryParams(map);
		
		if (log.isInfoEnabled())
			log.info(String.format("ISSUETRACKTIINGO CRYPTO. uri: %s", builder.toUriString()));
		
		CryptoTiingoBean[] cryptoHistoricDaysBean = restTemplate.getForObject(builder.toUriString(), CryptoTiingoBean[].class);
		
		IssueHistoricTiingoBean issueHistoricTiingoBean = new IssueHistoricTiingoBean();
		issueHistoricTiingoBean.setUri(builder.toUriString());
		issueHistoricTiingoBean.setName(issueHistoricQueryPojo.getIssue());
		issueHistoricTiingoBean.setHistory((cryptoHistoricDaysBean != null && cryptoHistoricDaysBean.length == 1) ? cryptoHistoricDaysBean[0].getHistory() : null);
		
		Map<String, IssueHistoryDayBean> cryptoHistoricalDays = new LinkedHashMap<>();
		
		for (ShareHistoryDayTiingoBean cryptoHistoryDayTiingoBean: issueHistoricTiingoBean.getHistory()) {
			
			LocalDateTime dateKey = dateFormatUtil.formatLocalDateTime(cryptoHistoryDayTiingoBean.getDate(), CatalogsStaticData.ServiceTiingo.DATE_FORMAT_DEFAULT);
		
			if (issueHistoricQueryPojo.getDateFrom() == null || dateUtil.compareDatesNotTime(dateKey, issueHistoricQueryPojo.getDateFrom()) >= 0) {
				
				IssueHistoryDayBean issueHistoryDayBean = new IssueHistoryDayBean();
				issueHistoryDayBean.setOpen(cryptoHistoryDayTiingoBean.getOpen());
				issueHistoryDayBean.setClose(cryptoHistoryDayTiingoBean.getClose());
				issueHistoryDayBean.setHigh(cryptoHistoryDayTiingoBean.getHigh());
				issueHistoryDayBean.setLow(cryptoHistoryDayTiingoBean.getLow());
				issueHistoryDayBean.setVolume(cryptoHistoryDayTiingoBean.getVolume());
				
				cryptoHistoricalDays.put(cryptoHistoryDayTiingoBean.getDate(), issueHistoryDayBean);
				
			}
		}
		
		IssueHistoricMainBean issueHistoricMainBean = new IssueHistoricMainBean();
		issueHistoricMainBean.setHistory(cryptoHistoricalDays);
		issueHistoricMainBean.setUri(builder.toUriString());
		issueHistoricMainBean.setName(issueHistoricQueryPojo.getIssue());
	
		return issueHistoricMainBean;
	}
	
}
