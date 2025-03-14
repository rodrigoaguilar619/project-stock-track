package project.stock.track.config.helpers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import lib.base.backend.utils.date.DateUtil;
import lombok.RequiredArgsConstructor;
import project.stock.track.app.beans.entity.IssuesHistoricalEntity;
import project.stock.track.app.beans.entity.IssuesHistoricalFairValueEntity;
import project.stock.track.app.beans.entity.IssuesLastPriceTmpEntity;
import project.stock.track.app.beans.entity.IssuesManagerEntity;
import project.stock.track.app.beans.entity.IssuesManagerTrackPropertiesEntity;
import project.stock.track.app.beans.pojos.business.issues.IssueHistoricalTrackResumenPojo;
import project.stock.track.app.beans.pojos.entity.CatalogIssuesEntityDesPojo;
import project.stock.track.app.beans.pojos.entity.IssueHistoricalDayEntityPojo;
import project.stock.track.app.beans.pojos.entity.IssueHistoricalEntityPojo;
import project.stock.track.app.beans.pojos.entity.IssueHistoricalFairValueEntityPojo;
import project.stock.track.app.beans.pojos.tuple.IssueTransactionsByDateTuplePojo;
import project.stock.track.app.repository.IssuesHistoricalRepositoryImpl;
import project.stock.track.app.repository.TransactionIssueRepositoryImpl;
import project.stock.track.app.utils.MapEntityToPojoUtil;

@RequiredArgsConstructor
@Component
public class IssueHistoricalHelper {
	
	private MapEntityToPojoUtil mapEntityToPojoUtil = new MapEntityToPojoUtil();
	private DateUtil dateUtil = new DateUtil();
	
	private final IssuesHistoricalRepositoryImpl issuesHistoricalRepository;
	private final TransactionIssueRepositoryImpl transactionIssueRepository;

	public IssueHistoricalEntityPojo buildIssueHistoricalData(IssuesManagerEntity issuesManagerEntity, LocalDateTime startDate) {
		
		List<IssuesHistoricalEntity> issuesHistoricalEntities = issuesHistoricalRepository.findIssueHistorical(issuesManagerEntity.getId().getIdIssue(), startDate);
		
		List<IssueHistoricalDayEntityPojo> issueHistoricalDayEntityPojos = new ArrayList<>();
		List<IssueHistoricalFairValueEntityPojo> issueHistoricalFairValueEntityPojos = new ArrayList<>();
		
		for (IssuesHistoricalFairValueEntity issuesHistoricalFairValueEntity: issuesManagerEntity.getCatalogIssueEntity().getIssuesHistoricalFairValueEntities()) {
		
			IssueHistoricalFairValueEntityPojo issueHistoricalFairValueEntityPojo = new IssueHistoricalFairValueEntityPojo();
			issueHistoricalFairValueEntityPojo.setDate(dateUtil.getMillis(issuesHistoricalFairValueEntity.getId().getIdDate()));
			issueHistoricalFairValueEntityPojo.setFairValue(issuesHistoricalFairValueEntity.getFairValue());
			
			issueHistoricalFairValueEntityPojos.add(issueHistoricalFairValueEntityPojo);
		}
	
		for (IssuesHistoricalEntity issuesHistoricalEntity: issuesHistoricalEntities) {
			
			IssueHistoricalDayEntityPojo issueHistoricalDayEntityPojo = new IssueHistoricalDayEntityPojo();
			issueHistoricalDayEntityPojo.setClose(issuesHistoricalEntity.getClose());
			issueHistoricalDayEntityPojo.setDate(dateUtil.getMillis(issuesHistoricalEntity.getId().getIdDate()));
			
			issueHistoricalDayEntityPojos.add(issueHistoricalDayEntityPojo);
		}
		
		IssuesHistoricalEntity lastIssueHistorical = !issuesHistoricalEntities.isEmpty() ? issuesHistoricalEntities.get(issuesHistoricalEntities.size() - 1) : null;
		IssuesLastPriceTmpEntity issuesLastPriceTmpEntity = issuesManagerEntity.getCatalogIssueEntity().getTempIssuesLastPriceEntity();
		
		if (lastIssueHistorical != null && issuesLastPriceTmpEntity != null && dateUtil.compareDatesNotTime(issuesLastPriceTmpEntity.getTimestamp(), lastIssueHistorical.getId().getIdDate()) > 0) {
			
			IssueHistoricalDayEntityPojo issueHistoricalDayEntityPojo = new IssueHistoricalDayEntityPojo();
			issueHistoricalDayEntityPojo.setClose(issuesLastPriceTmpEntity.getLast());
			issueHistoricalDayEntityPojo.setDate(dateUtil.getMillis(issuesLastPriceTmpEntity.getTimestamp()));
			
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
			issueHistoricalTrackResumenPojo.setCurrentPriceDate(dateUtil.getMillis(issuesLastPriceTmpEntity.getTimestamp()));
		}
		if (!issuesHistoricalEntities.isEmpty()) {
			
			issueHistoricalTrackResumenPojo.setPreviousClosePrice(issuesHistoricalEntities.get(issuesHistoricalEntities.size() - 1).getClose());
			issueHistoricalTrackResumenPojo.setPreviousClosePriceDate(dateUtil.getMillis(issuesHistoricalEntities.get(issuesHistoricalEntities.size() - 1).getId().getIdDate()));
		}
		
		List<IssueTransactionsByDateTuplePojo> issueTransactionsByDateTuplePojos = transactionIssueRepository.findIssueTransactionsBuys(issuesManagerEntity.getId().getIdUser(), issuesManagerEntity.getId().getIdIssue(),  startDate);
		
		IssueHistoricalEntityPojo issueHistoricalEntityPojo = new IssueHistoricalEntityPojo();
		issueHistoricalEntityPojo.setIssueData(issueEntityPojo);
		issueHistoricalEntityPojo.setIssueTrackProperties(issueHistoricalTrackResumenPojo);
		issueHistoricalEntityPojo.setIssueHistorical(issueHistoricalDayEntityPojos);
		issueHistoricalEntityPojo.setIssueTransactionBuys(issueTransactionsByDateTuplePojos);
		issueHistoricalEntityPojo.setIssueHistoricalFairValues(issueHistoricalFairValueEntityPojos);
		
		return issueHistoricalEntityPojo;
	}
}
