package project.stock.track.services.dollarprice.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import lib.base.backend.utils.date.DateFormatUtil;
import lombok.RequiredArgsConstructor;
import project.stock.track.app.beans.rest.dollarprice.DollarPriceBean;
import project.stock.track.app.beans.rest.dollarprice.service.currentlayer.DollarPriceCurrentLayerBean;
import project.stock.track.app.beans.rest.dollarprice.service.currentlayer.QuoteBean;
import project.stock.track.app.repository.ConfigControlRepositoryImpl;
import project.stock.track.app.vo.catalogs.CatalogsEntity;
import project.stock.track.app.vo.catalogs.CatalogsStaticData;
import project.stock.track.services.dollarprice.DollarPriceService;

@RequiredArgsConstructor
public class DollarPriceCurrentLayerServiceImpl implements DollarPriceService {
	
	
	private final RestTemplate restTemplate;
	private final ConfigControlRepositoryImpl configControlRepositoryImpl;
	
	private DateFormatUtil dateFormatUtil = new DateFormatUtil();

	@Override
	public DollarPriceBean getDollarPrice() throws ParseException {
		
		return null;
	}

	@Override
	public List<DollarPriceBean> getDollarPriceHistorical(long dateStart, long dateEnd) throws ParseException {

		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("source", "USD");
		map.add("currencies", "MXN");
		map.add("access_key", configControlRepositoryImpl.getParameterValue(CatalogsEntity.ConfigControl.API_PRICE_DOLLAR_CURRENT_LAYER_TOKEN).getValue());
		map.add("start_date", dateFormatUtil.formatDate(new Date(dateStart), CatalogsStaticData.PriceDollar.DATE_FORMAT_DEFAULT_CURRENT_LAYER));
		map.add("end_date", dateFormatUtil.formatDate(new Date(dateEnd), CatalogsStaticData.PriceDollar.DATE_FORMAT_DEFAULT_CURRENT_LAYER));
		
		String envPriceDollar = configControlRepositoryImpl.getParameterValue(CatalogsEntity.ConfigControl.API_PRICE_DOLLAR_CURRENT_LAYER_URI).getValue();
		
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
                dollarPriceBean.setDate(dateFormatUtil.formatDate(currencyPair, CatalogsStaticData.PriceDollar.DATE_FORMAT_DEFAULT_CURRENT_LAYER).getTime());
                dollarPriceBean.setPrice(quote.getMxn().toPlainString());
                
                dollarPriceBeans.add(dollarPriceBean);
            } 
		}
		
		return dollarPriceBeans;
	}
}
