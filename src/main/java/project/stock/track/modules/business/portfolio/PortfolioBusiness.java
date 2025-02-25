package project.stock.track.modules.business.portfolio;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lib.base.backend.modules.security.jwt.entity.UserEntity;
import lib.base.backend.modules.security.jwt.repository.UserRepositoryImpl;
import lib.base.backend.persistance.GenericPersistence;
import lombok.RequiredArgsConstructor;
import project.stock.track.app.beans.entity.CatalogBrokerEntity;
import project.stock.track.app.beans.pojos.business.portfolio.PorfolioIssuePojo;
import project.stock.track.app.beans.pojos.business.portfolio.PortfolioResumePojo;
import project.stock.track.app.beans.pojos.petition.data.GetPortfolioDataPojo;
import project.stock.track.app.beans.pojos.petition.data.GetPortfolioListDataPojo;
import project.stock.track.app.beans.pojos.petition.request.GetPortfolioListRequestPojo;
import project.stock.track.app.beans.pojos.petition.request.GetPortfolioRequestPojo;
import project.stock.track.app.repository.TransactionIssueRepositoryImpl;
import project.stock.track.app.repository.TransactionMoneyRepositoryImpl;
import project.stock.track.app.utils.CalculatorUtil;
import project.stock.track.app.vo.entities.CatalogTypeMovementEnum;
import project.stock.track.modules.business.MainBusiness;

@RequiredArgsConstructor
@Component
public class PortfolioBusiness extends MainBusiness {
	
	@SuppressWarnings("rawtypes")
	private final GenericPersistence genericPersistance;
	private final UserRepositoryImpl userRepository;
	private final TransactionIssueRepositoryImpl transactionIssueRepository;
	private final TransactionMoneyRepositoryImpl transactionMoneyRepository;
	
	private CalculatorUtil calculatorUtil = new CalculatorUtil();
	
	public BigDecimal getTotalMoneyDeposits(int idBroker, int idUser) {
		
		BigDecimal deposits = transactionMoneyRepository.findTotalMovementMoney(CatalogTypeMovementEnum.DEPOSIT.getValue(), idBroker, idUser);
		BigDecimal withdraws = transactionMoneyRepository.findTotalMovementMoney(CatalogTypeMovementEnum.WITHDRAW.getValue(), idBroker, idUser);
		
		return deposits.subtract(withdraws);
	}
	
	public PortfolioResumePojo getTotalPortfolio(CatalogBrokerEntity catalogBrokerEntity, UserEntity userEntity) {
		
		BigDecimal totalMoneyDeposits = getTotalMoneyDeposits(catalogBrokerEntity.getId(), userEntity.getId());
		BigDecimal totalDividends = transactionMoneyRepository.findTotalMovementMoney(CatalogTypeMovementEnum.DIVIDEND.getValue(), catalogBrokerEntity.getId(), userEntity.getId());
		BigDecimal totalDividendTaxes = transactionMoneyRepository.findTotalMovementMoney(CatalogTypeMovementEnum.TAX.getValue(), catalogBrokerEntity.getId(), userEntity.getId());
		BigDecimal totalCashInLieu = transactionMoneyRepository.findTotalMovementMoney(CatalogTypeMovementEnum.CASH_IN_LIEU.getValue(), catalogBrokerEntity.getId(), userEntity.getId());
		BigDecimal totalIssueFee = transactionMoneyRepository.findTotalMovementMoney(CatalogTypeMovementEnum.ISSUE_FEE.getValue(), catalogBrokerEntity.getId(), userEntity.getId());
		BigDecimal totalBankInterest = transactionMoneyRepository.findTotalMovementMoney(CatalogTypeMovementEnum.BANK_INTEREST.getValue(), catalogBrokerEntity.getId(), userEntity.getId());
		BigDecimal totalBankFee = transactionMoneyRepository.findTotalMovementMoney(CatalogTypeMovementEnum.BANK_FEE.getValue(), catalogBrokerEntity.getId(), userEntity.getId());
		
		BigDecimal totalMoney = totalMoneyDeposits.add(totalDividends).subtract(totalDividendTaxes).add(totalCashInLieu).subtract(totalIssueFee).add(totalBankInterest).subtract(totalBankFee);
		
		BigDecimal totalSecuritesValueBuyNotSold = transactionIssueRepository.findTotalBuys(catalogBrokerEntity.getId(), catalogBrokerEntity.getIdTypeCurrency(), userEntity.getId(), false);
		BigDecimal totalSecuritesValueBuySold = transactionIssueRepository.findTotalBuys(catalogBrokerEntity.getId(), catalogBrokerEntity.getIdTypeCurrency(), userEntity.getId(), true);
		
		BigDecimal totalSecuritesValueSellNotSold = transactionIssueRepository.findTotalSells(catalogBrokerEntity.getId(), catalogBrokerEntity.getIdTypeCurrency(), userEntity.getId(), false);
		BigDecimal totalSecuritesValueSellSold = transactionIssueRepository.findTotalSells(catalogBrokerEntity.getId(), catalogBrokerEntity.getIdTypeCurrency(), userEntity.getId(), true);
		
		BigDecimal totalSecuritesGainSold = totalSecuritesValueSellSold.subtract(totalSecuritesValueBuySold);
		BigDecimal totalSecuritesGainNotSold = totalSecuritesValueSellNotSold.subtract(totalSecuritesValueBuyNotSold);
		BigDecimal totalSecuritesGain = totalSecuritesGainSold.add(totalSecuritesGainNotSold);
		BigDecimal totalCash = totalMoney.subtract(totalSecuritesValueBuyNotSold).add(totalSecuritesGainSold);
		
		BigDecimal yieldBroker = calculatorUtil.calculatePercentageUpDown(totalMoney, totalMoney.add(totalSecuritesGain));
		
		PortfolioResumePojo portfolioResumePojo = new PortfolioResumePojo();
		portfolioResumePojo.setIdBroker(catalogBrokerEntity.getId());
		portfolioResumePojo.setBroker(catalogBrokerEntity.getAcronym());
		portfolioResumePojo.setTotalDeposits(totalMoney);
		portfolioResumePojo.setTotalGainLoss(totalSecuritesGain);
		portfolioResumePojo.setTotalCash(totalCash);
		portfolioResumePojo.setTotalDividends(totalDividends);
		portfolioResumePojo.setTotalDividendTaxes(totalDividendTaxes);
		portfolioResumePojo.setTotalCashInLieu(totalCashInLieu);
		portfolioResumePojo.setTotalBankInterests(totalBankInterest);
		portfolioResumePojo.setTotalFees(totalBankFee.add(totalIssueFee));
		portfolioResumePojo.setIdTypeCurrency(catalogBrokerEntity.getIdTypeCurrency());
		portfolioResumePojo.setTypeCurrency(catalogBrokerEntity.getCatalogTypeCurrencyEntity().getDescription());
		portfolioResumePojo.setYield(yieldBroker);
		
		return portfolioResumePojo;
		
	}
	
	@SuppressWarnings("unchecked")
	public List<PortfolioResumePojo> getPortfolios(UserEntity userEntity) {
		
		List<CatalogBrokerEntity> catalogBrokerEntities = genericPersistance.findAll(CatalogBrokerEntity.class);
		
		List<PortfolioResumePojo> portfolioResumePojos = new ArrayList<>();
		
		for (CatalogBrokerEntity catalogBrokerEntity: catalogBrokerEntities) {
			
			PortfolioResumePojo brokerResumePojo = getTotalPortfolio(catalogBrokerEntity, userEntity);
			portfolioResumePojos.add(brokerResumePojo);
		}
		
		return portfolioResumePojos;
	}

	@Transactional(rollbackFor = Exception.class)
	public GetPortfolioListDataPojo executeGetPortfolioList(GetPortfolioListRequestPojo requestPojo) {
		
		UserEntity userEntity = userRepository.findByUserName(requestPojo.getUserName());
		
		GetPortfolioListDataPojo responsePojo = new GetPortfolioListDataPojo();
		responsePojo.setPortfolioList(getPortfolios(userEntity));
		return responsePojo;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(rollbackFor = Exception.class)
	public GetPortfolioDataPojo executeGetPortfolioData(GetPortfolioRequestPojo requestPojo) {
		
		UserEntity userEntity = userRepository.findByUserName(requestPojo.getUserName());
		
		CatalogBrokerEntity catalogBrokerEntity = (CatalogBrokerEntity) genericPersistance.findById(CatalogBrokerEntity.class, requestPojo.getIdBroker());
		
		PortfolioResumePojo portfolioResumePojo = getTotalPortfolio(catalogBrokerEntity, userEntity);
		
		CatalogBrokerEntity catalogBroker = (CatalogBrokerEntity) genericPersistance.findById(CatalogBrokerEntity.class, requestPojo.getIdBroker());
		
		List<PorfolioIssuePojo> portfolioIssuePojosNotSold = transactionIssueRepository.findPortfolioIssues(userEntity.getId(), requestPojo.getIdBroker(), catalogBroker.getIdTypeCurrency(), false);
		List<PorfolioIssuePojo> portfolioIssuePojosSold = transactionIssueRepository.findPortfolioIssues(userEntity.getId(), requestPojo.getIdBroker(), catalogBroker.getIdTypeCurrency(), true);
		
		GetPortfolioDataPojo dataPojo = new GetPortfolioDataPojo();
		dataPojo.setPortfolioResume(portfolioResumePojo);
		dataPojo.setPortfolioIssuesNotSold(portfolioIssuePojosNotSold);
		dataPojo.setPortfolioIssuesSold(portfolioIssuePojosSold);
		
		return dataPojo;
	}
}
