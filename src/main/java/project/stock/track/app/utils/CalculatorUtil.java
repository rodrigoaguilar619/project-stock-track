package project.stock.track.app.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.beans.factory.annotation.Autowired;

import project.stock.track.app.beans.entity.DollarHistoricalPriceEntity;
import project.stock.track.app.repository.DollarHistoricalPriceRepositoryImpl;
import project.stock.track.app.vo.catalogs.CatalogsEntity;
import project.stock.track.app.vo.catalogs.CatalogsStaticData;

public class CalculatorUtil {
	
	@Autowired
	DollarHistoricalPriceRepositoryImpl dollarHistoricalPriceRespository;
	
	public BigDecimal calculateTotalPriceSellAfter(BigDecimal priceBuy, BigDecimal priceSell, BigDecimal commision, BigDecimal taxes) {
		
		BigDecimal taxesTotal = priceSell.subtract(priceBuy).multiply(taxes).divide(BigDecimal.valueOf(100));
		BigDecimal commisionTotal = priceSell.multiply(commision).divide(BigDecimal.valueOf(100));
		
		return priceSell.subtract(taxesTotal).subtract(commisionTotal);
	}

	public BigDecimal calculatePercentageUpDown(BigDecimal valueTotal, BigDecimal valueToEstimatePercentage) {
		
		if (valueTotal == null || valueToEstimatePercentage == null)
			return new BigDecimal("0");
		
		BigDecimal total = valueToEstimatePercentage.multiply(BigDecimal.valueOf(100)).divide(valueTotal, 5, RoundingMode.HALF_DOWN);
		
		return total.subtract(BigDecimal.valueOf(100));
	}
	
	public BigDecimal calculateSellEstimate(BigDecimal issuePrice, BigDecimal priceDollar) {
		
		if (priceDollar == null) {
			DollarHistoricalPriceEntity dollarHistoricalPriceEntity = dollarHistoricalPriceRespository.findLastRecord();
			priceDollar = dollarHistoricalPriceEntity.getPrice();
			priceDollar = priceDollar.subtract(priceDollar.multiply(CatalogsStaticData.StaticData.DEFAULT_DOLAR_PRICE_DEPRECATE_PERCENTAGE).divide(BigDecimal.valueOf(100)));
		}
		
		return priceDollar.multiply(issuePrice);
	}
	
	public BigDecimal calculatePercentage(BigDecimal value, BigDecimal percentage) {
		
		return value.multiply(percentage).divide(BigDecimal.valueOf(100), 5, RoundingMode.HALF_DOWN);
	}

	public BigDecimal determineFinalValue(BigDecimal currentPrice, Integer idTypeCurrency, BigDecimal dollarPrice) {
		
		BigDecimal finalPrice = null;
		
		if (idTypeCurrency.equals(CatalogsEntity.CatalogTypeCurrency.MXN))
			finalPrice = currentPrice.multiply(dollarPrice);
		else
			finalPrice = currentPrice;
		
		return finalPrice;
	}
}
