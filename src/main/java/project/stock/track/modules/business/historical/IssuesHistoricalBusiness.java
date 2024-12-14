package project.stock.track.modules.business.historical;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lib.base.backend.modules.security.jwt.entity.UserEntity;
import lib.base.backend.modules.security.jwt.repository.UserRepositoryImpl;
import lib.base.backend.persistance.GenericPersistence;
import lib.base.backend.utils.DataTableUtil;
import lib.base.backend.utils.TimeElapseUtil;
import lombok.RequiredArgsConstructor;
import project.stock.track.app.beans.entity.DollarHistoricalPriceEntity;
import project.stock.track.app.beans.entity.IssuesHistoricalEntity;
import project.stock.track.app.beans.entity.IssuesLastPriceTmpEntity;
import project.stock.track.app.beans.entity.IssuesManagerEntity;
import project.stock.track.app.beans.entity.IssuesManagerEntityPk;
import project.stock.track.app.beans.pojos.business.issues.IssueCalculateResumePojo;
import project.stock.track.app.beans.pojos.business.issues.IssueCurrentPricePojo;
import project.stock.track.app.beans.pojos.business.transaction.TransactionIssueCalculatePojo;
import project.stock.track.app.beans.pojos.entity.IssueHistoricalEntityPojo;
import project.stock.track.app.beans.pojos.petition.data.GetIssueHistoricalDataPojo;
import project.stock.track.app.beans.pojos.petition.data.GetIssuesHistoricalDataPojo;
import project.stock.track.app.beans.pojos.petition.request.GetIssueHistoricalRequestPojo;
import project.stock.track.app.beans.pojos.petition.request.GetIssuesHistoricalRequestPojo;
import project.stock.track.app.beans.pojos.tuple.IssueTransactionResumeTuplePojo;
import project.stock.track.app.repository.DollarHistoricalPriceRepositoryImpl;
import project.stock.track.app.repository.IssuesHistoricalRepositoryImpl;
import project.stock.track.app.repository.TransactionIssueRepositoryImpl;
import project.stock.track.app.utils.CalculatorUtil;
import project.stock.track.app.utils.IssueUtil;
import project.stock.track.app.vo.catalogs.CatalogsEntity;
import project.stock.track.app.vo.catalogs.CatalogsStaticData;
import project.stock.track.config.helpers.IssueHistoricalHelper;
import project.stock.track.modules.business.MainBusiness;

@RequiredArgsConstructor
@Component
public class IssuesHistoricalBusiness extends MainBusiness {
	
	private IssueUtil issueUtil = new IssueUtil();
	private CalculatorUtil calculatorUtil = new CalculatorUtil();
	
	@SuppressWarnings("rawtypes")
	private final GenericPersistence genericPersistance;
	private final UserRepositoryImpl userRepository;
	private final IssuesHistoricalRepositoryImpl issuesHistoricalRepository;
	private final TransactionIssueRepositoryImpl transactionIssueRepository;
	private final DollarHistoricalPriceRepositoryImpl dollarHistoricalPriceRespository;
	private final IssueHistoricalHelper issueHistoricalHelper;
	
	private TransactionIssueCalculatePojo getIssueTransactionEstimates(TransactionIssueCalculatePojo transactionIssueCalculatePojo, BigDecimal currentIssuePrice, BigDecimal dollarPriceAfterDeprecate) {
		
		BigDecimal sellEstimate = calculatorUtil.determineFinalValue(currentIssuePrice, transactionIssueCalculatePojo.getIdTypeCurrency(), dollarPriceAfterDeprecate);

		if (transactionIssueCalculatePojo.getSellDate() == null) {
			
			BigDecimal commision = transactionIssueCalculatePojo.getIdBroker().equals(CatalogsEntity.CatalogBroker.GBM_HOMBROKER) ? CatalogsStaticData.StaticData.DEFAULT_COMMISION_GBM : CatalogsStaticData.StaticData.DEFAULT_COMMISION_CHARLES_SCHWAB;
			BigDecimal taxes = transactionIssueCalculatePojo.getIdBroker().equals(CatalogsEntity.CatalogBroker.GBM_HOMBROKER) ? CatalogsStaticData.StaticData.DEFAULT_TAXES_PERCENTAGE_GBM : CatalogsStaticData.StaticData.DEFAULT_TAXES_PERCENTAGE_CHARLES_SCHWAB; 
			
			BigDecimal taxesOutcomeEstimate = sellEstimate.subtract(transactionIssueCalculatePojo.getPriceBuy()).multiply(taxes).divide(BigDecimal.valueOf(100));
			BigDecimal commisionOutcomeEstimate = sellEstimate.multiply(commision).divide(BigDecimal.valueOf(100));
			BigDecimal sellEstimateAfterAll = sellEstimate.subtract(taxesOutcomeEstimate).subtract(commisionOutcomeEstimate);
			BigDecimal gainLossTotalEstimate = sellEstimateAfterAll.subtract(transactionIssueCalculatePojo.getPriceBuy());
			BigDecimal gainLossPercentageEstimate = sellEstimateAfterAll.multiply(BigDecimal.valueOf(100)).divide(transactionIssueCalculatePojo.getPriceBuy(), RoundingMode.CEILING).subtract(BigDecimal.valueOf(100));
			
			transactionIssueCalculatePojo.setGainLossTotalEstimate(gainLossTotalEstimate);
			transactionIssueCalculatePojo.setGainLossPercentageEstimate(gainLossPercentageEstimate);
		}
		
		return transactionIssueCalculatePojo;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(rollbackFor = Exception.class)
	public GetIssueHistoricalDataPojo executeGetIssueHistorical(GetIssueHistoricalRequestPojo requestPojo) {
		
		LocalDateTime startDate = LocalDateTime.of(1980, 1, 1, 0, 0, 0);

		UserEntity userEntity = userRepository.findByUserName(requestPojo.getUserName());
		IssuesManagerEntity issuesManagerEntity = (IssuesManagerEntity) genericPersistance.findById(IssuesManagerEntity.class, new IssuesManagerEntityPk(requestPojo.getIdIssue(), userEntity.getId()));
		DollarHistoricalPriceEntity dollarHistoricalPriceEntity = dollarHistoricalPriceRespository.findLastRecord();
		
		IssueHistoricalEntityPojo issueHistoricalEntityPojo = issueHistoricalHelper.buildIssueHistoricalData(issuesManagerEntity, startDate);
		
		IssuesHistoricalEntity issuesHistoricalEntityLastRecord = issuesHistoricalRepository.findLastRecord(issuesManagerEntity.getId().getIdIssue());
		issuesHistoricalEntityLastRecord.setClose(new BigDecimal(10));
		IssuesLastPriceTmpEntity issuesLastPriceTmpEntity = issuesManagerEntity.getCatalogIssueEntity().getTempIssuesLastPriceEntity();
		
		IssueCurrentPricePojo issueCurrentPricePojo = issueUtil.getCurrentPrice(issuesLastPriceTmpEntity, issuesHistoricalEntityLastRecord);
		BigDecimal dollarPriceAfterDeprecate = dollarHistoricalPriceEntity.getPrice().subtract(dollarHistoricalPriceEntity.getPrice().multiply(CatalogsStaticData.StaticData.DEFAULT_DOLAR_PRICE_DEPRECATE_PERCENTAGE).divide(BigDecimal.valueOf(100)));
		
		IssueCalculateResumePojo issueCalculateResumePojo = new IssueCalculateResumePojo();
		issueCalculateResumePojo.setCurrentDollarPrice(dollarHistoricalPriceEntity.getPrice());
		issueCalculateResumePojo.setCurrentDollarDate(dateUtil.getMillis(dollarHistoricalPriceEntity.getIdDate()));
		issueCalculateResumePojo.setDollarPriceDeprecatePercentage(CatalogsStaticData.StaticData.DEFAULT_DOLAR_PRICE_DEPRECATE_PERCENTAGE);
		issueCalculateResumePojo.setCurrentIssuePrice(issueCurrentPricePojo.getCurrentPrice());
		issueCalculateResumePojo.setCurrentDollarPriceAfterDeprecate(dollarPriceAfterDeprecate);
		issueCalculateResumePojo.setIssueSellEstimate(dollarPriceAfterDeprecate.multiply(issueCurrentPricePojo.getCurrentPrice()));
		issueCalculateResumePojo.setCommisionSell(CatalogsStaticData.StaticData.DEFAULT_COMMISION_GBM);
		issueCalculateResumePojo.setTaxesPercentage(CatalogsStaticData.StaticData.DEFAULT_TAXES_PERCENTAGE_GBM);
		issueCalculateResumePojo.setCurrentIssueDate(issueCurrentPricePojo.getDate());
		
		List<IssueTransactionResumeTuplePojo> issueTransactionResumeTuplePojos = transactionIssueRepository.findIssueTransactionsResume(userEntity.getId(), issuesManagerEntity.getId().getIdIssue());
		List<TransactionIssueCalculatePojo> transactionIssueCalculatePojos = new ArrayList<>();
		
		for(IssueTransactionResumeTuplePojo issueTransactionResumeTuplePojo: issueTransactionResumeTuplePojos) {
			
			TransactionIssueCalculatePojo transactionIssueCalculatePojo = new TransactionIssueCalculatePojo();
			transactionIssueCalculatePojo.setIdBroker(issueTransactionResumeTuplePojo.getIdBroker());
			transactionIssueCalculatePojo.setDescriptionBroker(issueTransactionResumeTuplePojo.getDescriptionBroker());
			transactionIssueCalculatePojo.setIdTypeCurrency(issueTransactionResumeTuplePojo.getIdTypeCurrency());
			transactionIssueCalculatePojo.setDescriptionTypeCurrency(issueTransactionResumeTuplePojo.getDescriptionTypeCurrency());
			transactionIssueCalculatePojo.setTotalShares(issueTransactionResumeTuplePojo.getTotalShares());
			transactionIssueCalculatePojo.setPriceBuy(issueTransactionResumeTuplePojo.getPriceBuy());
			transactionIssueCalculatePojo.setSumPriceBuy(issueTransactionResumeTuplePojo.getSumPriceBuy());
			transactionIssueCalculatePojo.setBuyDate(issueTransactionResumeTuplePojo.getBuyDate());
			transactionIssueCalculatePojo.setSellDate(issueTransactionResumeTuplePojo.getSellDate());
			transactionIssueCalculatePojo.setPriceSell(issueTransactionResumeTuplePojo.getPriceSell());
			transactionIssueCalculatePojo.setSumPriceSell(issueTransactionResumeTuplePojo.getSumPriceSell());
			transactionIssueCalculatePojo.setSellGainLossTotal(issueTransactionResumeTuplePojo.getSellGainLossTotal());
			transactionIssueCalculatePojo.setSellGainLossPercentage(issueTransactionResumeTuplePojo.getSellGainLossPercentage());
			
			getIssueTransactionEstimates(transactionIssueCalculatePojo, issueCurrentPricePojo.getCurrentPrice(), dollarPriceAfterDeprecate);
		
			transactionIssueCalculatePojos.add(transactionIssueCalculatePojo);
		}
		
		GetIssueHistoricalDataPojo dataPojo = new GetIssueHistoricalDataPojo();
		dataPojo.setIssueHistoricalData(issueHistoricalEntityPojo);
		dataPojo.setIssueCalculateResume(issueCalculateResumePojo);
		dataPojo.setIssueTransactions(transactionIssueCalculatePojos);
		
		return dataPojo;
	}
	
	public List<IssueHistoricalEntityPojo> generateIssuesHistoricalData(List<IssuesManagerEntity> issuesManagerEntities, LocalDateTime startDate) {
		
		List<IssueHistoricalEntityPojo> issueHistoricalEntityPojos = new ArrayList<>();
		
		for (IssuesManagerEntity issuesManagerEntity: issuesManagerEntities)
			issueHistoricalEntityPojos.add(issueHistoricalHelper.buildIssueHistoricalData(issuesManagerEntity, startDate));
		
		return issueHistoricalEntityPojos;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public GetIssuesHistoricalDataPojo executeGetIssuesHistorical(GetIssuesHistoricalRequestPojo requestPojo) {
		
		TimeElapseUtil timeElapseUtil = new TimeElapseUtil("MAIN GetIssuesHistorical");
		timeElapseUtil.printStart();
		
		LocalDateTime startDate = LocalDateTime.now();
		startDate = startDate.minusYears(2);
		
		UserEntity userEntity = userRepository.findByUserName(requestPojo.getUserName());
		
		List<IssuesManagerEntity> issuesManagerEntities = issuesHistoricalRepository.findAllWithStatusActive(userEntity.getId(), new DataTableUtil().getStartLimit(requestPojo.getDataTableConfig()), requestPojo.getDataTableConfig().getRowsPerPage(), requestPojo.getDataTableConfig().getFilters());
		
		List<IssueHistoricalEntityPojo> issueHistoricalEntityPojos = generateIssuesHistoricalData(issuesManagerEntities, startDate);
		
		Long totalIssues = requestPojo.getTotalRowsGlobal() == null || requestPojo.getTotalRowsGlobal() == 0 ? issuesHistoricalRepository.findCountWithStatusActive(requestPojo.getDataTableConfig().getFilters()) : requestPojo.getTotalRowsGlobal();
		
		GetIssuesHistoricalDataPojo responsePojo = new GetIssuesHistoricalDataPojo();
		responsePojo.setIssuesHistorical(issueHistoricalEntityPojos);
		responsePojo.setTotalIssues(totalIssues);
		
		timeElapseUtil.printEnd();
		
		return responsePojo;
		
	}

}
