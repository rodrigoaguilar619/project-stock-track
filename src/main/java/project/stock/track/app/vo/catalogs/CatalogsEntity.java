package project.stock.track.app.vo.catalogs;

public class CatalogsEntity {

	private CatalogsEntity() {
	 }

	public static final class CatalogTypeStock {
		
		private CatalogTypeStock() {
		 }
		
		public static final Integer ISSUE = 1;
		public static final Integer CRYPTO = 2;
	}
	
	public static final class CatalogTypeCurrency {
		
		private CatalogTypeCurrency() {
		 }
		
		public static final Integer MXN = 1;
		public static final Integer USD = 2;
	}
	
	public static final class CatalogTypeMovement {
		
		private CatalogTypeMovement() {
		 }
		
		public static final Integer DEPOSIT = 1;
		public static final Integer WITHDRAW = 2;
	}
	
	public static final class CatalogBroker {
		
		private CatalogBroker() {
		 }
		
		public static final Integer GBM_HOMBROKER = 1;
		public static final Integer CHARLES_SCHWAB = 2;
	}
	
	public static final class CatalogStatusIssue {
		
		private CatalogStatusIssue() {
		 }
		
		public static final Integer ACTIVE = 1;
		public static final Integer INACTIVE = 2;
	}
	
	public static final class CatalogStatusQuick {
		
		private CatalogStatusQuick() {
		 }
		
		public static final Integer ACTIVE = 1;
		public static final Integer INACTIVE = 2;
	}
	
	public static final class CatalogStatusTrading {
		
		private CatalogStatusTrading() {
		 }
		
		public static final Integer ACTIVE = 1;
		public static final Integer INACTIVE = 2;
	}
	
	public static final class CatalogSector {
		
		private CatalogSector() {
		 }
		
		public static final Integer FINANCIALS = 1;
		public static final Integer TECHNOLOGY = 2;
		public static final Integer HEALTHCARE = 3;
		public static final Integer MATERIALS = 4;
		public static final Integer REAL_ESTATE = 5;
		public static final Integer CONSUMER_STAPLES = 6;
		public static final Integer CONSUMER_DISCRETIONARY = 7;
		public static final Integer UTILITIES = 8;
		public static final Integer ENERGY = 9;
		public static final Integer INDUSTRIALS = 10;
		public static final Integer CONSUMER_SERVICES = 11;
		public static final Integer CONSUMER_CYCLICAL = 12;
		public static final Integer CONSUMER_DEFENSIVE = 13;
	}
	
	public static final class CatalogStatusIssueMovement {
		
		private CatalogStatusIssueMovement() {
		 }
		
		public static final Integer ACTIVE = 1;
		public static final Integer INACTIVE = 2;
	}
	
	public static final class ConfigControl {
		
		private ConfigControl() {
		 }
		
		public static final String API_STOCK_TIINGO_TOKEN_ONE = "api.stock.tiingo.token.one";
		public static final String API_STOCK_TIINGO_TOKEN_TWO = "api.stock.tiingo.token.two";
		public static final String API_PRICE_DOLLAR_BANXICO_TOKEN = "api.pricedollar.bancomexico.token";
		public static final String API_PRICE_DOLLAR_BANXICO_URI = "api.pricedollar.bancomexico.price";
		public static final String API_STOCK_TIINGO_IEX = "api.stock.tiingo.iex";
		public static final String API_STOCK_TIINGO_HISTORICAL_ISSUE = "api.stock.tiingo.finantial.historical.issue";
		public static final String API_STOCK_TIINGO_HISTORICAL_CRYPTO = "api.stock.tiingo.finantial.historical.crypto";
		public static final String API_STOCK_ALPHA_TOKEN = "api.stock.alpha.token";
		public static final String API_STOCK_ALPHA_URI = "api.stock.alpha.finantial.historical.issue";
		public static final String SCHEDULE_ISSUE_LAST_PRICE_UPDATE = "api.schedule.issue.lastprice.update.enabled";
	}
}
