package project.stock.track.app.beans.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity(name = "issues_manager_track_properties")
public class IssuesManagerTrackPropertiesEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private IssuesManagerEntityPk id;
	
	@Column(name = "track_buy_price")
	private BigDecimal trackBuyPrice;

	@Column(name = "track_sell_price")
	private BigDecimal trackSellPrice;
	
	@Column(name = "track_fair_value")
	private BigDecimal fairValue;
	
	@Column(name = "is_invest")
	private Boolean isInvest;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_issue", referencedColumnName = "id_issue")
	@JoinColumn(name = "id_user", referencedColumnName = "id_user")
	IssuesManagerEntity issuesManagerEntity;
	
	public IssuesManagerTrackPropertiesEntity() {
		super();
	}

	public IssuesManagerTrackPropertiesEntity(IssuesManagerEntityPk id) {
		super();
		this.id = id;
	}

	public BigDecimal getTrackBuyPrice() {
		return trackBuyPrice;
	}

	public IssuesManagerEntityPk getId() {
		return id;
	}

	public void setId(IssuesManagerEntityPk id) {
		this.id = id;
	}

	public void setTrackBuyPrice(BigDecimal trackBuyPrice) {
		this.trackBuyPrice = trackBuyPrice;
	}

	public BigDecimal getTrackSellPrice() {
		return trackSellPrice;
	}

	public void setTrackSellPrice(BigDecimal trackSellPrice) {
		this.trackSellPrice = trackSellPrice;
	}

	public BigDecimal getFairValue() {
		return fairValue;
	}

	public void setFairValue(BigDecimal fairValue) {
		this.fairValue = fairValue;
	}

	public Boolean getIsInvest() {
		return isInvest;
	}

	public void setIsInvest(Boolean isInvest) {
		this.isInvest = isInvest;
	}

	public IssuesManagerEntity getIssuesManagerEntity() {
		return issuesManagerEntity;
	}

	public void setIssuesManagerEntity(IssuesManagerEntity issuesManagerEntity) {
		this.issuesManagerEntity = issuesManagerEntity;
	}

}
