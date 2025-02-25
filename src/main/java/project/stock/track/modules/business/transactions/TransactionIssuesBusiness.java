package project.stock.track.modules.business.transactions;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lib.base.backend.modules.security.jwt.entity.UserEntity;
import lib.base.backend.modules.security.jwt.repository.UserRepositoryImpl;
import lib.base.backend.persistance.GenericPersistence;
import lombok.RequiredArgsConstructor;
import project.stock.track.app.beans.entity.CatalogBrokerEntity;
import project.stock.track.app.beans.entity.DollarHistoricalPriceEntity;
import project.stock.track.app.beans.entity.IssuesLastPriceTmpEntity;
import project.stock.track.app.beans.entity.IssuesManagerEntityPk;
import project.stock.track.app.beans.entity.IssuesManagerTrackPropertiesEntity;
import project.stock.track.app.beans.pojos.business.transaction.TransactionIssueNotSoldPojo;
import project.stock.track.app.beans.pojos.business.transaction.TransactionIssueTrackPojo;
import project.stock.track.app.beans.pojos.petition.data.GetTransactionIssuestrackDataPojo;
import project.stock.track.app.beans.pojos.petition.request.GetTransactionIssuestrackRequestPojo;
import project.stock.track.app.repository.DollarHistoricalPriceRepositoryImpl;
import project.stock.track.app.repository.TransactionIssueRepositoryImpl;
import project.stock.track.app.utils.CalculatorUtil;
import project.stock.track.app.vo.catalogs.CatalogsStaticData;
import project.stock.track.app.vo.entities.CatalogBrokerEnum;
import project.stock.track.modules.business.MainBusiness;

@RequiredArgsConstructor
@Component
public class TransactionIssuesBusiness extends MainBusiness {
	
	@SuppressWarnings("rawtypes")
	private final GenericPersistence genericPersistance;
	private final TransactionIssueRepositoryImpl transactionIssueRepository;
	private final DollarHistoricalPriceRepositoryImpl dollarHistoricalPriceRespository;
	private final UserRepositoryImpl userRepository;
	
	private CalculatorUtil calculatorUtil = new CalculatorUtil();

	@SuppressWarnings("unchecked")
	public List<TransactionIssueTrackPojo> getTransactionIssuesTrackPojos(UserEntity user) {
		
		List<TransactionIssueNotSoldPojo> transactionsNotSoldCustomPojos = transactionIssueRepository.findTransactionIssuesNotSoldList(user.getId());
		
		DollarHistoricalPriceEntity dollarHistoricalPriceEntity = dollarHistoricalPriceRespository.findLastRecord();
		BigDecimal dollarPriceAfterDeprecate = dollarHistoricalPriceEntity.getPrice().subtract(dollarHistoricalPriceEntity.getPrice().multiply(CatalogsStaticData.StaticData.DEFAULT_DOLAR_PRICE_DEPRECATE_PERCENTAGE).divide(BigDecimal.valueOf(100)));
		
		List<TransactionIssueTrackPojo> transactionIssuePojos = new ArrayList<>();
		
		for (TransactionIssueNotSoldPojo transactionIssueNotSoldPojo: transactionsNotSoldCustomPojos) {
			
			IssuesLastPriceTmpEntity issuesLastPriceTmpEntity = (IssuesLastPriceTmpEntity) genericPersistance.findById(IssuesLastPriceTmpEntity.class, transactionIssueNotSoldPojo.getIdIssue());
			CatalogBrokerEntity catalogBrokerEntity = (CatalogBrokerEntity) genericPersistance.findById(CatalogBrokerEntity.class, transactionIssueNotSoldPojo.getIdBroker());
			
			BigDecimal commision = transactionIssueNotSoldPojo.getIdBroker().equals(CatalogBrokerEnum.GBM_HOMBROKER.getValue()) ? CatalogsStaticData.StaticData.DEFAULT_COMMISION_GBM : CatalogsStaticData.StaticData.DEFAULT_COMMISION_CHARLES_SCHWAB;
			BigDecimal taxes = transactionIssueNotSoldPojo.getIdBroker().equals(CatalogBrokerEnum.GBM_HOMBROKER.getValue()) ? CatalogsStaticData.StaticData.DEFAULT_TAXES_PERCENTAGE_GBM : CatalogsStaticData.StaticData.DEFAULT_TAXES_PERCENTAGE_CHARLES_SCHWAB; 
			
			if (issuesLastPriceTmpEntity == null)
				continue;
			
			BigDecimal currentIssuePrice = issuesLastPriceTmpEntity.getLast();
			BigDecimal sellEstimate = new CalculatorUtil().determineFinalValue(currentIssuePrice, catalogBrokerEntity.getIdTypeCurrency(), dollarPriceAfterDeprecate);
			
			BigDecimal taxesOutcomeEstimate = sellEstimate.subtract(transactionIssueNotSoldPojo.getPriceTotalBuy()).multiply(taxes).divide(BigDecimal.valueOf(100));
			BigDecimal commisionOutcomeEstimate = sellEstimate.multiply(commision).divide(BigDecimal.valueOf(100));
			BigDecimal totalIncomeEstimate = sellEstimate.subtract(taxesOutcomeEstimate).subtract(commisionOutcomeEstimate);
			BigDecimal sellEstimateAfterAll = sellEstimate.subtract(taxesOutcomeEstimate).subtract(commisionOutcomeEstimate);
			BigDecimal gainLossTotal = sellEstimateAfterAll.subtract(transactionIssueNotSoldPojo.getPriceTotalBuy());
			BigDecimal gainLossPercentage = sellEstimateAfterAll.multiply(BigDecimal.valueOf(100)).divide(transactionIssueNotSoldPojo.getPriceTotalBuy(), RoundingMode.CEILING).subtract(BigDecimal.valueOf(100));
			
			TransactionIssueTrackPojo transactionIssueTrackPojo = new TransactionIssueTrackPojo();
			transactionIssueTrackPojo.setIdIssue(transactionIssueNotSoldPojo.getIdIssue());
			transactionIssueTrackPojo.setIssue(transactionIssueNotSoldPojo.getIssue());
			transactionIssueTrackPojo.setDate(dateUtil.getMillis(transactionIssueNotSoldPojo.getIdDate()));
			transactionIssueTrackPojo.setPriceBuy(transactionIssueNotSoldPojo.getPriceBuy());
			transactionIssueTrackPojo.setCommisionPercentage(transactionIssueNotSoldPojo.getCommisionPercentage());
			transactionIssueTrackPojo.setPriceTotalBuy(transactionIssueNotSoldPojo.getPriceTotalBuy());
			transactionIssueTrackPojo.setTotalTitles(transactionIssueNotSoldPojo.getTotalTitles());
			transactionIssueTrackPojo.setIssueSellEstimate(sellEstimate);
			
			transactionIssueTrackPojo.setCommisionOutcomeEstimate(commisionOutcomeEstimate);
			transactionIssueTrackPojo.setTaxesOutcomeEstimate(taxesOutcomeEstimate);
			transactionIssueTrackPojo.setTotalIncomeEstimate(totalIncomeEstimate);
			transactionIssueTrackPojo.setGainLossTotal(gainLossTotal);
			transactionIssueTrackPojo.setGainLossPercentage(gainLossPercentage);
			
			transactionIssueTrackPojo.setLast(issuesLastPriceTmpEntity.getLast());
			transactionIssueTrackPojo.setOpen(issuesLastPriceTmpEntity.getOpen());
			transactionIssueTrackPojo.setVolume(issuesLastPriceTmpEntity.getVolume());
			transactionIssueTrackPojo.setHigh(issuesLastPriceTmpEntity.getHigh());
			transactionIssueTrackPojo.setTimestamp(dateUtil.getMillis(issuesLastPriceTmpEntity.getTimestamp()));
			transactionIssueTrackPojo.setLastSaleTimestamp(dateUtil.getMillis(issuesLastPriceTmpEntity.getLastSaleTimestamp()));
			transactionIssueTrackPojo.setIsDownPercentageFromBuyPrice((calculatorUtil.calculatePercentageUpDown(transactionIssueNotSoldPojo.getPriceTotalBuy(), sellEstimate).compareTo(BigDecimal.valueOf(7)) < 0) + "");
			transactionIssueTrackPojo.setIsDownPercentageFromCurrentPrice((issuesLastPriceTmpEntity.getLast().compareTo(issuesLastPriceTmpEntity.getOpen().subtract(calculatorUtil.calculatePercentage(issuesLastPriceTmpEntity.getOpen(), BigDecimal.valueOf(7)))) < 1) + "");
			transactionIssueTrackPojo.setIsUpPercentageFromBuyPrice((calculatorUtil.calculatePercentageUpDown(transactionIssueNotSoldPojo.getPriceTotalBuy(), sellEstimate).compareTo(BigDecimal.valueOf(7)) > 0) + "");		
			transactionIssueTrackPojo.setTypeCurrency(catalogBrokerEntity.getIdTypeCurrency());
			transactionIssueTrackPojo.setDescriptionTypeCurrency(catalogBrokerEntity.getCatalogTypeCurrencyEntity().getDescription());
			
			IssuesManagerTrackPropertiesEntity issuesManagerTrackPropertiesEntity = (IssuesManagerTrackPropertiesEntity) genericPersistance.findById(IssuesManagerTrackPropertiesEntity.class, new IssuesManagerEntityPk(transactionIssueNotSoldPojo.getIdIssue(), user.getId()));
			if (issuesManagerTrackPropertiesEntity != null && issuesManagerTrackPropertiesEntity.getTrackBuyPrice() != null) {
				
				BigDecimal trackBuyPriceMxn = calculatorUtil.calculateSellEstimate(issuesManagerTrackPropertiesEntity.getTrackBuyPrice(), dollarHistoricalPriceEntity.getPrice());
				
				BigDecimal buyPriceTrack = calculatorUtil.calculatePercentage(issuesManagerTrackPropertiesEntity.getTrackBuyPrice(), BigDecimal.valueOf(2));
				buyPriceTrack = buyPriceTrack.add(issuesManagerTrackPropertiesEntity.getTrackBuyPrice());
				
				transactionIssueTrackPojo.setTrackBuyPriceMxn(trackBuyPriceMxn);
				transactionIssueTrackPojo.setTrackBuyPriceUsd(issuesManagerTrackPropertiesEntity.getTrackBuyPrice());
				transactionIssueTrackPojo.setIsNearPriceBuy((currentIssuePrice.compareTo(buyPriceTrack) <= 0) + "");
			}
				
			transactionIssueTrackPojo.setIdTrack(transactionIssueNotSoldPojo.getIdIssue() + "_" + transactionIssueNotSoldPojo.getPriceBuy() + "_" 
					+ transactionIssueTrackPojo.getIsDownPercentageFromBuyPrice() + "_" + transactionIssueTrackPojo.getIsDownPercentageFromCurrentPrice() + "_"
					+ transactionIssueTrackPojo.getIsUpPercentageFromBuyPrice() + "_" + transactionIssueTrackPojo.getIsNearPriceBuy());
			
			transactionIssuePojos.add(transactionIssueTrackPojo);
		}
		
		return transactionIssuePojos;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public GetTransactionIssuestrackDataPojo executeGetTransactionIssuesTrack(GetTransactionIssuestrackRequestPojo requestPojo) {
		
		UserEntity userEntity = userRepository.findByUserName(requestPojo.getUserName());
		
		GetTransactionIssuestrackDataPojo responsePojo = new GetTransactionIssuestrackDataPojo();
		responsePojo.setTransactionIssuesTrackList(getTransactionIssuesTrackPojos(userEntity));
		
		return responsePojo;
	}
}
