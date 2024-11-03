package project.stock.track.config.helpers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import project.stock.track.app.beans.pojos.business.transaction.CurrencyValuesPojo;
import project.stock.track.app.vo.catalogs.CatalogsEntity.CatalogTypeCurrency;

@RequiredArgsConstructor
@Component
public class CurrencyDataHelper {
	
	public CurrencyValuesPojo getCurrencyValues(Integer idTypeCurrency, BigDecimal dollarPrice, BigDecimal currentPrice) {
		
		CurrencyValuesPojo currencyValuesPojo = new CurrencyValuesPojo();
		
		if(idTypeCurrency == CatalogTypeCurrency.USD) {
			currencyValuesPojo.setValueUsd(currentPrice);
			currencyValuesPojo.setValueMxn(currentPrice.multiply(dollarPrice));
		}
		else if(idTypeCurrency == CatalogTypeCurrency.MXN) {
			currencyValuesPojo.setValueUsd(currentPrice.divide(dollarPrice, 5, RoundingMode.HALF_DOWN));
			currencyValuesPojo.setValueMxn(currentPrice);
		}
		
		return currencyValuesPojo;
	}
}
