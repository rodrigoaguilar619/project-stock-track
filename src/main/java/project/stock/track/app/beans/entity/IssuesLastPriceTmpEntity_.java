package project.stock.track.app.beans.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(IssuesLastPriceTmpEntity.class)
public abstract class IssuesLastPriceTmpEntity_ {

	public static volatile SingularAttribute<IssuesLastPriceTmpEntity, BigDecimal> volume;
	public static volatile SingularAttribute<IssuesLastPriceTmpEntity, Date> lastSaleTimestamp;
	public static volatile SingularAttribute<IssuesLastPriceTmpEntity, BigDecimal> high;
	public static volatile SingularAttribute<IssuesLastPriceTmpEntity, Integer> idIssue;
	public static volatile SingularAttribute<IssuesLastPriceTmpEntity, BigDecimal> last;
	public static volatile SingularAttribute<IssuesLastPriceTmpEntity, BigDecimal> open;
	public static volatile SingularAttribute<IssuesLastPriceTmpEntity, BigDecimal> prevClose;
	public static volatile SingularAttribute<IssuesLastPriceTmpEntity, Date> timestamp;
	public static volatile SingularAttribute<IssuesLastPriceTmpEntity, CatalogIssuesEntity> catalogIssuesEntity;

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

