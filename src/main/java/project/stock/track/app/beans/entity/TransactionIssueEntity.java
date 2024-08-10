package project.stock.track.app.beans.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter; 

@Getter @Setter
@Entity
@Table(name = "transaction_issue")
public class TransactionIssueEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "id_issue")
	private Integer idIssue;
	
	@Column(name = "id_user")
	private Integer idUser;
	
	@Column(name = "id_date")
	private Date idDate;
	
	@Column(name = "price_buy")
	private BigDecimal priceBuy;
	
	@Column(name = "commision_percentage")
	private BigDecimal commisionPercentage;
	
	@Column(name = "price_total_buy")
	private BigDecimal priceTotalBuy;
	
	@Column(name = "price_sell")
	private BigDecimal priceSell;
	
	@Column(name = "price_total_sell")
	private BigDecimal priceTotalSell;
	
	@Column(name = "sell_commision_percentage")
	private BigDecimal sellCommisionPercentage;
	
	@Column(name = "sell_taxes_percentage")
	private BigDecimal sellTaxesPercentage;
	
	@Column(name = "sell_gain_loss_percentage")
	private BigDecimal sellGainLossPercentage;
	
	@Column(name = "sell_gain_loss_total")
	private BigDecimal sellGainLossTotal;
	
	@Column(name = "sell_date")
	private Date sellDate;
	
	@Column(name = "id_broker")
	private Integer idBroker;
	
	@Column(name = "total_shares")
	private BigDecimal totalShares;
	
	@Column(name = "is_slice")
	private Boolean isSlice;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_issue", referencedColumnName = "id_issue", insertable = false, updatable = false)
	@JoinColumn(name = "id_user", referencedColumnName = "id_user", insertable = false, updatable = false)
	private IssuesManagerEntity issuesManagerEntity;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_issue", foreignKey = @ForeignKey(name = "none"), insertable = false, updatable= false)
	IssuesLastPriceTmpEntity issuesLastPriceTmpEntity;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_broker", insertable = false, updatable= false)
	private CatalogBrokerEntity catalogBrokerEntity;

}
