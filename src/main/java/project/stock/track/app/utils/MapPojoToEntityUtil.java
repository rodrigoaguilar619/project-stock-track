package project.stock.track.app.utils;

import java.util.Date;

import project.stock.track.app.beans.entity.CatalogIssuesEntity;
import project.stock.track.app.beans.pojos.entity.CatalogIssuesEntityPojo;

public class MapPojoToEntityUtil {

	public CatalogIssuesEntity mapCatalogIssue(CatalogIssuesEntityPojo catalogIssuesEntityPojo, CatalogIssuesEntity catalogIssuesEntity) {

		if (catalogIssuesEntity == null) {
			catalogIssuesEntity = new CatalogIssuesEntity();
		}
		
		catalogIssuesEntity.setDescription(catalogIssuesEntityPojo.getDescription());
		catalogIssuesEntity.setIdSector(catalogIssuesEntityPojo.getIdSector());
		catalogIssuesEntity.setInitials(catalogIssuesEntityPojo.getInitials());
		catalogIssuesEntity.setIdStatusIssue(catalogIssuesEntityPojo.getIdStatusIssue());
		catalogIssuesEntity.setIdTypeStock(catalogIssuesEntityPojo.getIdTypeStock());
		catalogIssuesEntity.setIsSp500(catalogIssuesEntityPojo.getIsSp500());
		catalogIssuesEntity.setHistoricalStartDate(catalogIssuesEntityPojo.getHistoricalStartDate() != null ? new Date(catalogIssuesEntityPojo.getHistoricalStartDate()) : null);
		
		return catalogIssuesEntity;

	}
}
