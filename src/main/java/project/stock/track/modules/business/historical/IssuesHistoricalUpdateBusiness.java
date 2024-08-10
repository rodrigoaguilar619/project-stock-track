package project.stock.track.modules.business.historical;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import lib.base.backend.exception.data.BusinessException;
import lib.base.backend.persistance.GenericPersistence;
import lombok.RequiredArgsConstructor;
import project.stock.track.app.beans.entity.CatalogIssuesEntity;
import project.stock.track.app.beans.entity.IssuesHistoricalEntity;
import project.stock.track.app.beans.entity.IssuesHistoricalEntityId;
import project.stock.track.app.beans.pojos.business.issues.IssueHistoricalDateResultPojo;
import project.stock.track.app.beans.pojos.business.issues.IssueHistoricalResultPojo;
import project.stock.track.app.beans.pojos.petition.data.UpdateIssuesHistoricalDataPojo;
import project.stock.track.app.beans.pojos.services.IssueHistoricQueryPojo;
import project.stock.track.app.beans.rest.exchangetrade.IssueHistoryDayBean;
import project.stock.track.app.beans.rest.exchangetrade.service.IssueHistoricMainBean;
import project.stock.track.app.repository.IssuesRepositoryImpl;
import project.stock.track.app.utils.DateFinantialUtil;
import project.stock.track.app.vo.catalogs.CatalogsEntity;
import project.stock.track.app.vo.catalogs.CatalogsStaticData;
import project.stock.track.modules.business.MainBusiness;
import project.stock.track.services.exchangetrade.IssueTrackService;

@RequiredArgsConstructor
@Component
public class IssuesHistoricalUpdateBusiness extends MainBusiness {

	private static final Logger log = LoggerFactory.getLogger(IssuesHistoricalUpdateBusiness.class);

	@SuppressWarnings("rawtypes")
	@Qualifier("customPersistance")
	private final GenericPersistence genericCustomPersistance;
	private final IssuesRepositoryImpl issuesRepository;
	private final IssueTrackService issueTrackService;

	@SuppressWarnings("unchecked")
	public void saveIssueHistorical(String dateIssue, IssueHistoryDayBean issueHistoricBean, CatalogIssuesEntity catalogIssuesEntity) {

		try {

			genericCustomPersistance.startTransaction();

			IssuesHistoricalEntity issuesHistoricalEntity = new IssuesHistoricalEntity();
			issuesHistoricalEntity.setIssuesHistoricalEntityId(new IssuesHistoricalEntityId(catalogIssuesEntity.getId(),
					dateFormatUtil.formatDate(dateIssue, CatalogsStaticData.ServiceTiingo.DATE_FORMAT_DEFAULT)));
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
		} catch (ParseException pe) {
			log.error("Error parsing issue: " + catalogIssuesEntity.getInitials() + " date: " + dateIssue, pe);
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

	public IssueHistoricalResultPojo updateIssueHistorical(CatalogIssuesEntity catalogIssuesEntity)
			throws BusinessException {

		Date dateFrom = null;

		if (!catalogIssuesEntity.getIssuesHistoricalEntities().isEmpty()
				&& catalogIssuesEntity.getIssuesHistoricalEntities().get(0) != null) {
			dateFrom = new DateTime(
					catalogIssuesEntity.getIssuesHistoricalEntities().get(0).getIssuesHistoricalEntityId().getIdDate())
					.plusDays(1).toDate();
		} else {
			dateFrom = catalogIssuesEntity.getHistoricalStartDate() != null
					? catalogIssuesEntity.getHistoricalStartDate()
					: null;
		}

		IssueHistoricQueryPojo issueHistoricQueryPojo = new IssueHistoricQueryPojo();
		issueHistoricQueryPojo.setIssue(catalogIssuesEntity.getInitials());
		issueHistoricQueryPojo.setDateFrom(dateFrom);
		issueHistoricQueryPojo
				.setIsStandarPoors(catalogIssuesEntity.getIsSp500());

		IssueHistoricMainBean historicBean = null;

		if (catalogIssuesEntity.getIdTypeStock().equals(CatalogsEntity.CatalogTypeStock.ISSUE))
			historicBean = issueTrackService.getIssueHistoric(null, issueHistoricQueryPojo);

		if (log.isInfoEnabled()) {
			log.info(String.format("FINANTIAL SERVICE START. uri: %s",
					(historicBean != null ? historicBean.getUri() : "--")));
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

		Date lastBusinessDay = new DateFinantialUtil().getLastBusinessDay(new Date());

		List<CatalogIssuesEntity> catalogIssuesEntities = getIssuesToUpdate();
		List<IssueHistoricalResultPojo> issueHistoricalResultPojos = new ArrayList<>();

		for (CatalogIssuesEntity catalogIssuesEntity : catalogIssuesEntities) {

			Date currentDate = (!catalogIssuesEntity.getIssuesHistoricalEntities().isEmpty()
					&& catalogIssuesEntity.getIssuesHistoricalEntities().get(0) != null)
							? catalogIssuesEntity.getIssuesHistoricalEntities().get(0).getIssuesHistoricalEntityId()
									.getIdDate()
							: null;

			if (currentDate == null || (dateUtil.compareDatesNotTime(new DateTime(currentDate).plusDays(1).toDate(),
					lastBusinessDay) <= 0)) {
				IssueHistoricalResultPojo issueHistoricalResultPojo = updateIssueHistorical(catalogIssuesEntity);
				issueHistoricalResultPojos.add(issueHistoricalResultPojo);
			}
		}

		UpdateIssuesHistoricalDataPojo dataPojo = new UpdateIssuesHistoricalDataPojo();
		dataPojo.setIssueHistoricalResult(issueHistoricalResultPojos);

		return dataPojo;
	}
}
