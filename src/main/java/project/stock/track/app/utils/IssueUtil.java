package project.stock.track.app.utils;

import java.math.BigDecimal;

import lib.base.backend.utils.date.DateUtil;
import project.stock.track.app.beans.entity.DollarHistoricalPriceEntity;
import project.stock.track.app.beans.entity.IssuesHistoricalEntity;
import project.stock.track.app.beans.entity.IssuesLastPriceTmpEntity;
import project.stock.track.app.beans.pojos.business.issues.IssueCurrentPricePojo;
import project.stock.track.app.vo.entities.CatalogTypeCurrencyEnum;

public class IssueUtil {
	
	private DateUtil dateUtil = new DateUtil();

	public IssueCurrentPricePojo getCurrentPrice(IssuesLastPriceTmpEntity tempIssuesLastPriceEntity, IssuesHistoricalEntity issuesHistoricalEntityLastRecord) {
		
		long dateHistorical = issuesHistoricalEntityLastRecord != null ? dateUtil.getMillis(issuesHistoricalEntityLastRecord.getId().getIdDate()) : 0;
		long dateTempLastPrice = tempIssuesLastPriceEntity != null && tempIssuesLastPriceEntity.getTimestamp() != null ? dateUtil.getMillis(tempIssuesLastPriceEntity.getTimestamp()) : 0;
		
		BigDecimal currentPrice = null;
		
		if (dateHistorical > dateTempLastPrice) {
			currentPrice = issuesHistoricalEntityLastRecord != null ? issuesHistoricalEntityLastRecord.getClose() : null;
		}
		else if (tempIssuesLastPriceEntity != null) {
			currentPrice = tempIssuesLastPriceEntity.getLast();
			dateHistorical = dateTempLastPrice;
		}
		
		IssueCurrentPricePojo issueCurrentPricePojo = new IssueCurrentPricePojo();
		issueCurrentPricePojo.setCurrentPrice(currentPrice);
		issueCurrentPricePojo.setDate(dateHistorical == 0 ? null : dateHistorical);
		
		return issueCurrentPricePojo;
	}
	
	public IssueCurrentPricePojo getCurrentPrice(IssuesLastPriceTmpEntity tempIssuesLastPriceEntity, IssuesHistoricalEntity issuesHistoricalEntityLastRecord,
			DollarHistoricalPriceEntity dollarHistoricalPriceEntity, int idTypeCurrency) {
		
		IssueCurrentPricePojo issueCurrentPricePojo = getCurrentPrice(tempIssuesLastPriceEntity, issuesHistoricalEntityLastRecord);
		
		if(idTypeCurrency == CatalogTypeCurrencyEnum.MXN.getValue())
			issueCurrentPricePojo.setCurrentPrice(issueCurrentPricePojo.getCurrentPrice() != null ? issueCurrentPricePojo.getCurrentPrice().multiply(dollarHistoricalPriceEntity.getPrice()) : null);
		
		return issueCurrentPricePojo;
	}
}
