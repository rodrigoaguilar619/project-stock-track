package project.stock.track.modules.business.files.transactions;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import project.stock.track.app.beans.pojos.business.transaction.TransactionIssueFilePojo;
import project.stock.track.app.vo.catalogs.CatalogsEntity;

public class ReadCsvTransactionIssuesBrokerGbm extends ReadCsvTransactionIssues {
	
	private String replaceMonth(String date, String textToFind, String textToReplace) {
		
		if (date.contains(textToFind))
			date = date.replace(textToFind, textToReplace);
		
		return date;
	}
	
	private Long buildDate(String date, int addSeconds) throws ParseException {
		
		date = replaceMonth(date, "ene", "01");
		date = replaceMonth(date, "feb", "02");
		date = replaceMonth(date, "mar", "03");
		date = replaceMonth(date, "abr", "04");
		date = replaceMonth(date, "may", "05");
		date = replaceMonth(date, "jun", "06");
		date = replaceMonth(date, "jul", "07");
		date = replaceMonth(date, "ago", "08");
		date = replaceMonth(date, "sep", "09");
		date = replaceMonth(date, "oct", "10");
		date = replaceMonth(date, "nov", "11");
		date = replaceMonth(date, "dic", "12");
		
		Long dateMillis = dateFormatUtil.formatDate(date, "dd/MM/yyyy hh:mm:ss").getTime();

		return new DateTime(dateMillis).plusSeconds(addSeconds).toDate().getTime();
	}
	
	private String buildTypeTransaction(String transactionDescription) {
		
		if (transactionDescription.contains("Venta"))
			return TYPE_TRANSACTION_SELL;
		else if (transactionDescription.contains("Compra de Acciones."))
			return TYPE_TRANSACTION_BUY; 
		else
			return null;
	}
	
	private String buildIssue(String issueDescription) {
		String issue = issueDescription.split(" ")[0].trim();
		
		switch(issue) {
			case "CCL1":
				return "CCL";
			case "ALSEA":
				return "ALSSF";
			case "VOLAR":
				return "VLRS";
			case "OMA":
				return "OMAB";
			case "ASUR":
				return "ASURB";
			case "OXY1":
				return "OXY";
			default:
				return issue;
		}
	}
	
	private BigDecimal buildPrice(String price) {
		return new BigDecimal(price.replace("$", "").replace(",", ""));
	}
	
	private boolean determineSkipRow(List<String> rowRecord) {
		
		return (rowRecord.size() < 13 || rowRecord.get(0).contains("GBMF2"));
	}

	public List<TransactionIssueFilePojo> readCsvFile(List<List<String>> records) throws ParseException, IOException {
		
		List<TransactionIssueFilePojo> issueTransactionDataPojos = new ArrayList<>();
		int rowCount = 0;
		int timeSeconds = 0;
		
		for (List<String> rowRecord: records) {
			
			rowCount ++;
			if (rowCount == 1 || determineSkipRow(rowRecord))
				continue;
			
			TransactionIssueFilePojo transactionIssueFileDataPojo = new TransactionIssueFilePojo();
			transactionIssueFileDataPojo.setTaxesPercentage(BigDecimal.valueOf(10));
			transactionIssueFileDataPojo.setIssue(buildIssue(rowRecord.get(0)));
			transactionIssueFileDataPojo.setDate(buildDate(rowRecord.get(1) + " " +rowRecord.get(2), ++timeSeconds));
			transactionIssueFileDataPojo.setTypeTransaction(buildTypeTransaction(rowRecord.get(3)));
			transactionIssueFileDataPojo.setTitles(Double.parseDouble(rowRecord.get(4).replace(",", "")));
			transactionIssueFileDataPojo.setPrice(buildPrice(rowRecord.get(5)));
			transactionIssueFileDataPojo.setTypeCurrency(CatalogsEntity.CatalogTypeCurrency.MXN);
			transactionIssueFileDataPojo.setBroker(CatalogsEntity.CatalogBroker.GBM_HOMBROKER);
			transactionIssueFileDataPojo.setIsSlice(numberDataUtil.hasFractionalPart(transactionIssueFileDataPojo.getTitles()));
			
			BigDecimal comissionPercentage = buildPrice(rowRecord.get(10)).divide(BigDecimal.valueOf(transactionIssueFileDataPojo.getTitles()), 5, RoundingMode.HALF_UP);
			BigDecimal comissionPercentageTotal = comissionPercentage.multiply(BigDecimal.valueOf(16)).divide(BigDecimal.valueOf(100));
			comissionPercentageTotal = comissionPercentageTotal.add(comissionPercentage);
			comissionPercentageTotal = comissionPercentageTotal.multiply(BigDecimal.valueOf(100)).divide(transactionIssueFileDataPojo.getPrice(), 5, RoundingMode.HALF_UP);
			transactionIssueFileDataPojo.setComissionPercentage(comissionPercentageTotal.setScale(2, RoundingMode.HALF_UP));
			
			issueTransactionDataPojos.add(transactionIssueFileDataPojo);
		}
		
		return issueTransactionDataPojos;
	}
}
