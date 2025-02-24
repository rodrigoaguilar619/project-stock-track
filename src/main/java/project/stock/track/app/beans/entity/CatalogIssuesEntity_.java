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

}

