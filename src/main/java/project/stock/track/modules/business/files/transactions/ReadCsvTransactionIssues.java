package project.stock.track.modules.business.files.transactions;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import lib.base.backend.utils.NumberDataUtil;
import lib.base.backend.utils.date.DateFormatUtil;
import project.stock.track.app.beans.pojos.business.transaction.TransactionIssueFilePojo;
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
	
	public abstract List<TransactionIssueFilePojo> readCsvFile(List<List<String>> records) throws ParseException, IOException;
	
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
	
}
