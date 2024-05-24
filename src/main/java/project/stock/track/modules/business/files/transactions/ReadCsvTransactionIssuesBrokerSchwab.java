package project.stock.track.modules.business.files.transactions;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import project.stock.track.app.beans.pojos.business.transaction.TransactionIssueFilePojo;
import project.stock.track.app.vo.catalogs.CatalogsEntity;
import project.stock.track.app.vo.catalogs.CatalogsStaticData;

public class ReadCsvTransactionIssuesBrokerSchwab extends ReadCsvTransactionIssues {
	
	private Long buildDate(String date, int addSeconds) throws ParseException {
		
		String newDate = date + " " + "00:00:00";
		Long dateMillis = dateFormatUtil.formatDate(newDate, "MM/dd/yyyy hh:mm:ss").getTime();
		
		return new DateTime(dateMillis).plusSeconds(addSeconds).toDate().getTime();
	}
	
	private String buildTypeTransaction(String transactionDescription) {
		
		if (transactionDescription.contains("Buy"))
			return TYPE_TRANSACTION_BUY;
		else if (transactionDescription.contains("Sell Short"))
			return TYPE_TRANSACTION_SHORT_SELL;
		else if (transactionDescription.contains("Sell"))
			return TYPE_TRANSACTION_SELL;
		else
			return null;
	}
	
	private BigDecimal buildPrice(String price) {
		return new BigDecimal(price.replace("$", "").replace(",", ""));
	}
	
	private boolean determineSkipRow(List<String> rowRecord) {
		
		return !(rowRecord.get(1).equals("Buy") || rowRecord.get(1).equals("Sell") || rowRecord.get(1).equals("Sell Short"));
	}

	public List<TransactionIssueFilePojo> readCsvFile(List<List<String>> records) throws ParseException, IOException {
		
		List<TransactionIssueFilePojo> issueTransactionDataPojos = new ArrayList<>();
		int timeSeconds = 0;
		boolean isRowHeader = false;
		
		for (List<String> rowRecord: records) {
			
			if (!isRowHeader && customArraysUtil.compareList(rowRecord, CatalogsStaticData.CsvReportsHeaders.CSV_HEADER_CHARLES_SCHWAB))
				isRowHeader = true;
				
			if (!isRowHeader || determineSkipRow(rowRecord))
				continue;
			
			TransactionIssueFilePojo transactionIssueFileDataPojo = new TransactionIssueFilePojo();
			transactionIssueFileDataPojo.setTaxesPercentage(BigDecimal.valueOf(10));
			transactionIssueFileDataPojo.setIssue(rowRecord.get(2));
			transactionIssueFileDataPojo.setDate(buildDate(rowRecord.get(0), ++timeSeconds));
			transactionIssueFileDataPojo.setTypeTransaction(buildTypeTransaction(rowRecord.get(1)));
			transactionIssueFileDataPojo.setTitles(Double.parseDouble(rowRecord.get(4)));
			transactionIssueFileDataPojo.setPrice(buildPrice(rowRecord.get(5)));
			transactionIssueFileDataPojo.setTypeCurrency(CatalogsEntity.CatalogTypeCurrency.USD);
			transactionIssueFileDataPojo.setComissionPercentage(BigDecimal.valueOf(0));
			transactionIssueFileDataPojo.setBroker(CatalogsEntity.CatalogBroker.CHARLES_SCHWAB);
			transactionIssueFileDataPojo.setIsSlice(numberDataUtil.hasFractionalPart(transactionIssueFileDataPojo.getTitles()));
			
			issueTransactionDataPojos.add(transactionIssueFileDataPojo);
		}
		
		return issueTransactionDataPojos;
	}
}
