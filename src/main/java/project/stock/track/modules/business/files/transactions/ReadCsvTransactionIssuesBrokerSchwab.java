package project.stock.track.modules.business.files.transactions;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.joda.time.DateTime;

import lib.base.backend.exception.data.BusinessException;
import project.stock.track.app.beans.pojos.business.transaction.TransactionIssueFilePojo;
import project.stock.track.app.beans.pojos.business.transaction.TransactionMoneyFilePojo;
import project.stock.track.app.vo.catalogs.CatalogsStaticData;
import project.stock.track.app.vo.entities.CatalogBrokerEnum;
import project.stock.track.app.vo.entities.CatalogTypeCurrencyEnum;

public class ReadCsvTransactionIssuesBrokerSchwab extends ReadCsvTransactionIssues {
	
	private static final String DATE_FORMAT = "MM/dd/yyyy HH:mm:ss";
	
	private List<String> TYPE_TRANSACTION_COMMON_IGNORE = Arrays.asList("Wire Funds Received", "Journal", "Special Qual Div", "Qualified Div Adj",
			"Journaled Shares", "MoneyLink Deposit", "Reverse Split", "Spin-off", "Stock Merger", "Stock Split", "Reorganized Issue", "Reorg Adj",
			"Other");
	private List<String> TYPE_TRANSACTION_ISSUE_IGNORE = new ArrayList<>(Arrays.asList("Qualified Dividend", "Cash Dividend", "Non-Qualified Div",
			"ADR Mgmt Fee", "Bank Interest", "Funds Received", "Service Fee", "Margin Interest" + "Cash In Lieu", "Cash/Stock Merger",
			"Foreign Tax Paid"));
	private List<String> TYPE_TRANSACTION_MONEY_IGNORE = new ArrayList<>(Arrays.asList("Buy", "Sell", "Sell Short"));
	
	public ReadCsvTransactionIssuesBrokerSchwab() {
		super();
		
		this.TYPE_TRANSACTION_ISSUE_IGNORE.addAll(TYPE_TRANSACTION_COMMON_IGNORE);
		this.TYPE_TRANSACTION_MONEY_IGNORE.addAll(TYPE_TRANSACTION_COMMON_IGNORE);
	}
	
	private Long buildDate(String date, int addSeconds) throws ParseException {
		
		String newDate = date + " " + "00:00:00";
		Long dateMillis = dateUtil.getMillis(dateFormatUtil.formatLocalDateTime(newDate, DATE_FORMAT));
		
		return new DateTime(dateMillis).plusSeconds(addSeconds).toDate().getTime();
	}
	
	private String buildTypeTransactionIssues(String transactionDescription) throws BusinessException {
		
		if (TYPE_TRANSACTION_ISSUE_IGNORE.contains(transactionDescription))
			return null;
		
		if (transactionDescription.contains("Buy"))
			return TYPE_TRANSACTION_BUY;
		else if (transactionDescription.contains("Sell Short"))
			return TYPE_TRANSACTION_SHORT_SELL;
		else if (transactionDescription.contains("Sell"))
			return TYPE_TRANSACTION_SELL;
		else
			throw new BusinessException("Transaction type not mapped: " + transactionDescription);
	}
	
	private String buildTypeTransactionMoney(String transactionDescription) throws BusinessException {
		
		if (TYPE_TRANSACTION_MONEY_IGNORE.contains(transactionDescription))
			return null;
		
		if (transactionDescription.contains("DEPOSIT"))
			return TYPE_TRANSACTION_DEPOSIT;
		else if (transactionDescription.contains("VISA"))
			return TYPE_TRANSACTION_DEPOSIT;
		else if (transactionDescription.contains("CREDIT"))
			return TYPE_TRANSACTION_DEPOSIT;
		else if (transactionDescription.contains("ACH"))
			return TYPE_TRANSACTION_DEPOSIT;
		else if (transactionDescription.contains("ATMREBATE"))
			return TYPE_TRANSACTION_DEPOSIT;
		else if (transactionDescription.contains("ATM"))
			return TYPE_TRANSACTION_WITHDRAW;
		else if (transactionDescription.contains("Qualified Dividend"))
			return TYPE_TRANSACTION_DIVIDEND;
		else if (transactionDescription.contains("Cash Dividend"))
			return TYPE_TRANSACTION_DIVIDEND;
		else if (transactionDescription.contains("Non-Qualified Div"))
			return TYPE_TRANSACTION_DIVIDEND;
		else if (transactionDescription.contains("ADR Mgmt Fee"))
			return TYPE_TRANSACTION_ISSUE_FEE;
		else if (transactionDescription.contains("Bank Interest"))
			return TYPE_TRANSACTION_BANK_INTEREST;
		else if (transactionDescription.contains("Funds Received"))
			return TYPE_TRANSACTION_BANK_INTEREST;
		else if (transactionDescription.contains("INTADJUST"))
			return TYPE_TRANSACTION_BANK_INTEREST;
		else if (transactionDescription.contains("Service Fee"))
			return TYPE_TRANSACTION_BANK_FEE;
		else if (transactionDescription.contains("Margin Interest"))
			return TYPE_TRANSACTION_BANK_FEE;
		else if (transactionDescription.contains("Cash In Lieu"))
			return TYPE_TRANSACTION_CASH_IN_LIEU;
		else if (transactionDescription.contains("Cash/Stock Merger"))
			return TYPE_TRANSACTION_CASH_IN_LIEU;
		else if (transactionDescription.contains("Foreign Tax Paid"))
			return TYPE_TRANSACTION_TAX;
		else
			throw new BusinessException("Transaction type not mapped: " + transactionDescription);
	}
	
	private boolean determineSkipRow(List<String> rowRecord) {
		
		return !(rowRecord.get(1).equals("Buy") || rowRecord.get(1).equals("Sell") || rowRecord.get(1).equals("Sell Short"));
	}
	
	private TransactionMoneyFilePojo buildTransactionMoneyBrokerage(List<String> rowRecord, int timeSeconds) throws BusinessException, ParseException {
		
		TransactionMoneyFilePojo transactionMoneyFilePojo = new TransactionMoneyFilePojo();
		transactionMoneyFilePojo.setDate(buildDate(rowRecord.get(0).split(" ")[0], ++timeSeconds));
		transactionMoneyFilePojo.setTypeTransaction(buildTypeTransactionMoney(rowRecord.get(1)));
		transactionMoneyFilePojo.setIssue(buildIssueCommon(rowRecord.get(2)));
		transactionMoneyFilePojo.setAmount(buildPrice(rowRecord.get(7)).abs());
		transactionMoneyFilePojo.setTypeCurrency(CatalogTypeCurrencyEnum.USD.getValue());
		transactionMoneyFilePojo.setInformation(rowRecord.get(1));
		
		return transactionMoneyFilePojo;
	}
	
	private TransactionMoneyFilePojo buildTransactionMoneyChecking(List<String> rowRecord, int timeSeconds) throws BusinessException, ParseException {
		
		String amount = (rowRecord.get(5) != null && !rowRecord.get(5).isEmpty()) ? rowRecord.get(5) : rowRecord.get(6);
		TransactionMoneyFilePojo transactionMoneyFilePojo = new TransactionMoneyFilePojo();
		transactionMoneyFilePojo.setDate(buildDate(rowRecord.get(0).split(" ")[0], ++timeSeconds));
		transactionMoneyFilePojo.setTypeTransaction(buildTypeTransactionMoney(rowRecord.get(2)));
		transactionMoneyFilePojo.setAmount(buildPrice(amount).abs());
		transactionMoneyFilePojo.setTypeCurrency(CatalogTypeCurrencyEnum.USD.getValue());
		transactionMoneyFilePojo.setInformation(rowRecord.get(4));
		
		return transactionMoneyFilePojo;
	}

	public List<TransactionIssueFilePojo> readCsvFileIssues(List<List<String>> records) throws ParseException, IOException, BusinessException {
		
		List<TransactionIssueFilePojo> issueTransactionDataPojos = new ArrayList<>();
		int timeSeconds = 0;
		boolean isRowHeader = false;
		
		for (List<String> rowRecord: records) {
			
			if (!isRowHeader && 
					(customArraysUtil.compareList(rowRecord, CatalogsStaticData.CsvReportsHeaders.CSV_HEADER_CHARLES_SCHWAB)
					|| customArraysUtil.compareList(rowRecord, CatalogsStaticData.CsvReportsHeaders.CSV_HEADER_CHARLES_SCHWAB_CHECKING_ACCOUNT)) )
				isRowHeader = true;
				
			if (!isRowHeader || determineSkipRow(rowRecord))
				continue;
			
			TransactionIssueFilePojo transactionIssueFileDataPojo = new TransactionIssueFilePojo();
			transactionIssueFileDataPojo.setTaxesPercentage(CatalogsStaticData.StaticData.DEFAULT_TAXES_PERCENTAGE_CHARLES_SCHWAB);
			transactionIssueFileDataPojo.setIssue(buildIssueCommon(rowRecord.get(2)));
			transactionIssueFileDataPojo.setDate(buildDate(rowRecord.get(0).split(" ")[0], ++timeSeconds));
			transactionIssueFileDataPojo.setTypeTransaction(buildTypeTransactionIssues(rowRecord.get(1)));
			transactionIssueFileDataPojo.setTitles(new BigDecimal(rowRecord.get(4)));
			transactionIssueFileDataPojo.setPrice(buildPrice(rowRecord.get(5)));
			transactionIssueFileDataPojo.setTypeCurrency(CatalogTypeCurrencyEnum.USD.getValue());
			transactionIssueFileDataPojo.setComissionPercentage(BigDecimal.valueOf(0));
			transactionIssueFileDataPojo.setBroker(CatalogBrokerEnum.CHARLES_SCHWAB.getValue());
			transactionIssueFileDataPojo.setIsSlice(numberDataUtil.hasFractionalPart(transactionIssueFileDataPojo.getTitles()));
			
			issueTransactionDataPojos.add(transactionIssueFileDataPojo);
		}
		
		return issueTransactionDataPojos;
	}

	@Override
	public List<TransactionMoneyFilePojo> readCsvFileMoney(List<List<String>> records) throws ParseException, IOException, BusinessException {

		List<TransactionMoneyFilePojo> issueTransactionDataPojos = new ArrayList<>();
		int rowCount = 0;
		int timeSeconds = 0;
		
		for (List<String> rowRecord: records) {
			
			rowCount ++;
			if (rowCount == 1)
				continue;
			
			if(customArraysUtil.compareList(CatalogsStaticData.CsvReportsHeaders.CSV_HEADER_CHARLES_SCHWAB, records.get(0)))
				issueTransactionDataPojos.add(buildTransactionMoneyBrokerage(rowRecord, timeSeconds));
			else if(customArraysUtil.compareList(CatalogsStaticData.CsvReportsHeaders.CSV_HEADER_CHARLES_SCHWAB_CHECKING_ACCOUNT, records.get(0)))
				issueTransactionDataPojos.add(buildTransactionMoneyChecking(rowRecord, timeSeconds));
			else 
				continue;
		}
		
		return issueTransactionDataPojos;
	}
	
	
}
