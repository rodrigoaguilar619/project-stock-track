package project.stock.track.app.vo.catalogs;

public class CatalogsErrorMessage {
	
	private CatalogsErrorMessage(){}

	private static final String ERROR_MSG_ISSUES_EXIST = "issues %s already registered";
	private static final String ERROR_MSG_ISSUE_NOT_REGISTERED = "Issue doesn't registered";
	private static final String ERROR_MSG_ISSUE_MANAGER_NOT_REGISTERED = "Issue Manager doesn't registered";
	private static final String ERROR_MSG_ISSUE_DESCRIPTION_NOT_REGISTERED = "Issue %s doesn't registered";
	private static final String ERROR_MSG_FILE_LOAD_ISSUE_NOT_REGISTERED = "Issue not registered issue: %s";
	private static final String ERROR_MSG_FILE_BUY_TRANSACTION_REGISTER = "Error registering buy transaction";
	private static final String ERROR_MSG_FILE_SELL_TRANSACTION_REGISTER = "Error registering sell transaction. issue: %s";
	private static final String ERROR_MSG_FILE_SHORT_SELL_TRANSACTION_REGISTER = "Error registering sell transaction. issue: %s";
	private static final String ERROR_MSG_FILE_EMPTY = "File is empty";
	private static final String ERROR_MSG_FILE_TRANSACTION_DEPOSIT_WITHDRAW = "Error registering transaction. deposit/withdraw";
	private static final String ERROR_MSG_FILE_TRANSACTION_BANK = "Error registering transaction bank";
	private static final String ERROR_MSG_FILE_TRANSACTION_ISSUE = "Error registering transaction. issue: %s";
	private static final String ERROR_MSG_FILE_BAD_HEADER_FORMAT = "Bad header format. broker: %s";
	private static final String ERROR_MSG_FILE_BROKER_NOT_REGISTERED = "Broker doesn't registered";
	private static final String ERROR_MSG_EXCHANGE_TRADE_BAD_RANGE_DATE = "Error validating range date on service consume. issue: %s, date start: %s, date end: %s";
	private static final String ERROR_DOLLAR_HISTORICAL_PRICE_BUY_SELL_NOT_FOUND = "Dollar historical price not found for %s on date %s";
	private static final String ERROR_MSG_FILE_SLICE_MULTIPLE_DATE = "Error slice multiple dates. issue: %s. First merge current slices registered";
	private static final String ERROR_MSG_FILE_SLICE_SHARES_NOT_MATCH = "Error slice TOTAL shares not match. issue: %s. First SPLIT current slices registered";
	
	public static String getErrorMsgIssuesRegistered(String issues) {
		return String.format(ERROR_MSG_ISSUES_EXIST, issues);
	}
	
	public static String getErrorMsgIssueNotRegistered() {
		return ERROR_MSG_ISSUE_NOT_REGISTERED;
	}
	
	public static String getErrorMsgIssueManagerNotRegistered() {
		return ERROR_MSG_ISSUE_MANAGER_NOT_REGISTERED;
	}
	
	public static String getErrorMsgIssueDescriptionNotRegistered(String issue) {
		return String.format(ERROR_MSG_ISSUE_DESCRIPTION_NOT_REGISTERED, issue);
	}
	
	public static String getErrorMsgFileLoadIssueNotRegistered(String issue) {
		return String.format(ERROR_MSG_FILE_LOAD_ISSUE_NOT_REGISTERED, issue);
	}
	
	public static String getErrorMsgFileBuyTransactionRegister() {
		return ERROR_MSG_FILE_BUY_TRANSACTION_REGISTER;
	}
	
	public static String getErrorMsgFileSellTransactionRegister(String issue) {
		return String.format(ERROR_MSG_FILE_SELL_TRANSACTION_REGISTER, issue);
	}
	
	public static String getErrorMsgFileShortSellTransactionRegister(String issue) {
		return String.format(ERROR_MSG_FILE_SHORT_SELL_TRANSACTION_REGISTER, issue);
	}
	
	public static String getErrorMsgFileEmpty() {
		return ERROR_MSG_FILE_EMPTY;
	}
	
	public static String getErrorMsgFileBadHeaderFormat(String broker) {
		return String.format(ERROR_MSG_FILE_BAD_HEADER_FORMAT, broker);
	}
	
	public static String getErrorMsgFileBrokerNotRegistered() {
		return ERROR_MSG_FILE_BROKER_NOT_REGISTERED;
	}
	
	public static String getErrorMsgExchangeTradeBadRangeDate(String issue, String dateTo, String dateFrom) {
		return String.format(ERROR_MSG_EXCHANGE_TRADE_BAD_RANGE_DATE, issue, dateTo, dateFrom);
	}
	
	public static String getErrorMsgDollarHistoricalPriceBuySellNotFound(String issue, String date) {
		return String.format(ERROR_DOLLAR_HISTORICAL_PRICE_BUY_SELL_NOT_FOUND, issue, date);
	}
	
	public static String getErrorMsgFileSliceMultipleDate(String issue) {
		return String.format(ERROR_MSG_FILE_SLICE_MULTIPLE_DATE, issue);
	}
	
	public static String getErrorMsgFileSliceSharesNotMatch(String issue) {
		return String.format(ERROR_MSG_FILE_SLICE_SHARES_NOT_MATCH, issue);
	}
	
	public static String getErrorMsgFileTransactionDepositWithdraw() {
		return ERROR_MSG_FILE_TRANSACTION_DEPOSIT_WITHDRAW;
	}
	
	public static String getErrorMsgFileTransaction(String issue) {
		return String.format(ERROR_MSG_FILE_TRANSACTION_ISSUE, issue);
	}
	
	public static String getErrorMsgFileTransactionBank() {
		return ERROR_MSG_FILE_TRANSACTION_BANK;
	}
}
