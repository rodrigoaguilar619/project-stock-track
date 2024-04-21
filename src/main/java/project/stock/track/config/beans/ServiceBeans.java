package project.stock.track.config.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import project.stock.track.services.dollarprice.DollarPriceService;
import project.stock.track.services.dollarprice.impl.DollarPriceBancoMexicoServiceImpl;
import project.stock.track.services.exchangetrade.IssueTrackService;
import project.stock.track.services.exchangetrade.impl.IssueTrackTiingoServiceImpl;

@Configuration
public class ServiceBeans {

	@Bean
	public IssueTrackService generateIssueTrackTiingoService() {
		return new IssueTrackTiingoServiceImpl();
	}
	
	@Bean
	public DollarPriceService generateDollarPriceService() {
		return new DollarPriceBancoMexicoServiceImpl();
	}
	
}
