package project.stock.track.app.beans.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Getter @Setter
@Entity
@Table(name="issues_movements_buy")
public class IssuesMovementsBuyEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private IssuesMovementsBuyEntityPk id;

	@Column(name="buy_price")
	private BigDecimal buyPrice;
	
	@Column(name="buy_price_mxn")
	private BigDecimal buyPriceMxn;
	
	@Column(name = "buy_date")
	private LocalDateTime buyDate;

	@Column(name="sell_price")
	private BigDecimal sellPrice;
	
	@Column(name="sell_price_mxn")
	private BigDecimal sellPriceMxn;
	
	@Column(name = "sell_date")
	private LocalDateTime sellDate;
	
	@Column(name="total_shares")
	private BigDecimal totalShares;

	//bi-directional many-to-one association to IssuesMovements
	@ManyToOne
	@JoinColumn(name="id_issue_movement", insertable=false, updatable=false)
	private IssuesMovementsEntity issuesMovementsEntity;

}