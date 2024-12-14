package project.stock.track.app.beans.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(IssuesLastPriceTmpEntity.class)
public abstract class IssuesLastPriceTmpEntity_ {

	public static SingularAttribute<IssuesLastPriceTmpEntity, BigDecimal> volume;
	public static SingularAttribute<IssuesLastPriceTmpEntity, LocalDateTime> lastSaleTimestamp;
	public static SingularAttribute<IssuesLastPriceTmpEntity, BigDecimal> high;
	public static SingularAttribute<IssuesLastPriceTmpEntity, Integer> idIssue;
	public static SingularAttribute<IssuesLastPriceTmpEntity, BigDecimal> last;
	public static SingularAttribute<IssuesLastPriceTmpEntity, BigDecimal> open;
	public static SingularAttribute<IssuesLastPriceTmpEntity, BigDecimal> prevClose;
	public static SingularAttribute<IssuesLastPriceTmpEntity, LocalDateTime> timestamp;
	public static SingularAttribute<IssuesLastPriceTmpEntity, CatalogIssuesEntity> catalogIssuesEntity;

	public static final String VOLUME = "volume";
	public static final String LAST_SALE_TIMESTAMP = "lastSaleTimestamp";
	public static final String HIGH = "high";
	public static final String ID_ISSUE = "idIssue";
	public static final String LAST = "last";
	public static final String OPEN = "open";
	public static final String PREV_CLOSE = "prevClose";
	public static final String TIMESTAMP = "timestamp";
	public static final String CATALOG_ISSUES_ENTITY = "catalogIssuesEntity";

}

