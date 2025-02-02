package project.stock.track.app.beans.entity;

import java.time.LocalDateTime;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(CatalogIssuesEntity.class)
public abstract class CatalogIssuesEntity_ {

	public static SingularAttribute<CatalogIssuesEntity, Integer> id;
	public static SingularAttribute<CatalogIssuesEntity, String> description;
	public static SingularAttribute<CatalogIssuesEntity, String> initials;
	public static SingularAttribute<CatalogIssuesEntity, LocalDateTime> historicalStartDate;
	public static SingularAttribute<CatalogIssuesEntity, Integer> idSector;
	public static SingularAttribute<CatalogIssuesEntity, Integer> idStatusIssue;
	public static SingularAttribute<CatalogIssuesEntity, Integer> idTypeStock;
	public static SingularAttribute<CatalogIssuesEntity, Integer> idIndex;
	public static SingularAttribute<CatalogIssuesEntity, CatalogSectorEntity> catalogSectorEntity;
	public static SingularAttribute<CatalogIssuesEntity, CatalogStatusIssueEntity> catalogStatusIssueEntity;
	public static SingularAttribute<CatalogIssuesEntity, CatalogTypeStockEntity> catalogTypeStockEntity;
	public static SingularAttribute<CatalogIssuesEntity, CatalogIndexEntity> catalogIndexEntity;
	public static SingularAttribute<CatalogIssuesEntity, IssuesLastPriceTmpEntity> tempIssuesLastPriceEntity;
	public static ListAttribute<CatalogIssuesEntity, IssuesHistoricalEntity> issuesHistoricalEntities;

	public static final String ID = "id";
	public static final String DESCRIPTION = "description";
	public static final String INITIALS = "initials";
	public static final String HISTORICAL_START_DATE = "historicalStartDate";
	public static final String ID_SECTOR = "idSector";
	public static final String ID_STATUS_ISSUE = "idStatusIssue";
	public static final String ID_TYPE_STOCK = "idTypeStock";
	public static final String ID_INDEX = "idIndex";
	public static final String CATALOG_SECTOR_ENTITY = "catalogSectorEntity";
	public static final String CATALOG_STATUS_ISSUE_ENTITY = "catalogStatusIssueEntity";
	public static final String CATALOG_TYPE_STOCK_ENTITY = "catalogTypeStockEntity";
	public static final String CATALOG_INDEX_ENTITY = "catalogIndexEntity";
	public static final String TEMP_ISSUES_LAST_PRICE_ENTITY = "tempIssuesLastPriceEntity";
	public static final String ISSUES_HISTORICAL_ENTITIES = "issuesHistoricalEntities";

}

