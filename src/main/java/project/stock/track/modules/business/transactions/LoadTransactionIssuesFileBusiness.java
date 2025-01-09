package project.stock.track.modules.business.transactions;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import lib.base.backend.exception.data.BusinessException;
import lib.base.backend.modules.security.jwt.entity.UserEntity;
import lib.base.backend.modules.security.jwt.repository.UserRepositoryImpl;
import lib.base.backend.persistance.GenericPersistence;
import lombok.RequiredArgsConstructor;
import project.stock.track.app.beans.entity.CatalogBrokerEntity;
import project.stock.track.app.beans.entity.CatalogIssuesEntity;
import project.stock.track.app.beans.entity.DollarHistoricalPriceEntity;
import project.stock.track.app.beans.entity.TransactionIssueEntity;
import project.stock.track.app.beans.pojos.business.transaction.CurrencyValuesPojo;
import project.stock.track.app.beans.pojos.business.transaction.TransactionIssueFilePojo;
import project.stock.track.app.beans.pojos.petition.data.LoadTransactionIssuesFileDataPojo;
import project.stock.track.app.beans.pojos.petition.request.LoadTransactionIssuesFileRequestPojo;
import project.stock.track.app.repository.DollarHistoricalPriceRepositoryImpl;
import project.stock.track.app.repository.IssuesRepositoryImpl;
import project.stock.track.app.repository.TransactionIssueRepositoryImpl;
import project.stock.track.app.utils.CalculatorUtil;
import project.stock.track.app.utils.CustomArraysUtil;
import project.stock.track.app.utils.ReadCsvFileUtil;
import project.stock.track.app.vo.catalogs.CatalogsEntity;
import project.stock.track.app.vo.catalogs.CatalogsEntity.CatalogTypeCurrency;
import project.stock.track.app.vo.catalogs.CatalogsErrorMessage;
import project.stock.track.app.vo.catalogs.CatalogsStaticData;
import project.stock.track.config.helpers.CurrencyDataHelper;
import project.stock.track.modules.business.MainBusiness;
import project.stock.track.modules.business.files.transactions.ReadCsvTransactionIssues;
import project.stock.track.modules.business.files.transactions.ReadCsvTransactionIssuesBrokerGbm;
import project.stock.track.modules.business.files.transactions.ReadCsvTransactionIssuesBrokerSchwab;

@SuppressWarnings("rawtypes")
@Component
@RequiredArgsConstructor
public class LoadTransactionIssuesFileBusiness extends MainBusiness {
	
	private final GenericPersistence genericPersistance;
	
	@Qualifier("customPersistance")
	private final GenericPersistence genericCustomPersistance;
	private final UserRepositoryImpl userRepository;
	private final IssuesRepositoryImpl issuesRepository;
	private final TransactionIssueRepositoryImpl transactionIssueRepository;
	private final DollarHistoricalPriceRepositoryImpl dollarHistoricalPriceRepository;
	private final CurrencyDataHelper currencyDataHelper;
	
	private CustomArraysUtil customArraysUtil = new CustomArraysUtil();
	
	private static final String MSG_DATE = " date: ";
	
	private TransactionIssueEntity buildTransactionIssueEntity(UserEntity userEntity, CatalogIssuesEntity catalogIssuesEntityVerify, TransactionIssueFilePojo transactionIssueFilePojo, BigDecimal dollarPrice) {
		
		BigDecimal titles = transactionIssueFilePojo.getIsSlice() ? transactionIssueFilePojo.getTitles() : new BigDecimal(1);
		
		CurrencyValuesPojo currencyValuesPriceBuyPojo = currencyDataHelper.getCurrencyValues(transactionIssueFilePojo.getTypeCurrency(), dollarPrice, transactionIssueFilePojo.getPrice());
		CurrencyValuesPojo currencyValuesPriceTotalBuyPojo = currencyDataHelper.getCurrencyValues(transactionIssueFilePojo.getTypeCurrency(), dollarPrice, transactionIssueFilePojo.getPrice().multiply(titles));
		
		TransactionIssueEntity transactionIssueEntity = new TransactionIssueEntity();
		transactionIssueEntity.setIdIssue(catalogIssuesEntityVerify.getId());
		transactionIssueEntity.setIdUser(userEntity.getId());
		transactionIssueEntity.setBuyDate(dateUtil.getLocalDateTime(transactionIssueFilePojo.getDate()));
		transactionIssueEntity.setPriceBuy(currencyValuesPriceBuyPojo.getValueUsd());
		transactionIssueEntity.setPriceBuyMxn(currencyValuesPriceBuyPojo.getValueMxn());
		transactionIssueEntity.setCommisionPercentage(transactionIssueFilePojo.getComissionPercentage());
		transactionIssueEntity.setPriceTotalBuy(currencyValuesPriceTotalBuyPojo.getValueUsd().multiply(transactionIssueFilePojo.getComissionPercentage()).divide(BigDecimal.valueOf(100)).add(currencyValuesPriceTotalBuyPojo.getValueUsd()));
		transactionIssueEntity.setPriceTotalBuyMxn(currencyValuesPriceTotalBuyPojo.getValueMxn().multiply(transactionIssueFilePojo.getComissionPercentage()).divide(BigDecimal.valueOf(100)).add(currencyValuesPriceTotalBuyPojo.getValueMxn()));
		transactionIssueEntity.setIdBroker(transactionIssueFilePojo.getBroker());
		transactionIssueEntity.setIsSlice(transactionIssueFilePojo.getIsSlice());
		transactionIssueEntity.setTotalShares(titles);
		
		return transactionIssueEntity;
	}
	
	private TransactionIssueEntity buildTransactionSellIssueEntity(TransactionIssueEntity transactionIssueEntity, CatalogIssuesEntity catalogIssuesEntityVerify, TransactionIssueFilePojo transactionIssueFilePojo, CurrencyValuesPojo currencyValuesPriceSellPojo, BigDecimal titles) {
		
		BigDecimal priceTotalSell = currencyValuesPriceSellPojo.getValueUsd().multiply(titles);
		BigDecimal priceTotalSellMxn = currencyValuesPriceSellPojo.getValueMxn().multiply(titles);
		
		transactionIssueEntity.setIdIssue(catalogIssuesEntityVerify.getId());
		transactionIssueEntity.setSellTaxesPercentage(transactionIssueFilePojo.getTaxesPercentage());
		transactionIssueEntity.setSellDate(dateUtil.getLocalDateTime(transactionIssueFilePojo.getDate()));
		transactionIssueEntity.setPriceSell(currencyValuesPriceSellPojo.getValueUsd());
		transactionIssueEntity.setPriceSellMxn(currencyValuesPriceSellPojo.getValueMxn());
		transactionIssueEntity.setSellCommisionPercentage(transactionIssueFilePojo.getComissionPercentage());
		transactionIssueEntity.setPriceTotalSell(new CalculatorUtil().calculateTotalPriceSellAfter(transactionIssueEntity.getPriceTotalBuy(), priceTotalSell, transactionIssueFilePojo.getComissionPercentage(), transactionIssueFilePojo.getTaxesPercentage()));
		transactionIssueEntity.setPriceTotalSellMxn(new CalculatorUtil().calculateTotalPriceSellAfter(transactionIssueEntity.getPriceTotalBuyMxn(), priceTotalSellMxn, transactionIssueFilePojo.getComissionPercentage(), transactionIssueFilePojo.getTaxesPercentage()));
		transactionIssueEntity.setSellGainLossTotal(transactionIssueEntity.getPriceTotalSell().subtract(transactionIssueEntity.getPriceTotalBuy()));
		transactionIssueEntity.setSellGainLossTotalMxn(transactionIssueEntity.getPriceTotalSellMxn().subtract(transactionIssueEntity.getPriceTotalBuyMxn()));
		
		if(transactionIssueEntity.getCatalogBrokerEntity().getIdTypeCurrency().equals(CatalogTypeCurrency.USD))
			transactionIssueEntity.setSellGainLossPercentage(new CalculatorUtil().calculatePercentageUpDown(transactionIssueEntity.getPriceTotalBuy(), transactionIssueEntity.getPriceTotalSell()));
		else if(transactionIssueEntity.getCatalogBrokerEntity().getIdTypeCurrency().equals(CatalogTypeCurrency.MXN))
			transactionIssueEntity.setSellGainLossPercentage(new CalculatorUtil().calculatePercentageUpDown(transactionIssueEntity.getPriceTotalBuyMxn(), transactionIssueEntity.getPriceTotalSellMxn()));
		
		return transactionIssueEntity;
	}
	
	@SuppressWarnings("unchecked")
	public List<String> registerIssueTransactionBuys(UserEntity userEntity, CatalogBrokerEntity catalogBrokerEntity, TransactionIssueFilePojo transactionIssueFilePojo) throws BusinessException {
		
		List<String> messages = new ArrayList<>();
		
		CatalogIssuesEntity catalogIssuesEntityVerify =  issuesRepository.findByInitials(transactionIssueFilePojo.getIssue());
	
		if (catalogIssuesEntityVerify == null)
			throw new BusinessException(CatalogsErrorMessage.getErrorMsgFileLoadIssueNotRegistered(transactionIssueFilePojo.getIssue()));
		
		TransactionIssueEntity issuesFundsTransactionsEntityVerify = transactionIssueRepository.findTransactionIssueBuy(userEntity.getId(), catalogIssuesEntityVerify.getId(), catalogBrokerEntity.getId(), dateUtil.getLocalDateTime(transactionIssueFilePojo.getDate()));
		
		if (issuesFundsTransactionsEntityVerify != null) {
			messages.add("Issue transaction found issue: " + transactionIssueFilePojo.getIssue() + MSG_DATE + issuesFundsTransactionsEntityVerify.getBuyDate());
			return messages;
		}
			
		try {
			
			genericCustomPersistance.startTransaction();
			
			DollarHistoricalPriceEntity dollarHistoricalPriceEntityBuy = dollarHistoricalPriceRepository.findByDate(dateUtil.getLocalDate(transactionIssueFilePojo.getDate()));
			CurrencyValuesPojo currencyValuesPriceBuyPojo = currencyDataHelper.getCurrencyValues(transactionIssueFilePojo.getTypeCurrency(), dollarHistoricalPriceEntityBuy.getPrice(), transactionIssueFilePojo.getPrice());
			
			if (!transactionIssueFilePojo.getIsSlice()) {
			
				List<TransactionIssueEntity> transactionIssueEntities = transactionIssueRepository.findTransactionIssueShortSells(userEntity.getId(), catalogIssuesEntityVerify.getId(), transactionIssueFilePojo.getBroker());
	
				for (int i = 0; i < transactionIssueFilePojo.getTitles().intValue(); i++) {
					
					if (i < transactionIssueEntities.size()) {
						
						TransactionIssueEntity transactionIssueEntity = transactionIssueEntities.get(i);
						transactionIssueEntity.setBuyDate(dateUtil.getLocalDateTime(transactionIssueFilePojo.getDate()));
						transactionIssueEntity.setPriceBuy(currencyValuesPriceBuyPojo.getValueUsd());
						transactionIssueEntity.setPriceBuyMxn(currencyValuesPriceBuyPojo.getValueMxn());
						transactionIssueEntity.setCommisionPercentage(transactionIssueFilePojo.getComissionPercentage());
						transactionIssueEntity.setPriceTotalBuy(currencyValuesPriceBuyPojo.getValueUsd().multiply(transactionIssueFilePojo.getComissionPercentage()).divide(BigDecimal.valueOf(100)).add(currencyValuesPriceBuyPojo.getValueUsd()));
						transactionIssueEntity.setPriceTotalBuyMxn(currencyValuesPriceBuyPojo.getValueMxn().multiply(transactionIssueFilePojo.getComissionPercentage()).divide(BigDecimal.valueOf(100)).add(currencyValuesPriceBuyPojo.getValueMxn()));
						transactionIssueEntity.setPriceTotalSell(new CalculatorUtil().calculateTotalPriceSellAfter(currencyValuesPriceBuyPojo.getValueUsd(), transactionIssueEntity.getPriceSell(), transactionIssueFilePojo.getComissionPercentage(), transactionIssueFilePojo.getTaxesPercentage()));
						transactionIssueEntity.setPriceTotalSellMxn(new CalculatorUtil().calculateTotalPriceSellAfter(currencyValuesPriceBuyPojo.getValueMxn(), transactionIssueEntity.getPriceSellMxn(), transactionIssueFilePojo.getComissionPercentage(), transactionIssueFilePojo.getTaxesPercentage()));
						transactionIssueEntity.setSellGainLossTotal(transactionIssueEntity.getPriceTotalSell().subtract(currencyValuesPriceBuyPojo.getValueUsd()));
						transactionIssueEntity.setSellGainLossTotalMxn(transactionIssueEntity.getPriceTotalSellMxn().subtract(currencyValuesPriceBuyPojo.getValueMxn()));
						transactionIssueEntity.setSellGainLossPercentage(new CalculatorUtil().calculatePercentageUpDown(transactionIssueEntity.getPriceTotalBuy(), transactionIssueEntity.getPriceTotalSell()));
						
						genericCustomPersistance.update(transactionIssueEntity);
						continue;
					}
				
					TransactionIssueEntity transactionIssueEntity = buildTransactionIssueEntity(userEntity, catalogIssuesEntityVerify, transactionIssueFilePojo, dollarHistoricalPriceEntityBuy.getPrice());
					genericCustomPersistance.save(transactionIssueEntity);
				}
			}
			else {
				
				TransactionIssueEntity transactionIssueEntity = buildTransactionIssueEntity(userEntity, catalogIssuesEntityVerify, transactionIssueFilePojo, dollarHistoricalPriceEntityBuy.getPrice());
				genericCustomPersistance.save(transactionIssueEntity);
			}
			
			genericCustomPersistance.commitTransaction();
		}
		catch(Exception ex) {
			genericCustomPersistance.rollBackTransaction();
			
			if (ex instanceof BusinessException)
				throw ex;
			else
				throw new BusinessException(CatalogsErrorMessage.getErrorMsgFileBuyTransactionRegister(), ex);
		}
		finally {
			genericCustomPersistance.closeEntityManager();
		}
		return messages;
	}
	
	@SuppressWarnings("unchecked")
	public List<String> registerTransactionIssueSells(UserEntity userEntity, CatalogBrokerEntity catalogBrokerEntity, TransactionIssueFilePojo transactionIssueFilePojo) throws BusinessException {
		
		List<String> messages = new ArrayList<>();
		
		CatalogIssuesEntity catalogIssuesEntityVerify =  issuesRepository.findByInitials(transactionIssueFilePojo.getIssue());
		
		if (catalogIssuesEntityVerify == null)
			throw new BusinessException(CatalogsErrorMessage.getErrorMsgFileLoadIssueNotRegistered(transactionIssueFilePojo.getIssue()));
		
		TransactionIssueEntity issuesFundsTransactionsEntityVerify = transactionIssueRepository.findTransactionIssueSell(userEntity.getId(), catalogIssuesEntityVerify.getId(), catalogBrokerEntity.getId(), dateUtil.getLocalDateTime(transactionIssueFilePojo.getDate()));
		
		if (issuesFundsTransactionsEntityVerify != null && issuesFundsTransactionsEntityVerify.getSellDate() != null) {
			messages.add("Issue transaction sell found issue: " + catalogIssuesEntityVerify.getInitials() + MSG_DATE + issuesFundsTransactionsEntityVerify.getSellDate());
			return messages;
		}
		
		try {
			
			genericCustomPersistance.startTransaction();
			
			List<TransactionIssueEntity> transactionIssueEntities = transactionIssueRepository.findTransactionIssuesNotSoldLower(userEntity.getId(), catalogIssuesEntityVerify.getId(), catalogBrokerEntity.getId(), transactionIssueFilePojo.getIsSlice());
			DollarHistoricalPriceEntity dollarHistoricalPriceEntitySell = dollarHistoricalPriceRepository.findByDate(dateUtil.getLocalDate(transactionIssueFilePojo.getDate()));
			CurrencyValuesPojo currencyValuesPriceBuyPojo = currencyDataHelper.getCurrencyValues(transactionIssueFilePojo.getTypeCurrency(), dollarHistoricalPriceEntitySell.getPrice(), transactionIssueFilePojo.getPrice());
			
			if(transactionIssueFilePojo.getIsSlice()) {
				
				if (transactionIssueEntities.size() > 1)
					throw new BusinessException(CatalogsErrorMessage.getErrorMsgFileSliceMultipleDate(transactionIssueFilePojo.getIssue()));
				if (transactionIssueEntities.size() == 1 && transactionIssueEntities.getFirst().getTotalShares().compareTo(transactionIssueFilePojo.getTitles()) != 0)
					throw new BusinessException(CatalogsErrorMessage.getErrorMsgFileSliceSharesNotMatch(transactionIssueFilePojo.getIssue()));
			
				TransactionIssueEntity transactionIssueEntity = buildTransactionSellIssueEntity(transactionIssueEntities.getFirst(), catalogIssuesEntityVerify, transactionIssueFilePojo, currencyValuesPriceBuyPojo, transactionIssueEntities.getFirst().getTotalShares());
				genericCustomPersistance.update(transactionIssueEntity);
			}
			
			for (int i = 0; i < transactionIssueFilePojo.getTitles().intValue(); i++) {
				
				TransactionIssueEntity transactionIssueEntity = buildTransactionSellIssueEntity(transactionIssueEntities.get(i), catalogIssuesEntityVerify, transactionIssueFilePojo, currencyValuesPriceBuyPojo, new BigDecimal(1));
				genericCustomPersistance.update(transactionIssueEntity);
			}
			genericCustomPersistance.commitTransaction();
		}
		catch(BusinessException ex) {
			throw ex;
		}
		catch(Exception ex) {
			genericCustomPersistance.rollBackTransaction();
			throw new BusinessException(CatalogsErrorMessage.getErrorMsgFileSellTransactionRegister(transactionIssueFilePojo.getIssue()), ex);
		}
		finally {
			genericCustomPersistance.closeEntityManager();
		}
		return messages;
	}
	
	@SuppressWarnings({ "unchecked" })
	public List<String> registerTransactionIssueShortSells(UserEntity userEntity, CatalogBrokerEntity catalogBrokerEntity, TransactionIssueFilePojo transactionIssueFilePojo) throws BusinessException {
		
		List<String> messages = new ArrayList<>();
		
		CatalogIssuesEntity catalogIssuesEntityVerify =  issuesRepository.findByInitials(transactionIssueFilePojo.getIssue());
		
		if (catalogIssuesEntityVerify == null) {
			messages.add(CatalogsErrorMessage.getErrorMsgFileLoadIssueNotRegistered(transactionIssueFilePojo.getIssue()));
		}
		else {
			TransactionIssueEntity transactionIssueEntityVerify = transactionIssueRepository.findTransactionIssueSell(userEntity.getId(), catalogIssuesEntityVerify.getId(), catalogBrokerEntity.getId(), dateUtil.getLocalDateTime(transactionIssueFilePojo.getDate()));
			
			if (transactionIssueEntityVerify != null && transactionIssueEntityVerify.getSellDate() != null) {
				messages.add("Issue transaction short sell found issue: " + transactionIssueEntityVerify.getIdIssue() + MSG_DATE + transactionIssueEntityVerify.getSellDate());
				return messages;
			}
		
			try {
				
				genericCustomPersistance.startTransaction();
				
				DollarHistoricalPriceEntity dollarHistoricalPriceEntityShortSell = dollarHistoricalPriceRepository.findByDate(dateUtil.getLocalDate(transactionIssueFilePojo.getDate()));
				CurrencyValuesPojo currencyValuesPriceBuyPojo = currencyDataHelper.getCurrencyValues(transactionIssueFilePojo.getTypeCurrency(), dollarHistoricalPriceEntityShortSell.getPrice(), transactionIssueFilePojo.getPrice());
				
				for (int i = 0; i < transactionIssueFilePojo.getTitles().intValue(); i++) {
					
					TransactionIssueEntity transactionIssueEntity = new TransactionIssueEntity();
					
					transactionIssueEntity.setIdIssue(catalogIssuesEntityVerify.getId());
					transactionIssueEntity.setIdUser(userEntity.getId());
					transactionIssueEntity.setSellTaxesPercentage(transactionIssueFilePojo.getTaxesPercentage());
					transactionIssueEntity.setSellDate(dateUtil.getLocalDateTime(transactionIssueFilePojo.getDate()));
					transactionIssueEntity.setPriceSell(currencyValuesPriceBuyPojo.getValueUsd());
					transactionIssueEntity.setPriceSellMxn(currencyValuesPriceBuyPojo.getValueMxn());
					transactionIssueEntity.setSellCommisionPercentage(transactionIssueFilePojo.getComissionPercentage());
					transactionIssueEntity.setIdBroker(transactionIssueFilePojo.getBroker());
					transactionIssueEntity.setTotalShares(new BigDecimal(1));
					transactionIssueEntity.setIsSlice(false);
					transactionIssueEntity.setIsShortSell(true);
					
					genericCustomPersistance.save(transactionIssueEntity);
				}
				genericCustomPersistance.commitTransaction();
			}
			catch(Exception ex) {
				genericCustomPersistance.rollBackTransaction();
				throw new BusinessException(CatalogsErrorMessage.getErrorMsgFileSellTransactionRegister(transactionIssueFilePojo.getIssue()), ex);
			}
			finally {
				genericCustomPersistance.closeEntityManager();
			}
		}
		return messages;
	}
	
	@SuppressWarnings("unchecked")
	public void validateFileFormat(Integer idBroker, List<List<String>> csvFileData) throws BusinessException {
		
		if (csvFileData.isEmpty())
			throw new BusinessException(CatalogsErrorMessage.getErrorMsgFileEmpty());
		
		CatalogBrokerEntity catalogBrokerEntity = (CatalogBrokerEntity) genericPersistance.findById(CatalogBrokerEntity.class, idBroker);
		
		List<String> header = csvFileData.get(0);
		
		if (!header.isEmpty() && header.getLast().isEmpty())
			header.remove(header.size() - 1);
		
		if ((idBroker.equals(CatalogsEntity.CatalogBroker.GBM_HOMBROKER) && !customArraysUtil.compareList(header, CatalogsStaticData.CsvReportsHeaders.CSV_HEADER_HOMEBROKER_ISSUES)) ||
				(idBroker.equals(CatalogsEntity.CatalogBroker.CHARLES_SCHWAB) && 
				(!customArraysUtil.compareList(header, CatalogsStaticData.CsvReportsHeaders.CSV_HEADER_CHARLES_SCHWAB) &&
				 !customArraysUtil.compareList(header, CatalogsStaticData.CsvReportsHeaders.CSV_HEADER_CHARLES_SCHWAB_CHECKING_ACCOUNT)
			)))
			throw new BusinessException(CatalogsErrorMessage.getErrorMsgFileBadHeaderFormat(catalogBrokerEntity.getAcronym()));
	}
	
	public ReadCsvTransactionIssues getReadTransactionIssues(Integer idBroker) throws BusinessException {
		
		if (idBroker.equals(CatalogsEntity.CatalogBroker.GBM_HOMBROKER))
			return new ReadCsvTransactionIssuesBrokerGbm();
		else if (idBroker.equals(CatalogsEntity.CatalogBroker.CHARLES_SCHWAB))
			return new ReadCsvTransactionIssuesBrokerSchwab();
		else
			throw new BusinessException(CatalogsErrorMessage.getErrorMsgFileBrokerNotRegistered());
	}
	
	public LoadTransactionIssuesFileDataPojo registerTransactionIssuesFromFile(UserEntity userEntity, CatalogBrokerEntity catalogBrokerEntity, List<TransactionIssueFilePojo> transactionIssueFilePojos) throws BusinessException {
		
		List<String> messages = new ArrayList<>();
			
			for (TransactionIssueFilePojo transactionIssueFilePojo: transactionIssueFilePojos) {
				
				if (transactionIssueFilePojo.getTypeTransaction() == null)
					continue;
					
				if (transactionIssueFilePojo.getTypeTransaction().contentEquals(ReadCsvTransactionIssues.TYPE_TRANSACTION_BUY))
					messages.addAll(registerIssueTransactionBuys(userEntity, catalogBrokerEntity, transactionIssueFilePojo));
				else if (transactionIssueFilePojo.getTypeTransaction().contentEquals(ReadCsvTransactionIssues.TYPE_TRANSACTION_SELL))
					messages.addAll(registerTransactionIssueSells(userEntity, catalogBrokerEntity, transactionIssueFilePojo));
				else if (transactionIssueFilePojo.getTypeTransaction().contentEquals(ReadCsvTransactionIssues.TYPE_TRANSACTION_SHORT_SELL))
					messages.addAll(registerTransactionIssueShortSells(userEntity, catalogBrokerEntity, transactionIssueFilePojo));
			}
		
		LoadTransactionIssuesFileDataPojo dataPojo = new LoadTransactionIssuesFileDataPojo();
		dataPojo.setMessages(messages);
		return dataPojo;
	}
	
	public LoadTransactionIssuesFileDataPojo storeTransactionIssues(UserEntity userEntity, CatalogBrokerEntity catalogBrokerEntity, MultipartFile transactionIssuesFile) throws BusinessException, ParseException, IOException {
		
		List<List<String>> csvData = new ReadCsvFileUtil().readCsvFile(transactionIssuesFile);
		validateFileFormat(catalogBrokerEntity.getId(), csvData);
		ReadCsvTransactionIssues readCsvTransactionIssues = getReadTransactionIssues(catalogBrokerEntity.getId());
		
		List<TransactionIssueFilePojo> transactionIssueFilePojos = readCsvTransactionIssues.readCsvFileIssues(csvData);
		List<TransactionIssueFilePojo> transactionIssueFilePojosSplit = new ArrayList<>();
		
		for(TransactionIssueFilePojo transactionIssueFilePojo: transactionIssueFilePojos) {
			
			if(transactionIssueFilePojo.getIsSlice() && transactionIssueFilePojo.getTitles().compareTo(BigDecimal.ONE) > 0 
					&& transactionIssueFilePojo.getTitles().remainder(BigDecimal.ONE).compareTo(BigDecimal.ZERO) != 0) {

				TransactionIssueFilePojo transactionEven = SerializationUtils.clone(transactionIssueFilePojo);
				TransactionIssueFilePojo transactionOdd =  SerializationUtils.clone(transactionIssueFilePojo);
				
				transactionEven.setTitles(new BigDecimal(transactionIssueFilePojo.getTitles().intValue()));
				transactionEven.setIsSlice(false);
				transactionOdd.setTitles(transactionIssueFilePojo.getTitles().remainder(BigDecimal.ONE));
				
				transactionIssueFilePojosSplit.add(transactionEven);
				transactionIssueFilePojosSplit.add(transactionOdd);
			}
			else {
				transactionIssueFilePojosSplit.add(transactionIssueFilePojo);
			}
		}
		
		return registerTransactionIssuesFromFile(userEntity, catalogBrokerEntity, transactionIssueFilePojosSplit);	
	}
	
	@SuppressWarnings("unchecked")
	public LoadTransactionIssuesFileDataPojo executeRegisterTransactionIssuesFromFile(LoadTransactionIssuesFileRequestPojo requestPojo) throws IOException, BusinessException, ParseException {
		
		UserEntity userEntity = userRepository.findByUserName(requestPojo.getUserName());
		CatalogBrokerEntity catalogBrokerEntity = (CatalogBrokerEntity) genericPersistance.findById(CatalogBrokerEntity.class, requestPojo.getIdBroker());
		
		return storeTransactionIssues(userEntity, catalogBrokerEntity, requestPojo.getFileTransactionIssues());
	}
}
