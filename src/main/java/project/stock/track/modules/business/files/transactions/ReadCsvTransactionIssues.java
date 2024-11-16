package project.stock.track.modules.business.files.transactions;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;

import lib.base.backend.exception.data.BusinessException;
import lib.base.backend.utils.NumberDataUtil;
import lib.base.backend.utils.date.DateFormatUtil;
import project.stock.track.app.beans.pojos.business.transaction.TransactionIssueFilePojo;
import project.stock.track.app.beans.pojos.business.transaction.TransactionMoneyFilePojo;
import project.stock.track.app.utils.CustomArraysUtil;
import project.stock.track.app.utils.ReadCsvFileUtil;

public abstract class ReadCsvTransactionIssues {

	ReadCsvFileUtil readCsvFileUtil = new ReadCsvFileUtil();
	DateFormatUtil dateFormatUtil = new DateFormatUtil();
	NumberDataUtil numberDataUtil = new NumberDataUtil();
	CustomArraysUtil customArraysUtil = new CustomArraysUtil();
	
	public static final String TYPE_TRANSACTION_BUY = "BUY";
	public static final String TYPE_TRANSACTION_SELL = "SELL";
	public static final String TYPE_TRANSACTION_SHORT_SELL = "SHORT_SELL";
	public static final String TYPE_TRANSACTION_TAX = "TAX";
	public static final String TYPE_TRANSACTION_DIVIDEND = "DIVIDEND";
	public static final String TYPE_TRANSACTION_DEPOSIT = "DEPOSIT";
	public static final String TYPE_TRANSACTION_WITHDRAW = "WITHDRAW";
	public static final String TYPE_TRANSACTION_CASH_IN_LIEU = "CASH_IN_LIEU";
	public static final String TYPE_TRANSACTION_ISSUE_FEE = "ISSUE_FEE";
	public static final String TYPE_TRANSACTION_BANK_INTEREST = "BANK_INTEREST";
	public static final String TYPE_TRANSACTION_BANK_FEE = "BANK_FEE";
	
	public abstract List<TransactionIssueFilePojo> readCsvFileIssues(List<List<String>> records) throws ParseException, IOException, BusinessException;
	
	public abstract List<TransactionMoneyFilePojo> readCsvFileMoney(List<List<String>> records) throws ParseException, IOException, BusinessException;
	
	protected String buildIssueCommon(String issue) {
		
		switch(issue) {
			case "FB":
				return "META";
			case "WLL1":
				return "WLL";
			default:
				return issue;
		}
	}
	
	protected BigDecimal buildPrice(String price) {
		if (price == null || price.isEmpty())
			return new BigDecimal(0);
		
		return new BigDecimal(price.replace("$", "").replace(",", ""));
	}
	
}
