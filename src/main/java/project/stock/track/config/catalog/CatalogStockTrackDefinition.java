package project.stock.track.config.catalog;

import java.util.LinkedHashMap;
import java.util.Map;

import lib.base.backend.modules.catalog.interaface.CatalogDefinition;
import project.stock.track.app.beans.entity.CatalogBrokerEntity;
import project.stock.track.app.beans.entity.CatalogSectorEntity;
import project.stock.track.app.beans.entity.CatalogStatusIssueEntity;
import project.stock.track.app.beans.entity.CatalogStatusIssueMovementEntity;
import project.stock.track.app.beans.entity.CatalogTypeCurrencyEntity;
import project.stock.track.app.beans.entity.CatalogTypeMovementEntity;
import project.stock.track.app.beans.entity.CatalogTypeStockEntity;

public class CatalogStockTrackDefinition implements CatalogDefinition {

	@SuppressWarnings("rawtypes")
	Map<String, Class> catalogs = new LinkedHashMap<>();
	
	public CatalogStockTrackDefinition() {
		
		catalogs.put("sector", CatalogSectorEntity.class);
		catalogs.put("broker", CatalogBrokerEntity.class);
		catalogs.put("typeCurrency", CatalogTypeCurrencyEntity.class);
		catalogs.put("typeStock", CatalogTypeStockEntity.class);
		catalogs.put("typeMovement", CatalogTypeMovementEntity.class);
		catalogs.put("statusIssue", CatalogStatusIssueEntity.class);
		catalogs.put("statusIssueMovement", CatalogStatusIssueMovementEntity.class);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Map<String, Class> getCatalogsDefinition() {
		return catalogs;
	}

}
