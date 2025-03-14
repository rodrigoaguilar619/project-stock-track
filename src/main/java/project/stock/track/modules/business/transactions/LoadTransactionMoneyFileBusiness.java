package project.stock.track.modules.business.transactions;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
import project.stock.track.app.beans.entity.DollarHistoricalPriceEntity;
import project.stock.track.app.beans.entity.TransactionMoneyEntity;
import project.stock.track.app.beans.pojos.business.transaction.CurrencyValuesPojo;
import project.stock.track.app.beans.pojos.business.transaction.TransactionMoneyFilePojo;
import project.stock.track.app.beans.pojos.petition.data.LoadTransactionIssuesFileDataPojo;
import project.stock.track.app.beans.pojos.petition.request.LoadTransactionIssuesFileRequestPojo;
import project.stock.track.app.repository.DollarHistoricalPriceRepositoryImpl;
import project.stock.track.app.repository.IssuesRepositoryImpl;
import project.stock.track.app.repository.TransactionMoneyRepositoryImpl;
import project.stock.track.app.utils.CustomArraysUtil;
import project.stock.track.app.utils.ReadCsvFileUtil;
import project.stock.track.app.vo.catalogs.CatalogsErrorMessage;
import project.stock.track.app.vo.catalogs.CatalogsStaticData;
import project.stock.track.app.vo.catalogs.CatalogsStaticData.StaticData;
import project.stock.track.app.vo.entities.CatalogBrokerEnum;
import project.stock.track.app.vo.entities.CatalogTypeMovementEnum;
import project.stock.track.config.helpers.CurrencyDataHelper;
import project.stock.track.modules.business.MainBusiness;
import project.stock.track.modules.business.files.transactions.ReadCsvTransactionIssues;
import project.stock.track.modules.business.files.transactions.ReadCsvTransactionIssuesBrokerGbm;
import project.stock.track.modules.business.files.transactions.ReadCsvTransactionIssuesBrokerSchwab;

@SuppressWarnings("rawtypes")
@Component
@RequiredArgsConstructor
public class LoadTransactionMoneyFileBusiness extends MainBusiness {
	
private final GenericPersistence genericPersistance;
	
	@Qualifier("customPersistance")
	private final GenericPersistence genericCustomPersistance;
	private final UserRepositoryImpl userRepository;
	private final IssuesRepositoryImpl issuesRepository;
	private final TransactionMoneyRepositoryImpl transactionMoneyRepository;
	private final DollarHistoricalPriceRepositoryImpl dollarHistoricalPriceRepository;
	private final CurrencyDataHelper currencyDataHelper;

	private CustomArraysUtil customArraysUtil = new CustomArraysUtil();
	
	private boolean validateRowRegistered(Integer idUser, Integer idIssue, int idBroker, LocalDateTime date, CatalogTypeMovementEnum catalogTypeMovementEnum) {
		
		return transactionMoneyRepository.findTransactionMoney(idUser, idIssue, idBroker, date, catalogTypeMovementEnum.getValue()) != null;
	}
	
	public ReadCsvTransactionIssues getReadTransactionMoney(Integer idBroker) throws BusinessException {
		
		if (idBroker.equals(CatalogBrokerEnum.GBM_HOMBROKER.getValue()))
			return new ReadCsvTransactionIssuesBrokerGbm();
		else if (idBroker.equals(CatalogBrokerEnum.CHARLES_SCHWAB.getValue()))
			return new ReadCsvTransactionIssuesBrokerSchwab();
		else
			throw new BusinessException(CatalogsErrorMessage.getErrorMsgFileBrokerNotRegistered());
	}
	
	@SuppressWarnings("unchecked")
	public List<String> registerMoneyTransactionIssue(UserEntity userEntity, CatalogBrokerEntity catalogBrokerEntity, TransactionMoneyFilePojo transactionMoneyFilePojo, CatalogTypeMovementEnum catalogTypeMovementEnum) throws BusinessException {
		
		List<String> messages = new ArrayList<>();
		
		CatalogIssuesEntity catalogIssuesEntityVerify =  issuesRepository.findByInitials(transactionMoneyFilePojo.getIssue());
	
		if (catalogIssuesEntityVerify == null)
			throw new BusinessException(CatalogsErrorMessage.getErrorMsgFileLoadIssueNotRegistered(transactionMoneyFilePojo.getIssue()));
		
		if(validateRowRegistered(userEntity.getId(), catalogIssuesEntityVerify.getId(), catalogBrokerEntity.getId(), dateUtil.getLocalDateTime(transactionMoneyFilePojo.getDate()), catalogTypeMovementEnum))
			return messages;
			
		try {
			
			genericCustomPersistance.startTransaction();
			
			DollarHistoricalPriceEntity dollarHistoricalPriceEntityBuy = dollarHistoricalPriceRepository.findByDate(dateUtil.getLocalDate(transactionMoneyFilePojo.getDate()));
			
			if (dollarHistoricalPriceEntityBuy == null)
				throw new BusinessException(CatalogsErrorMessage.getErrorMsgDollarHistoricalPriceBuySellNotFound(catalogIssuesEntityVerify.getInitials(), dateFormatUtil.formatLocalDateTime(dateUtil.getLocalDateTime(transactionMoneyFilePojo.getDate()), StaticData.DEFAULT_CURRENCY_FORMAT)));
			
			CurrencyValuesPojo currencyValuesPriceBuyPojo = currencyDataHelper.getCurrencyValues(transactionMoneyFilePojo.getTypeCurrency(), dollarHistoricalPriceEntityBuy.getPrice(), transactionMoneyFilePojo.getAmount());
			
			TransactionMoneyEntity transactionMoneyEntity = new TransactionMoneyEntity();
			transactionMoneyEntity.setIdBroker(catalogBrokerEntity.getId());
			transactionMoneyEntity.setIdUser(userEntity.getId());
			transactionMoneyEntity.setIdIssue(catalogIssuesEntityVerify.getId());
			transactionMoneyEntity.setAmount(currencyValuesPriceBuyPojo.getValueUsd());
			transactionMoneyEntity.setAmountMxn(currencyValuesPriceBuyPojo.getValueMxn());
			transactionMoneyEntity.setDateTransaction(dateUtil.getLocalDateTime(transactionMoneyFilePojo.getDate()));
			transactionMoneyEntity.setIdTypeMovement(catalogTypeMovementEnum.getValue());
			transactionMoneyEntity.setInformation(transactionMoneyFilePojo.getInformation());
			
			genericCustomPersistance.save(transactionMoneyEntity);
			genericCustomPersistance.commitTransaction();
		}
		catch(Exception ex) {
			genericCustomPersistance.rollBackTransaction();
			
			if (ex instanceof BusinessException)
				throw ex;
			else
				throw new BusinessException(CatalogsErrorMessage.getErrorMsgFileTransaction(transactionMoneyFilePojo.getIssue()), ex);
		}
		finally {
			genericCustomPersistance.closeEntityManager();
		}
		
		return messages;
	}
	
	@SuppressWarnings("unchecked")
	public List<String> registerMoneyTransactionBank(UserEntity userEntity, CatalogBrokerEntity catalogBrokerEntity, TransactionMoneyFilePojo transactionMoneyFilePojo, CatalogTypeMovementEnum catalogTypeMovementEnum) throws BusinessException {
		
		List<String> messages = new ArrayList<>();
		
		if(validateRowRegistered(userEntity.getId(), null, catalogBrokerEntity.getId(), dateUtil.getLocalDateTime(transactionMoneyFilePojo.getDate()), catalogTypeMovementEnum))
			return messages;
			
		try {
			
			genericCustomPersistance.startTransaction();
			
			DollarHistoricalPriceEntity dollarHistoricalPriceEntityBuy = dollarHistoricalPriceRepository.findByDate(dateUtil.getLocalDate(transactionMoneyFilePojo.getDate()));
			CurrencyValuesPojo currencyValuesPriceBuyPojo = currencyDataHelper.getCurrencyValues(transactionMoneyFilePojo.getTypeCurrency(), dollarHistoricalPriceEntityBuy.getPrice(), transactionMoneyFilePojo.getAmount());
			
			TransactionMoneyEntity transactionMoneyEntity = new TransactionMoneyEntity();
			transactionMoneyEntity.setIdBroker(catalogBrokerEntity.getId());
			transactionMoneyEntity.setIdUser(userEntity.getId());
			transactionMoneyEntity.setAmount(currencyValuesPriceBuyPojo.getValueUsd());
			transactionMoneyEntity.setAmountMxn(currencyValuesPriceBuyPojo.getValueMxn());
			transactionMoneyEntity.setDateTransaction(dateUtil.getLocalDateTime(transactionMoneyFilePojo.getDate()));
			transactionMoneyEntity.setIdTypeMovement(catalogTypeMovementEnum.getValue());
			transactionMoneyEntity.setInformation(transactionMoneyFilePojo.getInformation());
			
			genericCustomPersistance.save(transactionMoneyEntity);
			genericCustomPersistance.commitTransaction();
		}
		catch(Exception ex) {
			genericCustomPersistance.rollBackTransaction();
			
			if (ex instanceof BusinessException)
				throw ex;
			else
				throw new BusinessException(CatalogsErrorMessage.getErrorMsgFileTransactionBank(), ex);
		}
		finally {
			genericCustomPersistance.closeEntityManager();
		}
		
		return messages;
	}
	
	@SuppressWarnings("unchecked")
	public List<String> registerMoneyDepositWithdraw(UserEntity userEntity, CatalogBrokerEntity catalogBrokerEntity, TransactionMoneyFilePojo transactionMoneyFilePojo, CatalogTypeMovementEnum catalogTypeMovementEnum) throws BusinessException {
		
		List<String> messages = new ArrayList<>();
			
		try {
			
			if(validateRowRegistered(userEntity.getId(), null, catalogBrokerEntity.getId(), dateUtil.getLocalDateTime(transactionMoneyFilePojo.getDate()), catalogTypeMovementEnum))
				return messages;
			
			genericCustomPersistance.startTransaction();
			
			DollarHistoricalPriceEntity dollarHistoricalPriceEntityBuy = dollarHistoricalPriceRepository.findByDate(dateUtil.getLocalDate(transactionMoneyFilePojo.getDate()));
			CurrencyValuesPojo currencyValuesPriceBuyPojo = currencyDataHelper.getCurrencyValues(transactionMoneyFilePojo.getTypeCurrency(), dollarHistoricalPriceEntityBuy.getPrice(), transactionMoneyFilePojo.getAmount());
			
			TransactionMoneyEntity transactionMoneyEntity = new TransactionMoneyEntity();
			transactionMoneyEntity.setIdBroker(catalogBrokerEntity.getId());
			transactionMoneyEntity.setIdUser(userEntity.getId());
			transactionMoneyEntity.setAmount(currencyValuesPriceBuyPojo.getValueUsd());
			transactionMoneyEntity.setAmountMxn(currencyValuesPriceBuyPojo.getValueMxn());
			transactionMoneyEntity.setDateTransaction(dateUtil.getLocalDateTime(transactionMoneyFilePojo.getDate()));
			transactionMoneyEntity.setIdTypeMovement(catalogTypeMovementEnum.getValue());
			transactionMoneyEntity.setInformation(transactionMoneyFilePojo.getInformation());
			
			genericCustomPersistance.save(transactionMoneyEntity);
			genericCustomPersistance.commitTransaction();
		}
		catch(Exception ex) {
			genericCustomPersistance.rollBackTransaction();
			
			if (ex instanceof BusinessException)
				throw ex;
			else
				throw new BusinessException(CatalogsErrorMessage.getErrorMsgFileTransactionDepositWithdraw(), ex);
		}
		finally {
			genericCustomPersistance.closeEntityManager();
		}
		
		return messages;
	}
	
	public LoadTransactionIssuesFileDataPojo registerTransactionMoneyFromFile(UserEntity userEntity, CatalogBrokerEntity catalogBrokerEntity, List<TransactionMoneyFilePojo> transactionMoneyFilePojos) throws BusinessException {
		
		List<String> messages = new ArrayList<>();
			
			for (TransactionMoneyFilePojo transactionMoneyFilePojo: transactionMoneyFilePojos) {
				
				if (transactionMoneyFilePojo.getTypeTransaction() == null)
					continue;
					
				if (transactionMoneyFilePojo.getTypeTransaction().contentEquals(ReadCsvTransactionIssues.TYPE_TRANSACTION_TAX))
					messages.addAll(registerMoneyTransactionIssue(userEntity, catalogBrokerEntity, transactionMoneyFilePojo, CatalogTypeMovementEnum.TAX));
				else if (transactionMoneyFilePojo.getTypeTransaction().contentEquals(ReadCsvTransactionIssues.TYPE_TRANSACTION_BANK_INTEREST))
					messages.addAll(registerMoneyTransactionBank(userEntity, catalogBrokerEntity, transactionMoneyFilePojo, CatalogTypeMovementEnum.BANK_INTEREST));
				else if (transactionMoneyFilePojo.getTypeTransaction().contentEquals(ReadCsvTransactionIssues.TYPE_TRANSACTION_BANK_FEE))
					messages.addAll(registerMoneyTransactionBank(userEntity, catalogBrokerEntity, transactionMoneyFilePojo, CatalogTypeMovementEnum.BANK_FEE));
				else if (transactionMoneyFilePojo.getTypeTransaction().contentEquals(ReadCsvTransactionIssues.TYPE_TRANSACTION_DEPOSIT))
					messages.addAll(registerMoneyDepositWithdraw(userEntity, catalogBrokerEntity, transactionMoneyFilePojo, CatalogTypeMovementEnum.DEPOSIT));
				else if (transactionMoneyFilePojo.getTypeTransaction().contentEquals(ReadCsvTransactionIssues.TYPE_TRANSACTION_WITHDRAW))
					messages.addAll(registerMoneyDepositWithdraw(userEntity, catalogBrokerEntity, transactionMoneyFilePojo, CatalogTypeMovementEnum.WITHDRAW));
				else if (transactionMoneyFilePojo.getTypeTransaction().contentEquals(ReadCsvTransactionIssues.TYPE_TRANSACTION_DIVIDEND))
					messages.addAll(registerMoneyTransactionIssue(userEntity, catalogBrokerEntity, transactionMoneyFilePojo, CatalogTypeMovementEnum.DIVIDEND));
				else if (transactionMoneyFilePojo.getTypeTransaction().contentEquals(ReadCsvTransactionIssues.TYPE_TRANSACTION_CASH_IN_LIEU))
					messages.addAll(registerMoneyTransactionIssue(userEntity, catalogBrokerEntity, transactionMoneyFilePojo, CatalogTypeMovementEnum.CASH_IN_LIEU));
				else if (transactionMoneyFilePojo.getTypeTransaction().contentEquals(ReadCsvTransactionIssues.TYPE_TRANSACTION_ISSUE_FEE))
					messages.addAll(registerMoneyTransactionIssue(userEntity, catalogBrokerEntity, transactionMoneyFilePojo, CatalogTypeMovementEnum.ISSUE_FEE));
			}
		
		LoadTransactionIssuesFileDataPojo dataPojo = new LoadTransactionIssuesFileDataPojo();
		dataPojo.setMessages(messages);
		return dataPojo;
	}
	
	@SuppressWarnings("unchecked")
	public void validateFileFormatMoney(Integer idBroker, List<List<String>> csvFileData) throws BusinessException {
		
		if (csvFileData.isEmpty())
			throw new BusinessException(CatalogsErrorMessage.getErrorMsgFileEmpty());
		
		CatalogBrokerEntity catalogBrokerEntity = (CatalogBrokerEntity) genericPersistance.findById(CatalogBrokerEntity.class, idBroker);
		
		List<String> header = csvFileData.get(0);
		
		if (!header.isEmpty() && header.getLast().isEmpty())
			header.remove(header.size() - 1);
		
		if ((idBroker.equals(CatalogBrokerEnum.GBM_HOMBROKER.getValue()) && !customArraysUtil.compareList(header, CatalogsStaticData.CsvReportsHeaders.CSV_HEADER_HOMEBROKER_MONEY)) ||
				(idBroker.equals(CatalogBrokerEnum.CHARLES_SCHWAB.getValue()) && 
				(!customArraysUtil.compareList(header, CatalogsStaticData.CsvReportsHeaders.CSV_HEADER_CHARLES_SCHWAB) &&
				 !customArraysUtil.compareList(header, CatalogsStaticData.CsvReportsHeaders.CSV_HEADER_CHARLES_SCHWAB_CHECKING_ACCOUNT)
			)))
			throw new BusinessException(CatalogsErrorMessage.getErrorMsgFileBadHeaderFormat(catalogBrokerEntity.getAcronym()));
	}
	
	public LoadTransactionIssuesFileDataPojo storeTransactionMoney(UserEntity userEntity, CatalogBrokerEntity catalogBrokerEntity, MultipartFile transactionIssuesFile) throws BusinessException, ParseException, IOException {
		
		List<List<String>> csvData = new ReadCsvFileUtil().readCsvFile(transactionIssuesFile);
		validateFileFormatMoney(catalogBrokerEntity.getId(), csvData);
		
		ReadCsvTransactionIssues readCsvTransactionMoney = getReadTransactionMoney(catalogBrokerEntity.getId());
		
		List<TransactionMoneyFilePojo> transactionMoneyFilePojos = readCsvTransactionMoney.readCsvFileMoney(csvData);
		return registerTransactionMoneyFromFile(userEntity, catalogBrokerEntity, transactionMoneyFilePojos);	
	}
	
	@SuppressWarnings("unchecked")
	public LoadTransactionIssuesFileDataPojo executeRegisterTransactionMoneyFromFile(LoadTransactionIssuesFileRequestPojo requestPojo) throws IOException, BusinessException, ParseException {
		
		UserEntity userEntity = userRepository.findByUserName(requestPojo.getUserName());
		CatalogBrokerEntity catalogBrokerEntity = (CatalogBrokerEntity) genericPersistance.findById(CatalogBrokerEntity.class, requestPojo.getIdBroker());
		
		return storeTransactionMoney(userEntity, catalogBrokerEntity, requestPojo.getFileTransactionIssues());
	}
}
