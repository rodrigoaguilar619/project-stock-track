package project.stock.track.app.vo.catalogs;

public class CatalogsUri {
	
	private CatalogsUri() {}
	
	public static final String API_SERVICE_DOLLAR_UPDATE = "/api/service/dollar/updateDollarPrice";
	public static final String API_ISSUES_LIST_GET = "/api/issues/getIssues";
	public static final String API_ISSUES_INDIVIDUAL_UPDATE = "/api/issues/updateIssue";
	public static final String API_ISSUES_INDIVIDUAL_GET = "/api/issues/getIssue";
	public static final String API_ISSUES_MULTIPLE_ADD = "/api/issues/addMultipleIssues";
	public static final String API_ISSUES_MANAGER_LIST_GET = "/api/manager/issues/getIssuesManager";
	public static final String API_ISSUES_MANAGER_INDIVIDUAL_GET = "/api/manager/issues/getIssueManager";
	public static final String API_ISSUES_MANAGER_INDIVIDUAL_UPDATE = "/api/manager/issues/updateIssueManager";
	public static final String API_ISSUES_HISTORICAL_LIST_GET = "/api/historical/getIssuesHistorical";
	public static final String API_ISSUES_HISTORICAL_LIST_UPDATE = "/api/historical/updateIssuesHistorical";
	public static final String API_ISSUES_HISTORICAL_INDVIDUAL_GET = "/api/historical/getIssueHistorical";
	public static final String API_ISSUES_LAST_PRICES_TEMP_GET = "/api/issuesLastPrices/getIssuesLastPrice";
	public static final String API_ISSUES_LAST_PRICES_TEMP_UPDATE = "/api/issuesLastPrices/updateIssuesLastPrice";
	public static final String API_ISSUES_MOVEMENTS_LIST_GET = "/api/issuesMovements/getIssuesMovements";
	public static final String API_ISSUES_MOVEMENTS_INDIVIDUAL_GET = "/api/issuesMovements/getIssueMovement";
	public static final String API_ISSUES_MOVEMENTS_INDIVIDUAL_SAVE = "/api/issuesMovements/saveIssueMovement";
	public static final String API_ISSUES_MOVEMENTS_INDIVIDUAL_UPDATE = "/api/issuesMovements/updateIssueMovement";
	public static final String API_ISSUES_MOVEMENTS_INDIVIDUAL_INACTIVATE = "/api/issuesMovements/inactivateIssueMovement";
	public static final String API_PORTFOLIO_LIST_GET = "/api/portfolio/getPortfolioList";
	public static final String API_PORTFOLIO_DATA_GET = "/api/portfolio/getPortfolioData";
	public static final String API_TRANSACION_ISSUES_TRACK_GET = "/api/transactions/getTransactionIssuesTrack";
	public static final String API_TRANSACION_ISSUES_FILE_LOAD = "/api/transactions/loadtransactionIssuesFile";
	
}
