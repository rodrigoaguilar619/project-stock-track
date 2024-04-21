package project.stock.track.config.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lib.base.backend.modules.catalog.interaface.CatalogDefinition;
import project.stock.track.app.utils.CalculatorUtil;
import project.stock.track.app.utils.CustomArraysUtil;
import project.stock.track.app.utils.IssueHistoricalUtil;
import project.stock.track.app.utils.IssueUtil;
import project.stock.track.app.utils.MapEntityToPojoUtil;
import project.stock.track.app.utils.MapPojoToEntityUtil;
import project.stock.track.config.catalog.CatalogStockTrackDefinition;

@Configuration
public class AppUtilBeans {
	
	@Bean
	public CalculatorUtil buildCalculatorUtil() {
		return new CalculatorUtil();
	}
	
	@Bean
	public IssueUtil buildIssueUtil() {
		return new IssueUtil();
	}
	
	@Bean
	public IssueHistoricalUtil buildIssueHistoricalUtil() {
		return new IssueHistoricalUtil();
	}
	
	@Bean
	public MapEntityToPojoUtil buildMapEntityToPojoUtil() {
		return new MapEntityToPojoUtil();
	}
	
	@Bean
	public MapPojoToEntityUtil buildMapPojoToEntityUtil() {
		return new MapPojoToEntityUtil();
	}
	
	@Bean
	public CustomArraysUtil buildCustomArraysUtil() {
		return new CustomArraysUtil();
	}
	
	@Bean
	public CatalogDefinition buildCatalogDefinition() {
		return new CatalogStockTrackDefinition();
	}

}
