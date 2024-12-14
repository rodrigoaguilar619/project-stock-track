package project.stock.track.app.utils;

import lib.base.backend.utils.date.DateUtil;
import project.stock.track.app.beans.entity.CatalogIssuesEntity;
import project.stock.track.app.beans.pojos.entity.CatalogIssuesEntityPojo;

public class MapPojoToEntityUtil {
	
	private DateUtil dateUtil = new DateUtil();

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
		catalogIssuesEntity.setHistoricalStartDate(dateUtil.getLocalDateTime(catalogIssuesEntityPojo.getHistoricalStartDate()));
		
		return catalogIssuesEntity;

	}
}
