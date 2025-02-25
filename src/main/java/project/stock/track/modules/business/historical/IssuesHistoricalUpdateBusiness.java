package project.stock.track.modules.business.historical;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import lib.base.backend.exception.data.BusinessException;
import lib.base.backend.persistance.GenericPersistence;
import lib.base.backend.utils.RestUtil;
import lombok.RequiredArgsConstructor;
import project.stock.track.app.beans.entity.CatalogIssuesEntity;
import project.stock.track.app.beans.entity.IssuesHistoricalEntity;
import project.stock.track.app.beans.entity.IssuesHistoricalEntityPk;
import project.stock.track.app.beans.pojos.business.historical.IssuesHistoricalProgressPojo;
import project.stock.track.app.beans.pojos.business.issues.IssueHistoricalDateResultPojo;
import project.stock.track.app.beans.pojos.business.issues.IssueHistoricalResultPojo;
import project.stock.track.app.beans.pojos.petition.data.UpdateIssuesHistoricalDataPojo;
import project.stock.track.app.beans.pojos.services.IssueHistoricQueryPojo;
import project.stock.track.app.beans.rest.exchangetrade.IssueHistoryDayBean;
import project.stock.track.app.beans.rest.exchangetrade.service.IssueHistoricMainBean;
import project.stock.track.app.repository.IssuesRepositoryImpl;
import project.stock.track.app.utils.DateFinantialUtil;
import project.stock.track.app.vo.catalogs.CatalogsStaticData;
import project.stock.track.app.vo.entities.CatalogIndexEnum;
import project.stock.track.app.vo.entities.CatalogTypeStockEnum;
import project.stock.track.modules.business.MainBusiness;
import project.stock.track.services.exchangetrade.IssueTrackService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class IssuesHistoricalUpdateBusiness extends MainBusiness {

	private static final Logger log = LoggerFactory.getLogger(IssuesHistoricalUpdateBusiness.class);

	@SuppressWarnings("rawtypes")
	@Qualifier("customPersistance")
	private final GenericPersistence genericCustomPersistance;
	private final IssuesRepositoryImpl issuesRepository;
	private final IssueTrackService issueTrackService;
	
	private DateFinantialUtil dateFinantialUtil = new DateFinantialUtil();
	
	private boolean verifyUpdateIssueHistorical(CatalogIssuesEntity catalogIssuesEntity) {
		
		LocalDateTime lastBusinessDay = dateFinantialUtil.getLastBusinessDay(LocalDateTime.now());

		LocalDateTime currentDate = (!catalogIssuesEntity.getIssuesHistoricalEntities().isEmpty() && catalogIssuesEntity.getIssuesHistoricalEntities().get(0) != null)
					? catalogIssuesEntity.getIssuesHistoricalEntities().get(0).getId().getIdDate() : null;

		return currentDate == null || (dateUtil.compareDatesNotTime(currentDate.plusDays(1), lastBusinessDay) <= 0);
	}

	@SuppressWarnings("unchecked")
	public void saveIssueHistorical(String dateIssue, IssueHistoryDayBean issueHistoricBean, CatalogIssuesEntity catalogIssuesEntity) {

		try {

			genericCustomPersistance.startTransaction();

			IssuesHistoricalEntity issuesHistoricalEntity = new IssuesHistoricalEntity();
			issuesHistoricalEntity.setId(new IssuesHistoricalEntityPk(catalogIssuesEntity.getId(),
					dateFormatUtil.formatLocalDateTime(dateIssue, CatalogsStaticData.ServiceTiingo.DATE_FORMAT_DEFAULT)));
			issuesHistoricalEntity.setClose(
					issueHistoricBean.getClose() != null ? new BigDecimal(issueHistoricBean.getClose()) : null);
			issuesHistoricalEntity
					.setOpen(issueHistoricBean.getOpen() != null ? new BigDecimal(issueHistoricBean.getOpen()) : null);
			issuesHistoricalEntity
					.setLow(issueHistoricBean.getLow() != null ? new BigDecimal(issueHistoricBean.getLow()) : null);
			issuesHistoricalEntity
					.setHigh(issueHistoricBean.getHigh() != null ? new BigDecimal(issueHistoricBean.getHigh()) : null);
			issuesHistoricalEntity.setVolume(
					issueHistoricBean.getVolume() != null ? new BigDecimal(issueHistoricBean.getVolume()) : null);

			genericCustomPersistance.save(issuesHistoricalEntity);
			genericCustomPersistance.commitTransaction();
		} catch (Exception pe) {
			log.error("Error processing issue: " + catalogIssuesEntity.getInitials() + " date: " + dateIssue, pe);
			genericCustomPersistance.rollBackTransaction();
		} finally {
			genericCustomPersistance.closeEntityManager();
		}
	}

	private List<IssueHistoricalDateResultPojo> saveIssuesHistorical(CatalogIssuesEntity catalogIssuesEntity,
			IssueHistoricMainBean issueHistoricBean) {

		List<IssueHistoricalDateResultPojo> issueDateHistoricalResultPojos = new ArrayList<>();

		for (Map.Entry<String, IssueHistoryDayBean> entry : new TreeMap<String, IssueHistoryDayBean>(
				issueHistoricBean.getHistory()).entrySet()) {

			if (log.isInfoEnabled())
				log.info(String.format("FINANTIAL SERVICE ISSUE/CRYPTO STORING. issue: %s date: %s",
						issueHistoricBean.getName(), entry.getKey()));

			IssueHistoricalDateResultPojo issueDateHistoricalResultPojo;
			try {

				saveIssueHistorical(entry.getKey(), entry.getValue(), catalogIssuesEntity);
				issueDateHistoricalResultPojo = new IssueHistoricalDateResultPojo();
				issueDateHistoricalResultPojo.setCode(200);
				issueDateHistoricalResultPojo.setDate(entry.getKey());
			} catch (Exception e) {
				log.error("ERROR FINANTIAL SERVICE ISSUE/CRYPTO STORING. issue: " + issueHistoricBean.getName()
						+ " date: " + entry.getKey(), e);
				issueDateHistoricalResultPojo = new IssueHistoricalDateResultPojo();
				issueDateHistoricalResultPojo.setCode(409);
				issueDateHistoricalResultPojo.setDate(entry.getKey());
			}

			issueDateHistoricalResultPojos.add(issueDateHistoricalResultPojo);
		}

		return issueDateHistoricalResultPojos;
	}

	public IssueHistoricalResultPojo updateIssueHistorical(CatalogIssuesEntity catalogIssuesEntity) throws BusinessException {

		LocalDateTime dateFrom = null;
		
		if (!catalogIssuesEntity.getIssuesHistoricalEntities().isEmpty()
	            && catalogIssuesEntity.getIssuesHistoricalEntities().get(0) != null) {
	        dateFrom = catalogIssuesEntity.getIssuesHistoricalEntities().get(0).getId().getIdDate().plusDays(1);
	    } else {
	        dateFrom = catalogIssuesEntity.getHistoricalStartDate() != null
	                ? catalogIssuesEntity.getHistoricalStartDate()
	                : null;
	    }

		IssueHistoricQueryPojo issueHistoricQueryPojo = new IssueHistoricQueryPojo();
		issueHistoricQueryPojo.setIssue(catalogIssuesEntity.getInitials());
		issueHistoricQueryPojo.setDateFrom(dateFrom);
		issueHistoricQueryPojo.setIsStandarPoors(catalogIssuesEntity.getIdIndex().equals(CatalogIndexEnum.SP500.getValue()));

		IssueHistoricMainBean historicBean = null;

		if (catalogIssuesEntity.getIdTypeStock().equals(CatalogTypeStockEnum.ISSUE.getValue()))
			historicBean = issueTrackService.getIssueHistoric(null, issueHistoricQueryPojo);

		if (log.isInfoEnabled()) {
			log.info(String.format("FINANTIAL SERVICE START. uri: %s", (historicBean != null ? historicBean.getUri() : "--")));
			log.info(String.format("FINANTIAL SERVICE START. total records found: %s",
					(historicBean != null && historicBean.getHistory() != null ? historicBean.getHistory().size() + ""
							: "0")));
		}

		IssueHistoricalResultPojo issueHistoricalResultPojo = new IssueHistoricalResultPojo();
		issueHistoricalResultPojo.setIssue(catalogIssuesEntity.getInitials());
		List<IssueHistoricalDateResultPojo> issueDateHistoricalResultPojos = new ArrayList<>();

		if (historicBean != null)
			issueDateHistoricalResultPojos.addAll(saveIssuesHistorical(catalogIssuesEntity, historicBean));

		issueHistoricalResultPojo.setIssueDateHistoricalResultPojos(issueDateHistoricalResultPojos);
		return issueHistoricalResultPojo;
	}

	private List<CatalogIssuesEntity> getIssuesToUpdate() {

		return issuesRepository.findAllWithStatusActive();
	}

	public UpdateIssuesHistoricalDataPojo executeUpdateIssuesHistoricals() throws BusinessException {

		List<CatalogIssuesEntity> catalogIssuesEntities = getIssuesToUpdate();
		List<IssueHistoricalResultPojo> issueHistoricalResultPojos = new ArrayList<>();

		for (CatalogIssuesEntity catalogIssuesEntity : catalogIssuesEntities) {

			if (verifyUpdateIssueHistorical(catalogIssuesEntity)) {
				IssueHistoricalResultPojo issueHistoricalResultPojo = updateIssueHistorical(catalogIssuesEntity);
				issueHistoricalResultPojos.add(issueHistoricalResultPojo);
			}
		}

		UpdateIssuesHistoricalDataPojo dataPojo = new UpdateIssuesHistoricalDataPojo();
		dataPojo.setIssueHistoricalResult(issueHistoricalResultPojos);

		return dataPojo;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Flux<ResponseEntity> executeUpdateIssuesHistoricalsFlux() throws BusinessException {
	    
		List<CatalogIssuesEntity> catalogIssuesEntities = getIssuesToUpdate();
	    AtomicInteger currentStock = new AtomicInteger(0);
	    int window = 20;
	    
	    return Flux.fromIterable(catalogIssuesEntities)
	            .window(window) // Collect items into batches of 10
	            .delayElements(Duration.ofSeconds(0))
	            .flatMapSequential(batch -> {
	                // Process each batch and return a Mono of the entire batch's result
	                return batch.collectList().flatMap(catalogIssuesEntitiesBatch -> {
	                	
                        List<ResponseEntity> pojos = new ArrayList<>();
                        for (CatalogIssuesEntity catalogIssuesEntity : catalogIssuesEntitiesBatch) {
                        	
                        	if (verifyUpdateIssueHistorical(catalogIssuesEntity)) {
                				try {
									updateIssueHistorical(catalogIssuesEntity);
								} catch (BusinessException e) {
									throw new RuntimeException("Error processing issue: " + catalogIssuesEntity.getInitials()); 
								}
                			}

                            int currentStockShow = currentStock.incrementAndGet();
                            if (currentStockShow % window == 0) {
                            	IssuesHistoricalProgressPojo issuesHistoricalProgressPojo = new IssuesHistoricalProgressPojo(catalogIssuesEntity.getInitials(), currentStockShow, catalogIssuesEntities.size());
                            	pojos.add(new RestUtil().buildResponseSuccess(issuesHistoricalProgressPojo, "Issues historical batch data updated"));
                            }
                        }

                        return Mono.just(pojos);
                    });
	            })
	            .flatMap(pojos -> Flux.fromIterable(pojos))
	            .onErrorResume(e -> {
	            	return Mono.just(new RestUtil().buildResponseUnprocessable(e.getMessage()));
	            });
	}
}
