package project.stock.track.app.beans.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import lib.base.backend.modules.security.jwt.entity.UserEntity;

@Entity(name = "transaction_money")
public class TransactionMoneyEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "id_type_currency")
	private Integer idTypeCurrency;
	
	@Column(name = "amount")
	private BigDecimal amount;
	
	@Column(name = "id_broker")
	private Integer idBroker;
	
	@Column(name = "id_user")
	private Integer idUser;
	
	@Column(name = "value_mxn")
	private BigDecimal valueMxn;
	
	@Column(name = "id_type_movement")
	private Integer idTypeMovement;
	
	@Column(name = "date_transaction")
	private Date dateTransaction;
	
	@ManyToOne
	@JoinColumn(name = "id_broker", insertable=false, updatable=false)
	private CatalogBrokerEntity catalogBrokerEntity;
	
	@ManyToOne
	@JoinColumn(name = "id_user", insertable=false, updatable=false)
	private UserEntity userEntity;
	
	@ManyToOne
	@JoinColumn(name = "id_type_movement", insertable=false, updatable=false)
	private CatalogTypeMovementEntity catalogTypeMovementEntity;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getIdTypeCurrency() {
		return idTypeCurrency;
	}

	public void setIdTypeCurrency(Integer idTypeCurrency) {
		this.idTypeCurrency = idTypeCurrency;
	}

	public void setIdBroker(Integer idBroker) {
		this.idBroker = idBroker;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Integer getIdBroker() {
		return idBroker;
	}

	public BigDecimal getValueMxn() {
		return valueMxn;
	}

	public void setValueMxn(BigDecimal valueMxn) {
		this.valueMxn = valueMxn;
	}

	public Integer getIdTypeMovement() {
		return idTypeMovement;
	}

	public void setIdTypeMovement(Integer idTypeMovement) {
		this.idTypeMovement = idTypeMovement;
	}

	public CatalogTypeMovementEntity getCatalogTypeMovementEntity() {
		return catalogTypeMovementEntity;
	}

	public void setCatalogTypeMovementEntity(CatalogTypeMovementEntity catalogTypeMovementEntity) {
		this.catalogTypeMovementEntity = catalogTypeMovementEntity;
	}

	public Date getDateTransaction() {
		return dateTransaction;
	}

	public void setDateTransaction(Date dateTransaction) {
		this.dateTransaction = dateTransaction;
	}

	public CatalogBrokerEntity getCatalogBrokerEntity() {
		return catalogBrokerEntity;
	}

	public void setCatalogBrokerEntity(CatalogBrokerEntity catalogBrokerEntity) {
		this.catalogBrokerEntity = catalogBrokerEntity;
	}
	
}
