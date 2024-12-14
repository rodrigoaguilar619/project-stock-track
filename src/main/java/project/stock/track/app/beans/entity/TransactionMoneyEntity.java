package project.stock.track.app.beans.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lib.base.backend.modules.security.jwt.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity(name = "transaction_money")
public class TransactionMoneyEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "amount")
	private BigDecimal amount;
	
	@Column(name = "id_broker")
	private Integer idBroker;
	
	@Column(name = "id_user")
	private Integer idUser;
	
	@Column(name = "id_issue")
	private Integer idIssue;
	
	@Column(name = "amount_mxn")
	private BigDecimal amountMxn;
	
	@Column(name = "id_type_movement")
	private Integer idTypeMovement;
	
	@Column(name = "date_transaction")
	private LocalDateTime dateTransaction;
	
	@Column(name = "information")
	private String information;
	
	@ManyToOne
	@JoinColumn(name = "id_broker", insertable=false, updatable=false)
	private CatalogBrokerEntity catalogBrokerEntity;
	
	@ManyToOne
	@JoinColumn(name = "id_user", insertable=false, updatable=false)
	private UserEntity userEntity;
	
	@ManyToOne
	@JoinColumn(name = "id_type_movement", insertable=false, updatable=false)
	private CatalogTypeMovementEntity catalogTypeMovementEntity;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_issue", insertable = false, updatable = false)
	private CatalogIssuesEntity catalogIssueEntity;
	
}
