package project.stock.track.services.dollarprice.impl;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import lib.base.backend.utils.date.DateFormatUtil;
import lib.base.backend.utils.date.DateUtil;
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
	private DateUtil dateUtil = new DateUtil();

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
			dollarPriceBean.setDate(dateUtil.getMillis(dateFormatUtil.formatLocalDateTime(dollarPriceBancoMexicoBean.getBmx().getSeries().get(0).getDatos().get(0).getFecha(), CatalogsStaticData.PriceDollar.DATE_FORMAT_DEFAULT_BANCO_MEXICO)));
			dollarPriceBean.setPrice(dollarPriceBancoMexicoBean.getBmx().getSeries().get(0).getDatos().get(0).getDato());
		}
		
		return dollarPriceBean;
	}

	@Override
	public List<DollarPriceBean> getDollarPriceHistorical(LocalDate dateStart, LocalDate dateEnd) {
		return new ArrayList<>();
	}

}
