package project.stock.track.app.beans.entity;

import java.math.BigDecimal;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(IssuesManagerTrackPropertiesEntity.class)
public abstract class IssuesManagerTrackPropertiesEntity_ {

	public static volatile SingularAttribute<IssuesManagerTrackPropertiesEntity, Boolean> isInvest;
	public static volatile SingularAttribute<IssuesManagerTrackPropertiesEntity, Integer> idIssue;
	public static volatile SingularAttribute<IssuesManagerTrackPropertiesEntity, Integer> idUser;
	public static volatile SingularAttribute<IssuesManagerTrackPropertiesEntity, BigDecimal> trackSellPrice;
	public static volatile SingularAttribute<IssuesManagerTrackPropertiesEntity, BigDecimal> fairValue;
	public static volatile SingularAttribute<IssuesManagerTrackPropertiesEntity, BigDecimal> trackBuyPrice;
	public static volatile SingularAttribute<IssuesManagerTrackPropertiesEntity, IssuesManagerEntity> issuesManagerEntity;

	public static final String IS_INVEST = "isInvest";
	public static final String ID_ISSUE = "idIssue";
	public static final String ID_USER = "idUser";
	public static final String TRACK_SELL_PRICE = "trackSellPrice";
	public static final String FAIR_VALUE = "fairValue";
	public static final String TRACK_BUY_PRICE = "trackBuyPrice";
	public static final String ISSUES_MANAGER_ENTITY = "issuesManagerEntity";

}

