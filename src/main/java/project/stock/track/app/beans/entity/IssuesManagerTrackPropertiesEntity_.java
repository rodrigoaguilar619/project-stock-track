package project.stock.track.app.beans.entity;

import java.math.BigDecimal;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(IssuesManagerTrackPropertiesEntity.class)
public abstract class IssuesManagerTrackPropertiesEntity_ {

	public static SingularAttribute<IssuesManagerTrackPropertiesEntity, IssuesManagerEntityPk> id;
	public static SingularAttribute<IssuesManagerTrackPropertiesEntity, Boolean> isInvest;
	public static SingularAttribute<IssuesManagerTrackPropertiesEntity, Integer> idIssue;
	public static SingularAttribute<IssuesManagerTrackPropertiesEntity, Integer> idUser;
	public static SingularAttribute<IssuesManagerTrackPropertiesEntity, BigDecimal> trackSellPrice;
	public static SingularAttribute<IssuesManagerTrackPropertiesEntity, BigDecimal> fairValue;
	public static SingularAttribute<IssuesManagerTrackPropertiesEntity, BigDecimal> trackBuyPrice;
	public static SingularAttribute<IssuesManagerTrackPropertiesEntity, IssuesManagerEntity> issuesManagerEntity;

	public static final String ID = "id";
	public static final String IS_INVEST = "isInvest";
	public static final String ID_ISSUE = "idIssue";
	public static final String ID_USER = "idUser";
	public static final String TRACK_SELL_PRICE = "trackSellPrice";
	public static final String FAIR_VALUE = "fairValue";
	public static final String TRACK_BUY_PRICE = "trackBuyPrice";
	public static final String ISSUES_MANAGER_ENTITY = "issuesManagerEntity";

}

