package project.stock.track.modules.business.transactions;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import project.stock.track.app.beans.entity.TransactionIssueEntity;
import project.stock.track.app.beans.pojos.business.transaction.TransactionIssueFilePojo;
import project.stock.track.app.beans.pojos.petition.data.LoadTransactionIssuesFileDataPojo;
import project.stock.track.app.beans.pojos.petition.request.LoadTransactionIssuesFileRequestPojo;
import project.stock.track.app.repository.IssuesRepositoryImpl;
import project.stock.track.app.repository.TransactionIssueRepositoryImpl;
import project.stock.track.app.utils.CalculatorUtil;
import project.stock.track.app.utils.CustomArraysUtil;
import project.stock.track.app.utils.ReadCsvFileUtil;
import project.stock.track.app.vo.catalogs.CatalogsEntity;
import project.stock.track.app.vo.catalogs.CatalogsErrorMessage;
import project.stock.track.app.vo.catalogs.CatalogsStaticData;
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
	
	private CustomArraysUtil customArraysUtil = new CustomArraysUtil();
	
	private static final String MSG_ISSUE_NOT_REGISTERED = "Issue not registered issue: ";
	private static final String MSG_DATE = " date: ";
	
	private TransactionIssueEntity buildTransactionIssueEntity(UserEntity userEntity, CatalogIssuesEntity catalogIssuesEntityVerify, TransactionIssueFilePojo transactionIssueFilePojo) {
		
		BigDecimal priceTotalBuy = transactionIssueFilePojo.getPrice().multiply(BigDecimal.valueOf(transactionIssueFilePojo.getTitles()));
		
		TransactionIssueEntity transactionIssueEntity = new TransactionIssueEntity();
		transactionIssueEntity.setIdIssue(catalogIssuesEntityVerify.getId());
		transactionIssueEntity.setIdUser(userEntity.getId());
		transactionIssueEntity.setIdDate(new Date(transactionIssueFilePojo.getDate()));
		transactionIssueEntity.setPriceBuy(transactionIssueFilePojo.getPrice());
		transactionIssueEntity.setCommisionPercentage(transactionIssueFilePojo.getComissionPercentage());
		transactionIssueEntity.setPriceTotalBuy(priceTotalBuy.multiply(transactionIssueFilePojo.getComissionPercentage()).divide(BigDecimal.valueOf(100)).add(priceTotalBuy));
		transactionIssueEntity.setIdBroker(transactionIssueFilePojo.getBroker());
		transactionIssueEntity.setIsSlice(transactionIssueFilePojo.getIsSlice());
		transactionIssueEntity.setTotalShares(transactionIssueFilePojo.getIsSlice() ? BigDecimal.valueOf(transactionIssueFilePojo.getTitles()) : new BigDecimal(1));
		
		return transactionIssueEntity;
	}
	
	@SuppressWarnings("unchecked")
	public List<String> registerIssueTransactionBuys(UserEntity userEntity, CatalogBrokerEntity catalogBrokerEntity, TransactionIssueFilePojo transactionIssueFilePojo) throws BusinessException {
		
		List<String> messages = new ArrayList<>();
		
		try {
			
			genericCustomPersistance.startTransaction();
			
			CatalogIssuesEntity catalogIssuesEntityVerify =  issuesRepository.findByInitials(transactionIssueFilePojo.getIssue());
		
			if (catalogIssuesEntityVerify == null)
				throw new BusinessException(CatalogsErrorMessage.getErrorMsgFileLoadIssueNotRegistered(transactionIssueFilePojo.getIssue()));
			
			TransactionIssueEntity issuesFundsTransactionsEntityVerify = transactionIssueRepository.findTransactionIssueBuy(userEntity.getId(), catalogIssuesEntityVerify.getId(), catalogBrokerEntity.getId(), new Date(transactionIssueFilePojo.getDate()));
			
			if (issuesFundsTransactionsEntityVerify != null) {
				messages.add("Issue transaction found issue: " + transactionIssueFilePojo.getIssue() + MSG_DATE + issuesFundsTransactionsEntityVerify.getIdDate());
				return messages;
			}
			
			if (!transactionIssueFilePojo.getIsSlice()) {
			
				List<TransactionIssueEntity> transactionIssueEntities = transactionIssueRepository.findTransactionIssueShortSells(userEntity.getId(), catalogIssuesEntityVerify.getId(), transactionIssueFilePojo.getBroker());
	
				for (int i = 0; i < transactionIssueFilePojo.getTitles(); i++) {
					
					if (i < transactionIssueEntities.size()) {
						
						TransactionIssueEntity transactionIssueEntity = transactionIssueEntities.get(i);
						transactionIssueEntity.setIdDate(new Date(transactionIssueFilePojo.getDate()));
						transactionIssueEntity.setPriceBuy(transactionIssueFilePojo.getPrice());
						transactionIssueEntity.setCommisionPercentage(transactionIssueFilePojo.getComissionPercentage());
						transactionIssueEntity.setPriceTotalBuy(transactionIssueFilePojo.getPrice().multiply(transactionIssueFilePojo.getComissionPercentage()).divide(BigDecimal.valueOf(100)).add(transactionIssueFilePojo.getPrice()));
						transactionIssueEntity.setPriceTotalSell(new CalculatorUtil().calculateTotalPriceSellAfter(transactionIssueEntity.getPriceTotalBuy(), transactionIssueEntity.getPriceSell(), transactionIssueFilePojo.getComissionPercentage(), transactionIssueFilePojo.getTaxesPercentage()));
						transactionIssueEntity.setSellGainLossTotal(transactionIssueEntity.getPriceTotalSell().subtract(transactionIssueEntity.getPriceTotalBuy()));
						transactionIssueEntity.setSellGainLossPercentage(new CalculatorUtil().calculatePercentageUpDown(transactionIssueEntity.getPriceTotalBuy(), transactionIssueEntity.getPriceTotalSell()));
						
						genericCustomPersistance.update(transactionIssueEntity);
						continue;
					}
				
					TransactionIssueEntity transactionIssueEntity = buildTransactionIssueEntity(userEntity, catalogIssuesEntityVerify, transactionIssueFilePojo);
					genericCustomPersistance.save(transactionIssueEntity);
				}
			}
			else {
				
				TransactionIssueEntity transactionIssueEntity = buildTransactionIssueEntity(userEntity, catalogIssuesEntityVerify, transactionIssueFilePojo);
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
		
		TransactionIssueEntity issuesFundsTransactionsEntityVerify = transactionIssueRepository.findTransactionIssueSell(userEntity.getId(), catalogIssuesEntityVerify.getId(), catalogBrokerEntity.getId(), new Date(transactionIssueFilePojo.getDate()));
		
		if (issuesFundsTransactionsEntityVerify != null && issuesFundsTransactionsEntityVerify.getSellDate() != null) {
			messages.add("Issue transaction sell found issue: " + catalogIssuesEntityVerify.getInitials() + MSG_DATE + issuesFundsTransactionsEntityVerify.getSellDate());
			return messages;
		}
		
		try {
			
			genericCustomPersistance.startTransaction();
			
			List<TransactionIssueEntity> transactionIssueEntities = transactionIssueRepository.findTransactionIssuesNotSoldLower(userEntity.getId(), catalogIssuesEntityVerify.getId(), catalogBrokerEntity.getId());
			
			for (int i = 0; i < transactionIssueFilePojo.getTitles(); i++) {
				
				TransactionIssueEntity transactionIssueEntity = transactionIssueEntities.get(i);
				
				transactionIssueEntity.setIdIssue(catalogIssuesEntityVerify.getId());
				transactionIssueEntity.setSellTaxesPercentage(transactionIssueFilePojo.getTaxesPercentage());
				transactionIssueEntity.setSellDate(new Date(transactionIssueFilePojo.getDate()));
				transactionIssueEntity.setPriceSell(transactionIssueFilePojo.getPrice());
				transactionIssueEntity.setSellCommisionPercentage(transactionIssueFilePojo.getComissionPercentage());
				transactionIssueEntity.setPriceTotalSell(new CalculatorUtil().calculateTotalPriceSellAfter(transactionIssueEntity.getPriceTotalBuy(), transactionIssueEntity.getPriceSell(), transactionIssueFilePojo.getComissionPercentage(), transactionIssueFilePojo.getTaxesPercentage()));
				transactionIssueEntity.setSellGainLossTotal(transactionIssueEntity.getPriceTotalSell().subtract(transactionIssueEntity.getPriceTotalBuy()));
				transactionIssueEntity.setSellGainLossPercentage(new CalculatorUtil().calculatePercentageUpDown(transactionIssueEntity.getPriceTotalBuy(), transactionIssueEntity.getPriceTotalSell()));
				
				genericCustomPersistance.update(transactionIssueEntity);
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
		return messages;
	}
	
	@SuppressWarnings({ "unused", "unchecked" })
	public List<String> registerTransactionIssueShortSells(UserEntity userEntity, CatalogBrokerEntity catalogBrokerEntity, TransactionIssueFilePojo transactionIssueFilePojo) throws BusinessException {
		
		List<String> messages = new ArrayList<>();
		
		CatalogIssuesEntity catalogIssuesEntityVerify =  issuesRepository.findByInitials(transactionIssueFilePojo.getIssue());
		
		if (catalogIssuesEntityVerify == null) {
			messages.add(MSG_ISSUE_NOT_REGISTERED + transactionIssueFilePojo.getIssue());
		}
		else {
			TransactionIssueEntity transactionIssueEntityVerify = transactionIssueRepository.findTransactionIssueSell(userEntity.getId(), catalogIssuesEntityVerify.getId(), catalogBrokerEntity.getId(), new Date(transactionIssueFilePojo.getDate()));
			
			if (transactionIssueEntityVerify != null && transactionIssueEntityVerify.getSellDate() != null) {
				messages.add("Issue transaction short sell found issue: " + transactionIssueEntityVerify.getIdIssue() + MSG_DATE + transactionIssueEntityVerify.getSellDate());
				return messages;
			}
		
			try {
				
				genericCustomPersistance.startTransaction();
				
				List<TransactionIssueEntity> transactionIssueEntities = transactionIssueRepository.findTransactionIssueShortSells(userEntity.getId(), catalogIssuesEntityVerify.getId(), transactionIssueFilePojo.getBroker());
				
				for (int i = 0; i < transactionIssueFilePojo.getTitles(); i++) {
					
					TransactionIssueEntity transactionIssueEntity = new TransactionIssueEntity();
					
					transactionIssueEntity.setIdIssue(catalogIssuesEntityVerify.getId());
					transactionIssueEntity.setSellTaxesPercentage(transactionIssueFilePojo.getTaxesPercentage());
					transactionIssueEntity.setSellDate(new Date(transactionIssueFilePojo.getDate()));
					transactionIssueEntity.setPriceSell(transactionIssueFilePojo.getPrice());
					transactionIssueEntity.setSellCommisionPercentage(transactionIssueFilePojo.getComissionPercentage());
					transactionIssueEntity.setIdBroker(transactionIssueFilePojo.getBroker());
					
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
		
		if ((idBroker.equals(CatalogsEntity.CatalogBroker.GBM_HOMBROKER) && !customArraysUtil.compareList(csvFileData.get(0), CatalogsStaticData.CsvReportsHeaders.CSV_HEADER_HOMEBROKER)) ||
				(idBroker.equals(CatalogsEntity.CatalogBroker.CHARLES_SCHWAB) && 
				(!customArraysUtil.compareList(csvFileData.get(0), CatalogsStaticData.CsvReportsHeaders.CSV_HEADER_CHARLES_SCHWAB) &&
				 !customArraysUtil.compareList(csvFileData.get(0), CatalogsStaticData.CsvReportsHeaders.CSV_HEADER_CHARLES_SCHWAB_2)
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
		
		List<TransactionIssueFilePojo> transactionIssueFilePojos = readCsvTransactionIssues.readCsvFile(csvData);
		return registerTransactionIssuesFromFile(userEntity, catalogBrokerEntity, transactionIssueFilePojos);	
	}
	
	@SuppressWarnings("unchecked")
	public LoadTransactionIssuesFileDataPojo executeRegisterTransactionIssuesFromFile(LoadTransactionIssuesFileRequestPojo requestPojo) throws IOException, BusinessException, ParseException {
		
		UserEntity userEntity = userRepository.findByUserName(requestPojo.getUserName());
		CatalogBrokerEntity catalogBrokerEntity = (CatalogBrokerEntity) genericPersistance.findById(CatalogBrokerEntity.class, requestPojo.getIdBroker());
		
		return storeTransactionIssues(userEntity, catalogBrokerEntity, requestPojo.getFileTransactionIssues());
	}
}
