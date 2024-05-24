package project.stock.track.app.beans.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


/**
 * The persistent class for the movements_issue_buy database table.
 * 
 */
@Entity
@Table(name="issues_movements_buy")
public class IssuesMovementsBuyEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private IssuesMovementsBuyEntityPk id;

	@Column(name="buy_price")
	private BigDecimal buyPrice;
	
	@Column(name = "buy_date")
	private Date buyDate;

	@Column(name="sell_price")
	private BigDecimal sellPrice;
	
	@Column(name = "sell_date")
	private Date sellDate;
	
	@Column(name="total_shares")
	private BigDecimal totalShares;

	//bi-directional many-to-one association to IssuesMovements
	@ManyToOne
	@JoinColumn(name="id_issue_movement", insertable=false, updatable=false)
	private IssuesMovementsEntity issuesMovementsEntity;

	public IssuesMovementsBuyEntityPk getId() {
		return this.id;
	}

	public void setId(IssuesMovementsBuyEntityPk id) {
		this.id = id;
	}

	public BigDecimal getBuyPrice() {
		return buyPrice;
	}

	public void setBuyPrice(BigDecimal buyPrice) {
		this.buyPrice = buyPrice;
	}

	public Date getBuyDate() {
		return buyDate;
	}

	public void setBuyDate(Date buyDate) {
		this.buyDate = buyDate;
	}

	public BigDecimal getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(BigDecimal sellPrice) {
		this.sellPrice = sellPrice;
	}

	public Date getSellDate() {
		return sellDate;
	}

	public void setSellDate(Date sellDate) {
		this.sellDate = sellDate;
	}

	public IssuesMovementsEntity getIssuesMovementsEntity() {
		return issuesMovementsEntity;
	}

	public void setIssuesMovementsEntity(IssuesMovementsEntity issueMovementsEntity) {
		this.issuesMovementsEntity = issueMovementsEntity;
	}

	public BigDecimal getTotalShares() {
		return totalShares;
	}

	public void setTotalShares(BigDecimal totalShares) {
		this.totalShares = totalShares;
	}
}