package project.stock.track.app.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lib.base.backend.entity.generic.GenericCatalogIntEntity;
import lib.base.backend.entity.generic.GenericStatusEntity;
import lib.base.backend.utils.DataUtil;
import lib.base.backend.utils.date.DateUtil;
import project.stock.track.app.beans.entity.CatalogIssuesEntity;
import project.stock.track.app.beans.entity.CatalogTypeStockEntity;
import project.stock.track.app.beans.entity.IssuesLastPriceTmpEntity;
import project.stock.track.app.beans.entity.IssuesMovementsBuyEntity;
import project.stock.track.app.beans.pojos.entity.CatalogIssuesEntityDesPojo;
import project.stock.track.app.beans.pojos.entity.CatalogIssuesEntityPojo;
import project.stock.track.app.beans.pojos.entity.IssueLastPriceTmpEntityPojo;
import project.stock.track.app.beans.pojos.entity.IssueMovementBuyEntityPojo;
import project.stock.track.app.vo.catalogs.CatalogsEntity.CatalogTypeCurrency;

public class MapEntityToPojoUtil {
	
	DateUtil dateUtil = new DateUtil();
	DataUtil dataUtil = new DataUtil();
	
	public IssueLastPriceTmpEntityPojo mapTempIssueLastPrice(IssueLastPriceTmpEntityPojo tempIssueLastPricePojo, IssuesLastPriceTmpEntity tempIssuesLastPriceEntity) {
		
		if (tempIssueLastPricePojo == null)
			tempIssueLastPricePojo = new IssueLastPriceTmpEntityPojo();
		
		tempIssueLastPricePojo.setCurrentPrice(tempIssuesLastPriceEntity.getLast());
		tempIssueLastPricePojo.setDatePrice(tempIssuesLastPriceEntity.getTimestamp() != null ? dateUtil.getDateWithoutTime(tempIssuesLastPriceEntity.getTimestamp()).getTime() : null);
		tempIssueLastPricePojo.setCurrentDatePrice(tempIssuesLastPriceEntity.getTimestamp() != null ? tempIssuesLastPriceEntity.getTimestamp().getTime() : null);
		tempIssueLastPricePojo.setHighPrice(tempIssuesLastPriceEntity.getHigh());
		tempIssueLastPricePojo.setIssue(tempIssuesLastPriceEntity.getCatalogIssuesEntity().getInitials());
		tempIssueLastPricePojo.setIdIssue(tempIssuesLastPriceEntity.getIdIssue());
		tempIssueLastPricePojo.setOpen(tempIssuesLastPriceEntity.getOpen());
		tempIssueLastPricePojo.setPreviousPriceClose(tempIssuesLastPriceEntity.getPrevClose());
		tempIssueLastPricePojo.setVolume(tempIssuesLastPriceEntity.getVolume());
		
		return tempIssueLastPricePojo;
	}
	
	public CatalogIssuesEntityPojo mapCatalogIssue(CatalogIssuesEntity catalogIssuesEntity, CatalogIssuesEntityPojo catalogIssuesEntityPojo) {
		
		if (catalogIssuesEntityPojo == null)
			catalogIssuesEntityPojo = new CatalogIssuesEntityPojo();
		
		catalogIssuesEntityPojo.setIdIssue(catalogIssuesEntity.getId());
		catalogIssuesEntityPojo.setInitials(catalogIssuesEntity.getInitials());
		catalogIssuesEntityPojo.setDescription(catalogIssuesEntity.getDescription());
		catalogIssuesEntityPojo.setHistoricalStartDate(dataUtil.getValueOrNull(catalogIssuesEntity.getHistoricalStartDate(), Date::getTime));
		catalogIssuesEntityPojo.setIdSector(dataUtil.getValueOrNull(catalogIssuesEntity.getCatalogSectorEntity(), GenericCatalogIntEntity::getId));
		catalogIssuesEntityPojo.setIdTypeStock(dataUtil.getValueOrNull(catalogIssuesEntity.getCatalogTypeStockEntity(), CatalogTypeStockEntity::getId));
		catalogIssuesEntityPojo.setIdStatusIssue(dataUtil.getValueOrNull(catalogIssuesEntity.getCatalogStatusIssueEntity(), GenericStatusEntity::getId));
		catalogIssuesEntityPojo.setIsSp500(catalogIssuesEntity.getIsSp500());
		
		return catalogIssuesEntityPojo;
	}
	
	public CatalogIssuesEntityDesPojo mapCatalogIssueWithDescriptions(CatalogIssuesEntity catalogIssuesEntity, CatalogIssuesEntityDesPojo catalogIssuesEntityDesPojo) {
		
		if (catalogIssuesEntityDesPojo == null)
			catalogIssuesEntityDesPojo = new CatalogIssuesEntityDesPojo();
		
		catalogIssuesEntityDesPojo = (CatalogIssuesEntityDesPojo) mapCatalogIssue(catalogIssuesEntity, catalogIssuesEntityDesPojo);
		catalogIssuesEntityDesPojo.setDescriptionSector(dataUtil.getValueOrNull(catalogIssuesEntity.getCatalogSectorEntity(), GenericCatalogIntEntity::getDescription));
		catalogIssuesEntityDesPojo.setDescriptionTypeStock(dataUtil.getValueOrNull(catalogIssuesEntity.getCatalogTypeStockEntity(), CatalogTypeStockEntity::getDescription));
		catalogIssuesEntityDesPojo.setDescriptionStatusIssue(dataUtil.getValueOrNull(catalogIssuesEntity.getCatalogStatusIssueEntity(), GenericStatusEntity::getDescription));
		
		return catalogIssuesEntityDesPojo;
	}
	
	public IssueMovementBuyEntityPojo mapIssueMovementBuy(IssuesMovementsBuyEntity issueMovementBuyEntity, IssueMovementBuyEntityPojo issueMovementBuyPojo, Integer idTypeCurrency) {
		
		if (issueMovementBuyPojo == null)
			issueMovementBuyPojo = new IssueMovementBuyEntityPojo();
		
		BigDecimal buyPrice = issueMovementBuyEntity.getBuyPrice();
		BigDecimal sellPrice = issueMovementBuyEntity.getSellPrice();
		
		if (idTypeCurrency == CatalogTypeCurrency.MXN) {
			buyPrice = issueMovementBuyEntity.getBuyPriceMxn();
			sellPrice = issueMovementBuyEntity.getSellPriceMxn();
		}
			
		
		issueMovementBuyPojo.setBuyPrice(buyPrice);
		issueMovementBuyPojo.setSellPrice(sellPrice);
		issueMovementBuyPojo.setBuyDate(dataUtil.getValueOrNull(issueMovementBuyEntity.getBuyDate(), Date::getTime));
		issueMovementBuyPojo.setSellDate(dataUtil.getValueOrNull(issueMovementBuyEntity.getSellDate(), Date::getTime));
		issueMovementBuyPojo.setBuyTransactionNumber(issueMovementBuyEntity.getId().getBuyTransactionNumber());
		issueMovementBuyPojo.setTotalShares(issueMovementBuyEntity.getTotalShares());
		
		return issueMovementBuyPojo;
	}
	
	public List<IssueMovementBuyEntityPojo> mapIssueMovementBuyList(List<IssuesMovementsBuyEntity> issuesMovementsBuyEntities, Integer idTypeCurrency){
		
		List<IssueMovementBuyEntityPojo> issueMovementBuyPojos = new ArrayList<>();
		
		for(IssuesMovementsBuyEntity issuesMovementsBuyEntity: issuesMovementsBuyEntities) {
			issueMovementBuyPojos.add(mapIssueMovementBuy(issuesMovementsBuyEntity, null, idTypeCurrency));
		}
		
		return issueMovementBuyPojos;
	}
	
}
