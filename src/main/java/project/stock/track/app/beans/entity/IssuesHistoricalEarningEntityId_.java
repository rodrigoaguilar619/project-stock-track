package project.stock.track.app.beans.entity;

import java.time.LocalDate;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(IssuesHistoricalEarningEntityId.class)
public abstract class IssuesHistoricalEarningEntityId_ {

	public static SingularAttribute<IssuesHistoricalEarningEntityId, Integer> idIssue;
	public static SingularAttribute<IssuesHistoricalEarningEntityId, LocalDate> idDate;

	public static final String ID_ISSUE = "idIssue";
	public static final String ID_DATE = "idDate";

}

