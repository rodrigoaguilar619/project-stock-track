package project.stock.track.services.dollarprice.impl;

import java.text.ParseException;
import java.util.List;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import lib.base.backend.utils.date.DateFormatUtil;
import lombok.RequiredArgsConstructor;
import project.stock.track.app.beans.rest.dollarprice.DollarPriceBean;
import project.stock.track.app.beans.rest.dollarprice.service.bancomexico.DollarPriceBancoMexicoBean;
import project.stock.track.app.repository.ConfigControlRepositoryImpl;
import project.stock.track.app.vo.catalogs.CatalogsEntity;
import project.stock.track.app.vo.catalogs.CatalogsStaticData;
import project.stock.track.services.dollarprice.DollarPriceService;

@RequiredArgsConstructor
public class DollarPriceBancoMexicoServiceImpl implements DollarPriceService {
	
	
	private final RestTemplate restTemplate;
	private final ConfigControlRepositoryImpl configControlRepositoryImpl;
	
	private DateFormatUtil dateFormatUtil = new DateFormatUtil();

	@Override
	public DollarPriceBean getDollarPrice() throws ParseException {
		
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("token", configControlRepositoryImpl.getParameterValue(CatalogsEntity.ConfigControl.API_PRICE_DOLLAR_BANXICO_TOKEN).getValue());
		
		String envPriceDollar = configControlRepositoryImpl.getParameterValue(CatalogsEntity.ConfigControl.API_PRICE_DOLLAR_BANXICO_URI).getValue();
		if (envPriceDollar == null)
			envPriceDollar = "";
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(envPriceDollar)
				.queryParams(map);
		
		DollarPriceBancoMexicoBean dollarPriceBancoMexicoBean = restTemplate.getForObject(
		        builder.toUriString(),
		        DollarPriceBancoMexicoBean.class);
		
		DollarPriceBean dollarPriceBean = new DollarPriceBean();
		
		if (dollarPriceBancoMexicoBean != null && dollarPriceBancoMexicoBean.getBmx() != null) {
			dollarPriceBean.setDate(dateFormatUtil.formatDate(dollarPriceBancoMexicoBean.getBmx().getSeries().get(0).getDatos().get(0).getFecha(), CatalogsStaticData.PriceDollar.DATE_FORMAT_DEFAULT_BANCO_MEXICO).getTime());
			dollarPriceBean.setPrice(dollarPriceBancoMexicoBean.getBmx().getSeries().get(0).getDatos().get(0).getDato());
		}
		
		return dollarPriceBean;
	}

	@Override
	public List<DollarPriceBean> getDollarPriceHistorical(long dateStart, long dateEnd) {
		return null;
	}

}
