package project.stock.track.app.beans.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
@Table(name = "issues_manager_track_properties")
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

}
