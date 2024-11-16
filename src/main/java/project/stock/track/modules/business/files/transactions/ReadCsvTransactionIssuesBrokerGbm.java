package project.stock.track.modules.business.files.transactions;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;

import lib.base.backend.exception.data.BusinessException;
import project.stock.track.app.beans.pojos.business.transaction.TransactionIssueFilePojo;
import project.stock.track.app.beans.pojos.business.transaction.TransactionMoneyFilePojo;
import project.stock.track.app.vo.catalogs.CatalogsEntity;
import project.stock.track.app.vo.catalogs.CatalogsStaticData;
import project.stock.track.app.vo.catalogs.CatalogsStaticData.CsvReportsHeaders;

public class ReadCsvTransactionIssuesBrokerGbm extends ReadCsvTransactionIssues {
	
	private List<String> TYPE_TRANSACTION_COMMON_IGNORE = Arrays.asList("INTERESES", "Retiro Titulos Fusión, Cust. Normal", "Deposito Titulos Fusión, Cust. Normal", "Retiro Titulos Split, Cust. Normal", "Deposito Titulos Split, Cust. Normal",
			"Retiro de Títulos por Escisión", "Deposito de Títulos por Escisión", "Deposito Titulos por Escision, Cust. Normal", "Deposito Titulos Dividendo Especie, Cust. Normal", "Retiro Titulos Canje, Cust. Normal",
			"Deposito Titulos Canje, Cust. Normal");
	private List<String> TYPE_TRANSACTION_ISSUE_IGNORE = new ArrayList<>(Arrays.asList("ISR 10 % POR DIVIDENDOS SIC", "ABONO DIVIDENDO EMISORA EXTRANJERA", "Abono Efectivo Dividendo, Cust. Normal", "COMPLEMENTO ABONO DIVIDENDO EMISORA EXT.",
			"DEPOSITO DE EFECTIVO", "CARGO DE EFECTIVO", "RETIRO DE EFECTIVO", "PAGO DE FRACCIONES POR DERECHO", "REEMBOLSO DISTRIBUCION DE CAPITAL", "ABONO PAGO DE FRACCIONES EN EFECTIVO"));
	private List<String> TYPE_TRANSACTION_MONEY_IGNORE = new ArrayList<>(Arrays.asList("Venta", "Compra de Acciones."));
	
	public ReadCsvTransactionIssuesBrokerGbm() {
		super();
		
		this.TYPE_TRANSACTION_ISSUE_IGNORE.addAll(TYPE_TRANSACTION_COMMON_IGNORE);
		this.TYPE_TRANSACTION_MONEY_IGNORE.addAll(TYPE_TRANSACTION_COMMON_IGNORE);
	}
	
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
	
	private String buildTypeTransactionIssues(String transactionDescription) throws BusinessException {
		
		if (TYPE_TRANSACTION_ISSUE_IGNORE.contains(transactionDescription))
			return null;
		
		if (transactionDescription.contains("Venta"))
			return TYPE_TRANSACTION_SELL;
		else if (transactionDescription.contains("Compra de Acciones."))
			return TYPE_TRANSACTION_BUY; 
		else
			throw new BusinessException("Transaction type not mapped: " + transactionDescription);
	}
	
	private String buildTypeTransactionMoney(String transactionDescription) throws BusinessException {

		if (TYPE_TRANSACTION_MONEY_IGNORE.contains(transactionDescription))
			return null;
		
		if (transactionDescription.contains("ISR 10 % POR DIVIDENDOS SIC"))
			return TYPE_TRANSACTION_TAX;
		else if (transactionDescription.contains("ABONO DIVIDENDO EMISORA EXTRANJERA"))
			return TYPE_TRANSACTION_DIVIDEND;
		else if (transactionDescription.contains("Abono Efectivo Dividendo, Cust. Normal"))
			return TYPE_TRANSACTION_DIVIDEND;
		else if (transactionDescription.contains("COMPLEMENTO ABONO DIVIDENDO EMISORA EXT."))
			return TYPE_TRANSACTION_DIVIDEND;
		else if (transactionDescription.contains("ABONO REEMBOLSO RETENCION DE DIVIDENDO"))
			return TYPE_TRANSACTION_DIVIDEND;
		else if (transactionDescription.contains("DEPOSITO DE EFECTIVO"))
			return TYPE_TRANSACTION_DEPOSIT;
		else if (transactionDescription.contains("CARGO DE EFECTIVO"))
			return TYPE_TRANSACTION_DEPOSIT;
		else if (transactionDescription.contains("ABONO DE EFECTIVO"))
			return TYPE_TRANSACTION_WITHDRAW;
		else if (transactionDescription.contains("RETIRO DE EFECTIVO"))
			return TYPE_TRANSACTION_WITHDRAW;
		else if (transactionDescription.contains("PAGO DE FRACCIONES POR DERECHO"))
			return TYPE_TRANSACTION_CASH_IN_LIEU;
		else if (transactionDescription.contains("REEMBOLSO DISTRIBUCION DE CAPITAL"))
			return TYPE_TRANSACTION_CASH_IN_LIEU;
		else if (transactionDescription.contains("ABONO PAGO DE FRACCIONES EN EFECTIVO"))
			return TYPE_TRANSACTION_CASH_IN_LIEU;
		else
			throw new BusinessException("Transaction type not mapped: " + transactionDescription);
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
				return buildIssueCommon(issue);
		}
	}
	
	private boolean determineSkipRow(int headerSize, List<String> rowRecord) {

		return (rowRecord.size() < headerSize || rowRecord.get(0).contains("GBMF2"));
	}

	public List<TransactionIssueFilePojo> readCsvFileIssues(List<List<String>> records) throws ParseException, IOException, BusinessException {
		
		List<TransactionIssueFilePojo> issueTransactionDataPojos = new ArrayList<>();
		int rowCount = 0;
		int timeSeconds = 0;
		
		for (List<String> rowRecord: records) {
			
			rowCount ++;
			if (rowCount == 1 || determineSkipRow(CsvReportsHeaders.CSV_HEADER_HOMEBROKER_ISSUES.size(), rowRecord))
				continue;
			
			TransactionIssueFilePojo transactionIssueFileDataPojo = new TransactionIssueFilePojo();
			transactionIssueFileDataPojo.setTaxesPercentage(CatalogsStaticData.StaticData.DEFAULT_TAXES_PERCENTAGE_GBM);
			transactionIssueFileDataPojo.setIssue(buildIssue(rowRecord.get(0)));
			transactionIssueFileDataPojo.setDate(buildDate(rowRecord.get(1) + " " +rowRecord.get(2), ++timeSeconds));
			transactionIssueFileDataPojo.setTypeTransaction(buildTypeTransactionIssues(rowRecord.get(3)));
			transactionIssueFileDataPojo.setTitles(new BigDecimal(rowRecord.get(4).replace(",", "")));
			transactionIssueFileDataPojo.setPrice(buildPrice(rowRecord.get(5)));
			transactionIssueFileDataPojo.setTypeCurrency(CatalogsEntity.CatalogTypeCurrency.MXN);
			transactionIssueFileDataPojo.setBroker(CatalogsEntity.CatalogBroker.GBM_HOMBROKER);
			transactionIssueFileDataPojo.setIsSlice(numberDataUtil.hasFractionalPart(transactionIssueFileDataPojo.getTitles()));
			
			BigDecimal comissionPercentage = buildPrice(rowRecord.get(10)).divide(transactionIssueFileDataPojo.getTitles(), 5, RoundingMode.HALF_UP);
			BigDecimal comissionPercentageTotal = comissionPercentage.multiply(BigDecimal.valueOf(16)).divide(BigDecimal.valueOf(100));
			comissionPercentageTotal = comissionPercentageTotal.add(comissionPercentage);
			comissionPercentageTotal = transactionIssueFileDataPojo.getPrice().compareTo(BigDecimal.valueOf(0)) == 0 ? BigDecimal.valueOf(0) : comissionPercentageTotal.multiply(BigDecimal.valueOf(100)).divide(transactionIssueFileDataPojo.getPrice(), 5, RoundingMode.HALF_UP);
			transactionIssueFileDataPojo.setComissionPercentage(comissionPercentageTotal.setScale(2, RoundingMode.HALF_UP));
			
			issueTransactionDataPojos.add(transactionIssueFileDataPojo);
		}
		
		return issueTransactionDataPojos;
	}
	
	public List<TransactionMoneyFilePojo> readCsvFileMoney(List<List<String>> records) throws ParseException, IOException, BusinessException {
		
		List<TransactionMoneyFilePojo> issueTransactionDataPojos = new ArrayList<>();
		int rowCount = 0;
		int timeSeconds = 0;
		
		for (List<String> rowRecord: records) {
			
			rowCount ++;
			if (rowCount == 1 || determineSkipRow(CsvReportsHeaders.CSV_HEADER_HOMEBROKER_MONEY.size(), rowRecord))
				continue;
			
			Date date = dateFormatUtil.formatDate(rowRecord.get(1) + " " +rowRecord.get(2), "dd/MM/yyyy hh:mm:ss");
			date = new DateTime(date.getTime()).plusSeconds(++timeSeconds).toDate();
			
			TransactionMoneyFilePojo transactionMoneyFilePojo = new TransactionMoneyFilePojo();
			transactionMoneyFilePojo.setDate(date.getTime());
			transactionMoneyFilePojo.setTypeTransaction(buildTypeTransactionMoney(rowRecord.get(4)));
			transactionMoneyFilePojo.setIssue(buildIssue(rowRecord.get(5)));
			transactionMoneyFilePojo.setAmount(buildPrice(rowRecord.get(6)).abs());
			transactionMoneyFilePojo.setTypeCurrency(CatalogsEntity.CatalogTypeCurrency.MXN);
			
			issueTransactionDataPojos.add(transactionMoneyFilePojo);
		}
		
		return issueTransactionDataPojos;
	}
}
