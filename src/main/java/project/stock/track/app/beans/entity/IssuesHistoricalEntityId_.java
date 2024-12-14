package project.stock.track.app.beans.entity;

import java.time.LocalDateTime;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(IssuesHistoricalEntityId.class)
public abstract class IssuesHistoricalEntityId_ {

	public static SingularAttribute<IssuesHistoricalEntityId, Integer> idIssue;
	public static SingularAttribute<IssuesHistoricalEntityId, LocalDateTime> idDate;

	public static final String ID_ISSUE = "idIssue";
	public static final String ID_DATE = "idDate";

}

