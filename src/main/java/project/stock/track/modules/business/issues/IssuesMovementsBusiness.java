package project.stock.track.modules.business.issues;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lib.base.backend.modules.security.jwt.entity.UserEntity;
import lib.base.backend.pojo.datatable.DataTablePojo;
import project.stock.track.app.beans.entity.CatalogIssuesEntity;
import project.stock.track.app.beans.entity.CatalogSectorEntity;
import project.stock.track.app.beans.entity.DollarHistoricalPriceEntity;
import project.stock.track.app.beans.entity.IssuesManagerEntity;
import project.stock.track.app.beans.entity.IssuesManagerTrackPropertiesEntity;
import project.stock.track.app.beans.entity.IssuesMovementsEntity;
import project.stock.track.app.beans.pojos.business.issues.IssueCurrentPricePojo;
import project.stock.track.app.beans.pojos.business.issues.IssueMovementResumePojo;
import project.stock.track.app.beans.pojos.business.issues.IssueMovementTransactionPojo;
import project.stock.track.app.beans.pojos.business.issues.IssueMovementTransactionTotalResumePojo;
import project.stock.track.app.beans.pojos.business.issues.IssuesMovementsFiltersPojo;
import project.stock.track.app.beans.pojos.entity.IssueMovementBuyEntityPojo;
import project.stock.track.app.beans.pojos.petition.data.GetIssuesMovementsListDataPojo;
import project.stock.track.app.beans.pojos.petition.request.GetIssuesMovementsListRequestPojo;
import project.stock.track.app.repository.DollarHistoricalPriceRepositoryImpl;
import project.stock.track.app.repository.IssuesHistoricalRepositoryImpl;
import project.stock.track.app.repository.IssuesMovementsRepositoryImpl;
import project.stock.track.app.utils.CalculatorUtil;
import project.stock.track.app.utils.IssueUtil;
import project.stock.track.app.vo.catalogs.CatalogsEntity;
import project.stock.track.modules.business.MainBusiness;

@Component
public class IssuesMovementsBusiness extends MainBusiness {
	
	IssuesMovementsRepositoryImpl issuesMovementsRepository;
	
	IssuesHistoricalRepositoryImpl issuesHistoricalRepository;
	
	DollarHistoricalPriceRepositoryImpl dollarHistoricalPriceRepository;
	
	IssueUtil issueUtil;
	
	CalculatorUtil calculatorUtil;
	
	@Autowired
	public IssuesMovementsBusiness(IssuesMovementsRepositoryImpl issuesMovementsRepository, IssuesHistoricalRepositoryImpl issuesHistoricalRepository,
			DollarHistoricalPriceRepositoryImpl dollarHistoricalPriceRepository, IssueUtil issueUtil, CalculatorUtil calculatorUtil) {
		this.issuesMovementsRepository = issuesMovementsRepository;
		this.issuesHistoricalRepository = issuesHistoricalRepository;
		this.dollarHistoricalPriceRepository = dollarHistoricalPriceRepository;
		this.issueUtil = issueUtil;
		this.calculatorUtil = calculatorUtil;
	}
	
	private String getAlertBuy(List<IssueMovementBuyEntityPojo> issueMovementBuysPojos, BigDecimal currentPrice) {
		
		String result = null;
		
		if (!issueMovementBuysPojos.isEmpty()) {
			
			BigDecimal priceBase = issueMovementBuysPojos.get(0).getBuyPrice();
		
			IssueMovementBuyEntityPojo lastMovement = issueMovementBuysPojos.get(issueMovementBuysPojos.size() - 1);
			BigDecimal variationPercentage = currentPrice != null ? calculatorUtil.calculatePercentageUpDown(priceBase, currentPrice) : null;
			
			if (variationPercentage != null && variationPercentage.compareTo(BigDecimal.valueOf(lastMovement.getBuyTransactionNumber() * (long)-10)) <= 0) {
				result = "BUY: Alert buy for transaction " + (lastMovement.getBuyTransactionNumber() + 1) + ".";
			}
		}
		
		return result;
		
	}
	
	private String getAlertSell(BigDecimal currentPrice, BigDecimal lastBuyPrice) {
		
		if (currentPrice == null)
			return null;
		
		BigDecimal percentageDifference = calculatorUtil.calculatePercentageUpDown(lastBuyPrice, currentPrice).setScale(2, RoundingMode.HALF_UP);
		
		return percentageDifference.compareTo(BigDecimal.valueOf(20)) >= 0 ? "SELL: Current price is " + percentageDifference + " % over last buy price (" + lastBuyPrice + ")" : null; 
	}
	
	private String getAlerts(List<IssueMovementBuyEntityPojo> issueMovementBuysPojos, BigDecimal currentPrice) {
		
		if(issueMovementBuysPojos.isEmpty())
			return null;
		
		String alert = null; 
		
		if (getAlertSell(currentPrice, issueMovementBuysPojos.getLast().getBuyPrice()) != null) {
			
			alert = getAlertSell(currentPrice, issueMovementBuysPojos.getLast().getBuyPrice());
		}
		else {
			
			alert = getAlertBuy(issueMovementBuysPojos, currentPrice);
		}
		
		return alert;
	}
	
	private IssueMovementTransactionPojo buildIssueMovementTransactionPojo(BigDecimal currentPrice, BigDecimal totalShares, BigDecimal totalSellPrice, BigDecimal totalBuyPrice, boolean isForSold) {
		
		BigDecimal avgCostByShare = BigDecimal.ZERO;
		BigDecimal performanceByShare = BigDecimal.ZERO;
		BigDecimal performanceTotal = BigDecimal.ZERO;
		BigDecimal performancePercentage = BigDecimal.ZERO;
		BigDecimal currentPriceValue = BigDecimal.ZERO;
		
		if (isForSold) {
			avgCostByShare = totalShares.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO : totalBuyPrice.divide(totalShares, 5, RoundingMode.HALF_DOWN);
			performanceTotal = totalSellPrice.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO : totalSellPrice.subtract(totalBuyPrice);
			performanceByShare = (totalSellPrice.compareTo(BigDecimal.ZERO) == 0 || totalShares.compareTo(BigDecimal.ZERO) == 0) ? BigDecimal.ZERO : performanceTotal.divide(totalShares, 5, RoundingMode.HALF_DOWN);
			performancePercentage = totalSellPrice.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO : calculatorUtil.calculatePercentageUpDown(totalBuyPrice, totalSellPrice);
			currentPriceValue = totalSellPrice.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO : totalSellPrice.divide(totalShares, 5, RoundingMode.HALF_DOWN);
		}
		else if(totalShares.compareTo(BigDecimal.ZERO) != 0) {
			avgCostByShare = totalBuyPrice.divide(totalShares, 5, RoundingMode.HALF_DOWN);
			performanceTotal = (currentPrice.multiply(totalShares)).subtract(totalBuyPrice);
			performanceByShare = performanceTotal.divide(totalShares, 5, RoundingMode.HALF_DOWN);
			performancePercentage = calculatorUtil.calculatePercentageUpDown(totalBuyPrice, currentPrice.multiply(totalShares));
			currentPriceValue = currentPrice;
		}
		
		IssueMovementTransactionPojo issueMovementTransactionPojo = new IssueMovementTransactionPojo();
		issueMovementTransactionPojo.setTotalShares(totalShares);
		issueMovementTransactionPojo.setAvgCostByShare(avgCostByShare);
		issueMovementTransactionPojo.setPerformanceByShare(performanceByShare);
		issueMovementTransactionPojo.setPerformanceTotal(performanceTotal);
		issueMovementTransactionPojo.setPerformancePercentage(performancePercentage);
		issueMovementTransactionPojo.setCurrentPriceByShare(currentPriceValue);
		
		return issueMovementTransactionPojo;
	}
	
	private IssueMovementTransactionPojo buildIssueMovementTransaction(List<IssueMovementBuyEntityPojo> issueMovementBuysPojoList, BigDecimal currentPrice, boolean isForSold) {
		
		BigDecimal totalShares = BigDecimal.ZERO;
		BigDecimal totalSellPrice = BigDecimal.ZERO;
		BigDecimal totalBuyPrice = BigDecimal.ZERO;
		
		for (IssueMovementBuyEntityPojo issueMovementBuyPojo : issueMovementBuysPojoList) {
			
			totalBuyPrice = totalBuyPrice.add((issueMovementBuyPojo.getBuyPrice().multiply(issueMovementBuyPojo.getTotalShares())));
			
			if (isForSold && issueMovementBuyPojo.getSellPrice() != null) {
				totalShares = totalShares.add(issueMovementBuyPojo.getTotalShares());
				totalSellPrice = totalSellPrice.add(issueMovementBuyPojo.getSellPrice().multiply(issueMovementBuyPojo.getTotalShares()));
			}
			else if (!isForSold && issueMovementBuyPojo.getSellPrice() == null) {
				totalShares = totalShares.add(issueMovementBuyPojo.getTotalShares());
			}
		}
		
		return buildIssueMovementTransactionPojo(currentPrice, totalShares, totalSellPrice, totalBuyPrice, isForSold);
		
	}
	
	private IssueMovementTransactionTotalResumePojo buildIssueMovementTransactionTotal(List<IssueMovementResumePojo> issueMovementResumePojos, DollarHistoricalPriceEntity dollarHistoricalPriceEntity, boolean isForSold) {
		
		BigDecimal totalCurrentPrice = BigDecimal.ZERO;
		BigDecimal totalBuyPrice = BigDecimal.ZERO;
		BigDecimal totalCurrentPriceSet;
		BigDecimal totalBuyPriceSet;
		
		for (IssueMovementResumePojo issueMovementResumePojo : issueMovementResumePojos) {
			
			if (isForSold) {
				totalCurrentPriceSet = issueMovementResumePojo.getIssueMovementTransactionSold().getCurrentPriceByShare().multiply(issueMovementResumePojo.getIssueMovementTransactionSold().getTotalShares());
				totalBuyPriceSet = issueMovementResumePojo.getIssueMovementTransactionSold().getAvgCostByShare().multiply(issueMovementResumePojo.getIssueMovementTransactionSold().getTotalShares());
			}
			else {
				totalCurrentPriceSet = issueMovementResumePojo.getIssueMovementTransactionNotSold().getCurrentPriceByShare().multiply(issueMovementResumePojo.getIssueMovementTransactionNotSold().getTotalShares());
				totalBuyPriceSet = issueMovementResumePojo.getIssueMovementTransactionNotSold().getAvgCostByShare().multiply(issueMovementResumePojo.getIssueMovementTransactionNotSold().getTotalShares());
			}
			
			if(issueMovementResumePojo.getIdBroker().equals(CatalogsEntity.CatalogBroker.GBM_HOMBROKER)) {
				totalCurrentPriceSet = totalCurrentPriceSet.divide(dollarHistoricalPriceEntity.getPrice(), 5, RoundingMode.HALF_DOWN);
				totalBuyPriceSet = totalBuyPriceSet.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO : totalBuyPriceSet.divide(dollarHistoricalPriceEntity.getPrice(), 5, RoundingMode.HALF_DOWN);
			}
			
			totalCurrentPrice = totalCurrentPrice.add(totalCurrentPriceSet);
			totalBuyPrice = totalBuyPrice.add(totalBuyPriceSet);
		}
		
		IssueMovementTransactionTotalResumePojo issueMovementTransactionTotalResumePojo = new IssueMovementTransactionTotalResumePojo();
		issueMovementTransactionTotalResumePojo.setTotalCurrentPrice(totalCurrentPrice);
		issueMovementTransactionTotalResumePojo.setTotalBuyPrice(totalBuyPrice);
		issueMovementTransactionTotalResumePojo.setPerformancePercentage(totalCurrentPrice.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO : calculatorUtil.calculatePercentageUpDown(totalBuyPrice, totalCurrentPrice));
		issueMovementTransactionTotalResumePojo.setPerformanceTotal(totalCurrentPrice.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO : totalCurrentPrice.subtract(totalBuyPrice));
		
		return issueMovementTransactionTotalResumePojo;
	}
	
	private IssueMovementResumePojo buildIssueMovementData(IssuesMovementsEntity issueMovement, List<IssueMovementBuyEntityPojo> issueMovementBuysPojoList, DollarHistoricalPriceEntity dollarHistoricalPriceEntity, IssueCurrentPricePojo currentPriceData) {
		
		IssuesManagerEntity managerIssuesEntity = issueMovement.getIssuesManagerEntity();
		CatalogIssuesEntity catalogIssuesEntity = managerIssuesEntity.getCatalogIssueEntity();
		CatalogSectorEntity catalogSectorEntity = catalogIssuesEntity.getCatalogSectorEntity();
		
		BigDecimal fairValue = dataUtil.getValueOrNull(managerIssuesEntity.getIssuesManagerTrackPropertiesEntity(), IssuesManagerTrackPropertiesEntity::getFairValue);
		BigDecimal currentPriceCurrency = null;
		
		if(currentPriceData.getCurrentPrice() != null)
			currentPriceCurrency = issueMovement.getCatalogBrokerEntity().getIdTypeCurrency().equals(CatalogsEntity.CatalogBroker.GBM_HOMBROKER) ? dollarHistoricalPriceEntity.getPrice().multiply(currentPriceData.getCurrentPrice()) : currentPriceData.getCurrentPrice();
		
		
		IssueMovementResumePojo issueMovementPojo = new IssueMovementResumePojo();
		issueMovementPojo.setIdIssueMovement(issueMovement.getId());
		issueMovementPojo.setIdIssue(managerIssuesEntity.getId().getIdIssue());
		issueMovementPojo.setIssue(catalogIssuesEntity.getInitials());
		issueMovementPojo.setIdStatus(issueMovement.getIdStatus());
		issueMovementPojo.setIssueMovementBuysList(issueMovementBuysPojoList);
		issueMovementPojo.setCurrentPrice(currentPriceData.getCurrentPrice());
		issueMovementPojo.setCurrentPriceDate(currentPriceData.getDate());
		issueMovementPojo.setAlert(getAlerts(issueMovementBuysPojoList, currentPriceCurrency));
		issueMovementPojo.setIdBroker(issueMovement.getCatalogBrokerEntity().getId());
		issueMovementPojo.setDescriptionBroker(issueMovement.getCatalogBrokerEntity().getAcronym());
		issueMovementPojo.setDescriptionCurrency(issueMovement.getCatalogBrokerEntity().getCatalogTypeCurrencyEntity().getDescription());
		issueMovementPojo.setDescriptionStatus(issueMovement.getCatalogStatusIssueMovementEntity().getDescription());
		issueMovementPojo.setIdSector(dataUtil.getValueOrNull(catalogSectorEntity, CatalogSectorEntity::getId));
		issueMovementPojo.setDescriptionSector(dataUtil.getValueOrNull(catalogSectorEntity, CatalogSectorEntity::getDescription));
		issueMovementPojo.setFairValue(fairValue != null ? fairValue.toPlainString() : null);
		issueMovementPojo.setPriceMovement(issueMovement.getPriceMovement());
		issueMovementPojo.setIssueMovementTransactionNotSold(buildIssueMovementTransaction(issueMovementBuysPojoList, currentPriceCurrency, false));
		issueMovementPojo.setIssueMovementTransactionSold(buildIssueMovementTransaction(issueMovementBuysPojoList, currentPriceCurrency, true));
		
		if (!issueMovementBuysPojoList.isEmpty() && currentPriceData.getCurrentPrice() != null)
			issueMovementPojo.setIssuePerformance(calculatorUtil.calculatePercentageUpDown(issueMovementBuysPojoList.getLast().getBuyPrice(), currentPriceCurrency).toPlainString());
	
		return issueMovementPojo;
	}
	
	private List<IssueMovementBuyEntityPojo> buildIssueMovementBuy(IssuesMovementsEntity issueMovement) {
		
		return mapEntityToPojoUtil.mapIssueMovementBuyList(issueMovement.getIssuesMovementsBuys());
	}

	public GetIssuesMovementsListDataPojo getIssuesMovements(Integer idUser, DataTablePojo<IssuesMovementsFiltersPojo> dataTableConfig) {
		
		List<IssuesMovementsEntity> issuesMovements = issuesMovementsRepository.findAllIssuesMovements(idUser, dataTableConfig.getFilters());
		Long totalIssuesMovements = issuesMovementsRepository.findCountIssuesMovements(idUser, dataTableConfig.getFilters());
		DollarHistoricalPriceEntity dollarHistoricalPriceEntity = dollarHistoricalPriceRepository.findLastRecord();
		
		List<IssueMovementResumePojo> issueMovementPojos = new ArrayList<>();
		
		for (IssuesMovementsEntity issueMovementEntity: issuesMovements) {
			
			if(issueMovementEntity.getIssuesManagerEntity().getCatalogIssueEntity().getInitials().equals("VTR"))
			{
				System.out.println("VTR");
			}
			
			List<IssueMovementBuyEntityPojo> issueMovementBuysPojosList = buildIssueMovementBuy(issueMovementEntity);
			
			IssuesManagerEntity managerIssuesEntity = issueMovementEntity.getIssuesManagerEntity();
			IssueCurrentPricePojo currentPriceData = issueUtil.getCurrentPrice(managerIssuesEntity.getCatalogIssueEntity().getTempIssuesLastPriceEntity(), null);
			
			IssueMovementResumePojo issueMovementPojo = buildIssueMovementData(issueMovementEntity, issueMovementBuysPojosList, dollarHistoricalPriceEntity, currentPriceData);
			issueMovementPojos.add(issueMovementPojo);
		}
		
		GetIssuesMovementsListDataPojo dataPojo = new GetIssuesMovementsListDataPojo();
		dataPojo.setIssuesMovementsList(issueMovementPojos);
		dataPojo.setTotalIssuesMovements(totalIssuesMovements);
		dataPojo.setIssueMovementTransactionTotalSold(buildIssueMovementTransactionTotal(issueMovementPojos, dollarHistoricalPriceEntity, true));
		dataPojo.setIssueMovementTransactionTotalNotSold(buildIssueMovementTransactionTotal(issueMovementPojos, dollarHistoricalPriceEntity, false));
		
		IssueMovementTransactionTotalResumePojo issueMovementTransactionTotalResumePojo = new IssueMovementTransactionTotalResumePojo();
		issueMovementTransactionTotalResumePojo.setTotalCurrentPrice(dataPojo.getIssueMovementTransactionTotalNotSold().getTotalCurrentPrice().add(dataPojo.getIssueMovementTransactionTotalSold().getTotalCurrentPrice()));
		issueMovementTransactionTotalResumePojo.setTotalBuyPrice(dataPojo.getIssueMovementTransactionTotalNotSold().getTotalBuyPrice().add(dataPojo.getIssueMovementTransactionTotalSold().getTotalBuyPrice()));
		issueMovementTransactionTotalResumePojo.setPerformanceTotal(issueMovementTransactionTotalResumePojo.getTotalCurrentPrice().subtract(issueMovementTransactionTotalResumePojo.getTotalBuyPrice()));
		issueMovementTransactionTotalResumePojo.setPerformancePercentage((issueMovementTransactionTotalResumePojo.getPerformanceTotal().divide(issueMovementTransactionTotalResumePojo.getTotalBuyPrice(), 5, RoundingMode.HALF_DOWN).multiply(new BigDecimal(100))));
		dataPojo.setIssueMovementTransactionTotal(issueMovementTransactionTotalResumePojo);
		
		return dataPojo;
	}

	@Transactional(rollbackFor = Exception.class)
	public GetIssuesMovementsListDataPojo executeGetIssuesMovements(GetIssuesMovementsListRequestPojo requestPojo) {
		
		UserEntity userEntity = userRepository.findByUserName(requestPojo.getUserName());
		
		return getIssuesMovements(userEntity.getId(), requestPojo.getDataTableConfig());
	}
}
