package project.stock.track.modules.business.issues;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lib.base.backend.utils.date.DateUtil;
import project.stock.track.app.beans.entity.CatalogIssuesEntity;
import project.stock.track.app.beans.entity.IssuesLastPriceTmpEntity;
import project.stock.track.app.beans.pojos.entity.IssueLastPriceTmpEntityPojo;
import project.stock.track.app.beans.pojos.petition.data.GetTempIssuesLastPriceDataPojo;
import project.stock.track.app.beans.pojos.services.IssuesLastPriceQueryPojo;
import project.stock.track.app.beans.rest.exchangetrade.service.IssueIexMainBean;
import project.stock.track.app.repository.IssuesManagerRepositoryImpl;
import project.stock.track.app.repository.IssuesRepositoryImpl;
import project.stock.track.app.repository.TempIssuesLastPriceRepositoryImpl;
import project.stock.track.app.utils.CalculatorUtil;
import project.stock.track.app.utils.DateFinantialUtil;
import project.stock.track.app.vo.catalogs.CatalogsEntity;
import project.stock.track.modules.business.MainBusiness;
import project.stock.track.services.exchangetrade.IssueTrackService;

@Component
public class IssuesLastPriceBusiness extends MainBusiness {
	
	IssueTrackService issueTrackService;
	
	IssuesManagerRepositoryImpl issuesManagerRepository;
	
	TempIssuesLastPriceRepositoryImpl tempIssuesLastPriceRepository;
	
	IssuesRepositoryImpl issuesRepository;
	
	DateUtil dateUtil;
	
	CalculatorUtil calculatorUtil;
	
	@Autowired
	public IssuesLastPriceBusiness( IssuesManagerRepositoryImpl issuesManagerRepository, TempIssuesLastPriceRepositoryImpl tempIssuesLastPriceRepository,
			IssuesRepositoryImpl issuesRepository, CalculatorUtil calculatorUtil, IssueTrackService issueTrackService) {
		this.issuesManagerRepository = issuesManagerRepository;
		this.tempIssuesLastPriceRepository = tempIssuesLastPriceRepository;
		this.issuesRepository = issuesRepository;
		this.calculatorUtil = calculatorUtil;
		this.issueTrackService = issueTrackService;
		
	}
	
	@SuppressWarnings("unchecked")
	public void storeIssuesLastPrice() {

		tempIssuesLastPriceRepository.deleteTempIssuesLastPrice();
		
		List<CatalogIssuesEntity> catalogIssuesEntities = issuesRepository.findAll(CatalogsEntity.CatalogStatusIssue.ACTIVE, CatalogsEntity.CatalogTypeStock.ISSUE);
		
		List<String> issuesStandardPoorsList = new ArrayList<>();
		List<String> issuesOthersList = new ArrayList<>();
		
		for (CatalogIssuesEntity catalogIssuesEntity: catalogIssuesEntities) {
			
			if (Boolean.TRUE.equals(catalogIssuesEntity.getIsSp500()))
				issuesStandardPoorsList.add(catalogIssuesEntity.getInitials());
			else
				issuesOthersList.add(catalogIssuesEntity.getInitials());
		}
		
		IssuesLastPriceQueryPojo issuesLastPriceQueryStandardPoorsPojo = new IssuesLastPriceQueryPojo();
		issuesLastPriceQueryStandardPoorsPojo.setIssues(issuesStandardPoorsList);
		issuesLastPriceQueryStandardPoorsPojo.setIsStandardPoors(true);
		
		IssuesLastPriceQueryPojo issuesLastPriceQueryOthersPojo = new IssuesLastPriceQueryPojo();
		issuesLastPriceQueryOthersPojo.setIssues(issuesOthersList);
		issuesLastPriceQueryOthersPojo.setIsStandardPoors(false);
		
		Map<String, IssueIexMainBean> issuesLastPriceOthersMap = issueTrackService.getIssuesLastPrice(null, issuesLastPriceQueryOthersPojo);
		Map<String, IssueIexMainBean> issuesLastPriceStandardPoorsMap = issueTrackService.getIssuesLastPrice(null, issuesLastPriceQueryStandardPoorsPojo);
		
		Map<String, IssueIexMainBean> issuesLastPriceMap = new LinkedHashMap<>();
		issuesLastPriceMap.putAll(issuesLastPriceStandardPoorsMap);
		issuesLastPriceMap.putAll(issuesLastPriceOthersMap);
		
		IssuesLastPriceTmpEntity tempIssuesLastPriceEntity = null;
		
		for (CatalogIssuesEntity catalogIssuesEntity: catalogIssuesEntities) {
			
			IssueIexMainBean issueIexMainBean = issuesLastPriceMap.get(catalogIssuesEntity.getInitials());
			
			if(issueIexMainBean == null)
				continue;
			
			tempIssuesLastPriceEntity = new IssuesLastPriceTmpEntity();
			tempIssuesLastPriceEntity.setHigh(issueIexMainBean.getHigh());
			tempIssuesLastPriceEntity.setIdIssue(catalogIssuesEntity.getId());
			tempIssuesLastPriceEntity.setLast(issueIexMainBean.getLast());
			tempIssuesLastPriceEntity.setLastSaleTimestamp(issueIexMainBean.getLastSaleTimestamp());
			tempIssuesLastPriceEntity.setOpen(issueIexMainBean.getOpen());
			tempIssuesLastPriceEntity.setPrevClose(issueIexMainBean.getPrevClose());
			tempIssuesLastPriceEntity.setTimestamp(issueIexMainBean.getTimestamp());
			tempIssuesLastPriceEntity.setVolume(issueIexMainBean.getVolume());
			tempIssuesLastPriceEntity.setCatalogIssuesEntity(catalogIssuesEntity);
			
			genericPersistance.save(tempIssuesLastPriceEntity);
		}
	}
	
	public List<IssueLastPriceTmpEntityPojo> getIssuesLastPrices() {
		
		List<IssuesLastPriceTmpEntity> tempIssuesLastPriceEntities = tempIssuesLastPriceRepository.findLastPrices(dateUtil.getDateWithoutTime(new Date()));
		
		List<IssueLastPriceTmpEntityPojo> tempIssueLastPricePojos = new ArrayList<>();
		
		for (IssuesLastPriceTmpEntity tempIssuesLastPriceEntity: tempIssuesLastPriceEntities) {
			
			IssueLastPriceTmpEntityPojo tempIssueLastPricePojo = mapEntityToPojoUtil.mapTempIssueLastPrice(new IssueLastPriceTmpEntityPojo(), tempIssuesLastPriceEntity);
			tempIssueLastPricePojos.add(tempIssueLastPricePojo);
		}
		
		return tempIssueLastPricePojos;
	}
	
	public Map<String, IssueLastPriceTmpEntityPojo> getIssuesLastPricesMap() {
		
		Date currentDate = new Date();
		
		while (new DateFinantialUtil().isWeekend(currentDate))
			currentDate = new DateTime(currentDate).minusDays(1).toDate();
		
		List<IssuesLastPriceTmpEntity> tempIssuesLastPriceEntities = tempIssuesLastPriceRepository.findAll();
		
		Map<String, IssueLastPriceTmpEntityPojo> tempIssueLastPricePojosMap = new LinkedHashMap<>();
		
		for (IssuesLastPriceTmpEntity tempIssuesLastPriceEntity: tempIssuesLastPriceEntities) {
			
			IssueLastPriceTmpEntityPojo tempIssueLastPricePojo = mapEntityToPojoUtil.mapTempIssueLastPrice(new IssueLastPriceTmpEntityPojo(), tempIssuesLastPriceEntity);
			tempIssueLastPricePojosMap.put(tempIssueLastPricePojo.getIdIssue().toString(), tempIssueLastPricePojo);
		}
		
		return tempIssueLastPricePojosMap;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void executeStoreIssuesLastPrice() {

		storeIssuesLastPrice();
	}
	
	@Transactional(rollbackFor = Exception.class)
	public GetTempIssuesLastPriceDataPojo executeGetIssuesLastPrice() {
		
		Map<String, IssueLastPriceTmpEntityPojo> tempIssueLastPricePojosMap = getIssuesLastPricesMap();
		GetTempIssuesLastPriceDataPojo getTempIssuesLastPricePojo = new GetTempIssuesLastPriceDataPojo();
		getTempIssuesLastPricePojo.setTempIssueLastPricesMap(tempIssueLastPricePojosMap);
		
		return getTempIssuesLastPricePojo;
	}

}
