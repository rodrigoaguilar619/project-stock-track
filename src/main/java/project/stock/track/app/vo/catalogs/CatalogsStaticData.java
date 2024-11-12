package project.stock.track.app.vo.catalogs;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CatalogsStaticData {
	
	public final class StaticSql {
		
		private StaticSql() {
		 }
		
		public static final String UNIX_TIMESTAMP = "unix_timestamp";
	}
	
	public static final class StaticData {
		
		private StaticData() {
		 }
		
		public static final BigDecimal DEFAULT_COMMISION_GBM = BigDecimal.valueOf(0.29);
		public static final BigDecimal DEFAULT_COMMISION_CHARLES_SCHWAB = BigDecimal.valueOf(0);
		public static final BigDecimal DEFAULT_TAXES_PERCENTAGE_GBM = BigDecimal.valueOf(0);
		public static final BigDecimal DEFAULT_TAXES_PERCENTAGE_CHARLES_SCHWAB = BigDecimal.valueOf(0);
		public static final BigDecimal DEFAULT_DOLAR_PRICE_DEPRECATE_PERCENTAGE = BigDecimal.valueOf(1.5);
		public static final String DEFAULT_CURRENCY_FORMAT = "YYYY-MM-dd";
	}
	
	public static final class OptionTypeQueryIssuesHistorial {
		
		private OptionTypeQueryIssuesHistorial() {
		 }
		
		public static final String QUICK = "QUICK";
		public static final String TRADING = "TRADING";
		public static final String FILTER = "FILTER";
	}
	
	public final class MapValues {
		
		private MapValues() {
		 }
		
		public static final String CURRENT_PRICE = "currentPrice";
	}
	
	public final class PriceDollar {
		
		private PriceDollar() {
		 }
		
		public static final String DATE_FORMAT_DEFAULT_BANCO_MEXICO = "dd/MM/yyyy";
		public static final String DATE_FORMAT_DEFAULT_CURRENT_LAYER = "yyyy-MM-dd";
	}
	
	public static final class CsvReportsHeaders {
		
		private CsvReportsHeaders() {
		 }
		
		public static final List<String> CSV_HEADER_HOMEBROKER = Collections.unmodifiableList(Arrays.asList("Emisora","Fecha","Hora","Descripción","Títulos","Precio","Tasa","Plazo","Intereses","Impuestos","Comisión","Importe","Saldo"));
		public static final List<String> CSV_HEADER_CHARLES_SCHWAB = Collections.unmodifiableList(Arrays.asList("Date","Action","Symbol","Description","Quantity","Price","Fees & Comm","Amount",""));
		public static final List<String> CSV_HEADER_CHARLES_SCHWAB_2 = Collections.unmodifiableList(Arrays.asList("Date","Action","Symbol","Description","Quantity","Price","Fees & Comm","Amount"));
	}
	
	public final class ServiceTiingo {
		
		private ServiceTiingo() {
		 }
		
		public static final String DATE_FORMAT_DEFAULT = "yyyy-MM-dd";
	}
}
