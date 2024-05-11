package project.stock.track.app.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import lib.base.backend.utils.DataUtil;
import lib.base.backend.utils.date.DateUtil;
import project.stock.track.app.beans.entity.IssuesHistoricalEntity;
import project.stock.track.app.beans.entity.IssuesLastPriceTmpEntity;
import project.stock.track.app.beans.entity.IssuesManagerEntity;
import project.stock.track.app.beans.entity.IssuesManagerTrackPropertiesEntity;
import project.stock.track.app.beans.pojos.business.issues.IssueHistoricalTrackResumenPojo;
import project.stock.track.app.beans.pojos.entity.CatalogIssuesEntityDesPojo;
import project.stock.track.app.beans.pojos.entity.IssueHistoricalDayEntityPojo;
import project.stock.track.app.beans.pojos.entity.IssueHistoricalEntityPojo;
import project.stock.track.app.beans.pojos.tuple.IssueTransactionsByDateTuplePojo;
import project.stock.track.app.repository.IssuesHistoricalRepositoryImpl;
import project.stock.track.app.repository.IssuesManagerRepositoryImpl;
import project.stock.track.app.repository.TransactionIssueRepositoryImpl;

public class IssueHistoricalUtil {
	
	@Autowired
	MapEntityToPojoUtil mapEntityToPojoUtil;
	
	@Autowired
	DateUtil dateUtil;
	
	@Autowired
	DataUtil dataUtil;
	
	@Autowired
	IssuesManagerRepositoryImpl issuesManagerRepository;
	
	@Autowired
	IssuesHistoricalRepositoryImpl issuesHistoricalRepository;
	
	@Autowired
	TransactionIssueRepositoryImpl transactionIssueRepository;

	public IssueHistoricalEntityPojo buildIssueHistoricalData(IssuesManagerEntity issuesManagerEntity, Date startDate) {
		
		List<IssuesHistoricalEntity> issuesHistoricalEntities = issuesHistoricalRepository.findIssueHistorical(issuesManagerEntity.getId().getIdIssue(), startDate);
		
		List<IssueHistoricalDayEntityPojo> issueHistoricalDayEntityPojos = new ArrayList<>();
		
		for (IssuesHistoricalEntity issuesHistoricalEntity: issuesHistoricalEntities) {
			
			IssueHistoricalDayEntityPojo issueHistoricalDayEntityPojo = new IssueHistoricalDayEntityPojo();
			issueHistoricalDayEntityPojo.setClose(issuesHistoricalEntity.getClose());
			issueHistoricalDayEntityPojo.setDate(issuesHistoricalEntity.getIssuesHistoricalEntityId().getIdDate().getTime());
			
			issueHistoricalDayEntityPojos.add(issueHistoricalDayEntityPojo);
		}
		
		IssuesHistoricalEntity lastIssueHistorical = !issuesHistoricalEntities.isEmpty() ? issuesHistoricalEntities.get(issuesHistoricalEntities.size() - 1) : null;
		IssuesLastPriceTmpEntity issuesLastPriceTmpEntity = issuesManagerEntity.getCatalogIssueEntity().getTempIssuesLastPriceEntity();
		
		if (lastIssueHistorical != null && issuesLastPriceTmpEntity != null && dateUtil.compareDatesNotTime(issuesLastPriceTmpEntity.getTimestamp(), lastIssueHistorical.getIssuesHistoricalEntityId().getIdDate()) > 0) {
			
			IssueHistoricalDayEntityPojo issueHistoricalDayEntityPojo = new IssueHistoricalDayEntityPojo();
			issueHistoricalDayEntityPojo.setClose(issuesLastPriceTmpEntity.getLast());
			issueHistoricalDayEntityPojo.setDate(dateUtil.getDateWithoutTime(issuesLastPriceTmpEntity.getTimestamp()).getTime());
			
			issueHistoricalDayEntityPojos.add(issueHistoricalDayEntityPojo);
		}
		
		CatalogIssuesEntityDesPojo issueEntityPojo = mapEntityToPojoUtil.mapCatalogIssueWithDescriptions(issuesManagerEntity.getCatalogIssueEntity(), null);
		IssuesManagerTrackPropertiesEntity issueTrackPropertiesEntity = issuesManagerEntity.getIssuesManagerTrackPropertiesEntity();
		
		IssueHistoricalTrackResumenPojo issueHistoricalTrackResumenPojo = new IssueHistoricalTrackResumenPojo();
		
		if (issueTrackPropertiesEntity != null) {
			
			issueHistoricalTrackResumenPojo.setBuyPrice(issueTrackPropertiesEntity.getTrackBuyPrice());
			issueHistoricalTrackResumenPojo.setSellPrice(issueTrackPropertiesEntity.getTrackSellPrice());
			issueHistoricalTrackResumenPojo.setFairValue(issueTrackPropertiesEntity.getFairValue());
		}
		if (issuesLastPriceTmpEntity != null) {
			
			issueHistoricalTrackResumenPojo.setCurrentPrice(issuesLastPriceTmpEntity.getLast());
			issueHistoricalTrackResumenPojo.setCurrentPriceDate(dataUtil.getValueOrNull(issuesLastPriceTmpEntity.getTimestamp(), Date::getTime));
		}
		if (!issuesHistoricalEntities.isEmpty()) {
			
			issueHistoricalTrackResumenPojo.setPreviousClosePrice(issuesHistoricalEntities.get(issuesHistoricalEntities.size() - 1).getClose());
			issueHistoricalTrackResumenPojo.setPreviousClosePriceDate(issuesHistoricalEntities.get(issuesHistoricalEntities.size() - 1).getIssuesHistoricalEntityId().getIdDate().getTime());
		}
		
		List<IssueTransactionsByDateTuplePojo> issueTransactionsByDateTuplePojos = transactionIssueRepository.findIssueTransactionsBuys(issuesManagerEntity.getId().getIdUser(), issuesManagerEntity.getId().getIdIssue(),  startDate);
		
		IssueHistoricalEntityPojo issueHistoricalEntityPojo = new IssueHistoricalEntityPojo();
		issueHistoricalEntityPojo.setIssueData(issueEntityPojo);
		issueHistoricalEntityPojo.setIssueTrackProperties(issueHistoricalTrackResumenPojo);
		issueHistoricalEntityPojo.setIssueHistorical(issueHistoricalDayEntityPojos);
		issueHistoricalEntityPojo.setIssueTransactionBuys(issueTransactionsByDateTuplePojos);
		
		return issueHistoricalEntityPojo;
	}
}
