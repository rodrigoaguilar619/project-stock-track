package project.stock.track.config.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

import lib.base.backend.modules.catalog.interaface.CatalogDefinition;
import project.stock.track.app.repository.ConfigControlRepositoryImpl;
import project.stock.track.config.catalog.CatalogStockTrackDefinition;
import project.stock.track.services.dollarprice.DollarPriceService;
import project.stock.track.services.dollarprice.impl.DollarPriceBancoMexicoServiceImpl;
import project.stock.track.services.dollarprice.impl.DollarPriceCurrentLayerServiceImpl;
import project.stock.track.services.exchangetrade.IssueTrackService;
import project.stock.track.services.exchangetrade.impl.IssueTrackTiingoServiceImpl;

@Configuration
public class ServiceBeans {
	
	private final RestTemplate restTemplate;
	private final ConfigControlRepositoryImpl configControlRepositoryImpl;
	
	public ServiceBeans(RestTemplate restTemplate, ConfigControlRepositoryImpl configControlRepositoryImpl) {
		this.restTemplate = restTemplate;
		this.configControlRepositoryImpl = configControlRepositoryImpl;
	}
	
	@Bean
	public CatalogDefinition buildCatalogDefinition() {
		return new CatalogStockTrackDefinition();
	}

	@Bean
	public IssueTrackService generateIssueTrackTiingoService() {
		return new IssueTrackTiingoServiceImpl(restTemplate, configControlRepositoryImpl);
	}
	
	@Bean
	@Primary
	public DollarPriceService generateDollarPriceService() {
		return new DollarPriceBancoMexicoServiceImpl(restTemplate, configControlRepositoryImpl);
	}
	
	@Bean("dollarPriceServiceCurrentLayer")
	public DollarPriceService generateDollarPriceServiceCurrentLayer() {
		return new DollarPriceCurrentLayerServiceImpl(restTemplate, configControlRepositoryImpl);
	}
	
}
