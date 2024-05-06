package project.stock.track.app.beans.entity;

import java.util.Date;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(CatalogIssuesEntity.class)
public abstract class CatalogIssuesEntity_ {

	public static volatile SingularAttribute<CatalogIssuesEntity, Integer> id;
	public static volatile SingularAttribute<CatalogIssuesEntity, String> description;
	public static volatile SingularAttribute<CatalogIssuesEntity, String> initials;
	public static volatile SingularAttribute<CatalogIssuesEntity, Date> historicalStartDate;
	public static volatile SingularAttribute<CatalogIssuesEntity, Boolean> isSp500;
	public static volatile SingularAttribute<CatalogIssuesEntity, Integer> idSector;
	public static volatile SingularAttribute<CatalogIssuesEntity, Integer> idStatusIssue;
	public static volatile SingularAttribute<CatalogIssuesEntity, Integer> idTypeStock;
	public static volatile SingularAttribute<CatalogIssuesEntity, CatalogSectorEntity> catalogSectorEntity;
	public static volatile SingularAttribute<CatalogIssuesEntity, CatalogStatusIssueEntity> catalogStatusIssueEntity;
	public static volatile SingularAttribute<CatalogIssuesEntity, CatalogTypeStockEntity> catalogTypeStockEntity;
	public static volatile SingularAttribute<CatalogIssuesEntity, IssuesLastPriceTmpEntity> tempIssuesLastPriceEntity;
	public static volatile ListAttribute<CatalogIssuesEntity, IssuesHistoricalEntity> issuesHistoricalEntities;

	public static final String ID = "id";
	public static final String DESCRIPTION = "description";
	public static final String INITIALS = "initials";
	public static final String HISTORICAL_START_DATE = "historicalStartDate";
	public static final String IS_SP500 = "isSp500";
	public static final String ID_SECTOR = "idSector";
	public static final String ID_STATUS_ISSUE = "idStatusIssue";
	public static final String ID_TYPE_STOCK = "idTypeStock";
	public static final String CATALOG_SECTOR_ENTITY = "catalogSectorEntity";
	public static final String CATALOG_STATUS_ISSUE_ENTITY = "catalogStatusIssueEntity";
	public static final String CATALOG_TYPE_STOCK_ENTITY = "catalogTypeStockEntity";
	public static final String TEMP_ISSUES_LAST_PRICE_ENTITY = "tempIssuesLastPriceEntity";
	public static final String ISSUES_HISTORICAL_ENTITIES = "issuesHistoricalEntities";

}

