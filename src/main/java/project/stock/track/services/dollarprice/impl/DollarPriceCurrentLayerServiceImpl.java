package project.stock.track.services.dollarprice.impl;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import lib.base.backend.utils.date.DateFormatUtil;
import lib.base.backend.utils.date.DateUtil;
import lombok.RequiredArgsConstructor;
import project.stock.track.app.beans.rest.dollarprice.DollarPriceBean;
import project.stock.track.app.beans.rest.dollarprice.service.currentlayer.DollarPriceCurrentLayerBean;
import project.stock.track.app.beans.rest.dollarprice.service.currentlayer.QuoteBean;
import project.stock.track.app.repository.ConfigControlRepositoryImpl;
import project.stock.track.app.vo.catalogs.CatalogsStaticData;
import project.stock.track.app.vo.entities.ConfigControlEnum;
import project.stock.track.services.dollarprice.DollarPriceService;

@RequiredArgsConstructor
public class DollarPriceCurrentLayerServiceImpl implements DollarPriceService {
	
	
	private final RestTemplate restTemplate;
	private final ConfigControlRepositoryImpl configControlRepositoryImpl;
	
	private DateFormatUtil dateFormatUtil = new DateFormatUtil();
	private DateUtil dateUtil = new DateUtil();

	@Override
	public DollarPriceBean getDollarPrice() throws ParseException {
		
		return null;
	}

	@Override
	public List<DollarPriceBean> getDollarPriceHistorical(LocalDate dateStart, LocalDate dateEnd) throws ParseException {

		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("source", "USD");
		map.add("currencies", "MXN");
		map.add("access_key", configControlRepositoryImpl.getParameterValue(ConfigControlEnum.API_PRICE_DOLLAR_CURRENT_LAYER_TOKEN.getValue()).getValue());
		map.add("start_date", dateFormatUtil.formatLocalDate(dateStart, CatalogsStaticData.PriceDollar.DATE_FORMAT_DEFAULT_CURRENT_LAYER));
		map.add("end_date", dateFormatUtil.formatLocalDate(dateEnd, CatalogsStaticData.PriceDollar.DATE_FORMAT_DEFAULT_CURRENT_LAYER));
		
		String envPriceDollar = configControlRepositoryImpl.getParameterValue(ConfigControlEnum.API_PRICE_DOLLAR_CURRENT_LAYER_URI.getValue()).getValue();
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(envPriceDollar + "/timeframe").queryParams(map);
		
		DollarPriceCurrentLayerBean dollarPriceCurrentLayerBean = restTemplate.getForObject(
		        builder.toUriString(),
		        DollarPriceCurrentLayerBean.class);
		
		List<DollarPriceBean> dollarPriceBeans = new ArrayList<>();
		
		if (dollarPriceCurrentLayerBean != null && dollarPriceCurrentLayerBean.getQuotes() != null) {
			
			for (Map.Entry<String, QuoteBean> currencyEntry : dollarPriceCurrentLayerBean.getQuotes().entrySet()) {
                
            	String currencyPair = currencyEntry.getKey();
                QuoteBean quote = currencyEntry.getValue();
                
                DollarPriceBean dollarPriceBean = new DollarPriceBean();
                dollarPriceBean.setDate(dateUtil.getMillis(dateFormatUtil.formatLocalDate(currencyPair, CatalogsStaticData.PriceDollar.DATE_FORMAT_DEFAULT_CURRENT_LAYER)));
                dollarPriceBean.setPrice(quote.getMxn().toPlainString());
                
                dollarPriceBeans.add(dollarPriceBean);
            } 
		}
		
		return dollarPriceBeans;
	}
}
