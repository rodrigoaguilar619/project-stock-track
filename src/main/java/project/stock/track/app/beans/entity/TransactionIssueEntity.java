package project.stock.track.app.beans.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Table(name = "transaction_issue")
@Entity
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
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_issue", referencedColumnName = "id_issue", insertable = false, updatable = false)
	@JoinColumn(name = "id_user", referencedColumnName = "id_user", insertable = false, updatable = false)
	private IssuesManagerEntity issuesManagerEntity;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_issue", insertable = false, updatable= false)
	IssuesLastPriceTmpEntity issuesLastPriceTmpEntity;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_broker", insertable = false, updatable= false)
	private CatalogBrokerEntity catalogBrokerEntity;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getIdIssue() {
		return idIssue;
	}

	public void setIdIssue(Integer idIssue) {
		this.idIssue = idIssue;
	}

	public Date getIdDate() {
		return idDate;
	}

	public void setIdDate(Date idDate) {
		this.idDate = idDate;
	}

	public BigDecimal getPriceBuy() {
		return priceBuy;
	}

	public void setPriceBuy(BigDecimal priceBuy) {
		this.priceBuy = priceBuy;
	}

	public BigDecimal getCommisionPercentage() {
		return commisionPercentage;
	}

	public void setCommisionPercentage(BigDecimal commisionPercentage) {
		this.commisionPercentage = commisionPercentage;
	}

	public BigDecimal getPriceTotalBuy() {
		return priceTotalBuy;
	}

	public void setPriceTotalBuy(BigDecimal priceTotalBuy) {
		this.priceTotalBuy = priceTotalBuy;
	}

	public BigDecimal getPriceSell() {
		return priceSell;
	}

	public void setPriceSell(BigDecimal priceSell) {
		this.priceSell = priceSell;
	}

	public BigDecimal getPriceTotalSell() {
		return priceTotalSell;
	}

	public void setPriceTotalSell(BigDecimal priceTotalSell) {
		this.priceTotalSell = priceTotalSell;
	}

	public BigDecimal getSellCommisionPercentage() {
		return sellCommisionPercentage;
	}

	public void setSellCommisionPercentage(BigDecimal sellCommisionPercentage) {
		this.sellCommisionPercentage = sellCommisionPercentage;
	}

	public BigDecimal getSellTaxesPercentage() {
		return sellTaxesPercentage;
	}

	public void setSellTaxesPercentage(BigDecimal sellTaxesPercentage) {
		this.sellTaxesPercentage = sellTaxesPercentage;
	}

	public BigDecimal getSellGainLossPercentage() {
		return sellGainLossPercentage;
	}

	public void setSellGainLossPercentage(BigDecimal sellGainLossPercentage) {
		this.sellGainLossPercentage = sellGainLossPercentage;
	}

	public BigDecimal getSellGainLossTotal() {
		return sellGainLossTotal;
	}

	public void setSellGainLossTotal(BigDecimal sellGainLossTotal) {
		this.sellGainLossTotal = sellGainLossTotal;
	}

	public Date getSellDate() {
		return sellDate;
	}

	public void setSellDate(Date sellDate) {
		this.sellDate = sellDate;
	}

	public CatalogBrokerEntity getCatalogBrokerEntity() {
		return catalogBrokerEntity;
	}

	public void setCatalogBrokerEntity(CatalogBrokerEntity catalogBrokerEntity) {
		this.catalogBrokerEntity = catalogBrokerEntity;
	}

	public Integer getIdBroker() {
		return idBroker;
	}

	public void setIdBroker(Integer idBroker) {
		this.idBroker = idBroker;
	}

	public Integer getIdUser() {
		return idUser;
	}

	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
	}

	public IssuesManagerEntity getIssuesManagerEntity() {
		return issuesManagerEntity;
	}

	public void setIssuesManagerEntity(IssuesManagerEntity issuesManagerEntity) {
		this.issuesManagerEntity = issuesManagerEntity;
	}

	public IssuesLastPriceTmpEntity getIssuesLastPriceTmpEntity() {
		return issuesLastPriceTmpEntity;
	}

	public void setIssuesLastPriceTmpEntity(IssuesLastPriceTmpEntity issuesLastPriceTmpEntity) {
		this.issuesLastPriceTmpEntity = issuesLastPriceTmpEntity;
	}
}
