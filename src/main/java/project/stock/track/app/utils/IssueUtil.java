package project.stock.track.app.utils;

import java.math.BigDecimal;

import project.stock.track.app.beans.entity.IssuesHistoricalEntity;
import project.stock.track.app.beans.entity.IssuesLastPriceTmpEntity;
import project.stock.track.app.beans.pojos.business.issues.IssueCurrentPricePojo;

public class IssueUtil {

	public IssueCurrentPricePojo getCurrentPrice(IssuesLastPriceTmpEntity tempIssuesLastPriceEntity, IssuesHistoricalEntity issuesHistoricalEntityLastRecord) {
		
		long dateHistorical = issuesHistoricalEntityLastRecord != null ? issuesHistoricalEntityLastRecord.getIssuesHistoricalEntityId().getIdDate().getTime() : 0;
		long dateTempLastPrice = tempIssuesLastPriceEntity != null && tempIssuesLastPriceEntity.getTimestamp() != null ? tempIssuesLastPriceEntity.getTimestamp().getTime() : 0;
		
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
}
