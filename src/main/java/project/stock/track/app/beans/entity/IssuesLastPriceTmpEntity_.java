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

}

