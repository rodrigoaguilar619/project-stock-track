package project.stock.track.app.beans.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
@Table(name="issues_movements")
public class IssuesMovementsEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="id_issue")
	private int idIssue;

	@Column(name="id_user")
	private int idUser;

	@Column(name="id_status")
	private Integer idStatus;
	
	@Column(name="id_broker")
	private Integer idBroker;
	
	@Column(name="price_movement")
	private BigDecimal priceMovement;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_issue", referencedColumnName = "id_issue", insertable = false, updatable = false)
	@JoinColumn(name = "id_user", referencedColumnName = "id_user", insertable = false, updatable = false)
	private IssuesManagerEntity issuesManagerEntity;
	
	@ManyToOne
	@JoinColumn(name="id_broker", insertable = false, updatable = false)
	private CatalogBrokerEntity catalogBrokerEntity;
	
	@ManyToOne
	@JoinColumn(name = "id_status", insertable = false, updatable = false)
	private CatalogStatusIssueMovementEntity catalogStatusIssueMovementEntity;

	//bi-directional many-to-one association to MovementsIssueBuy
	@OneToMany(mappedBy="issuesMovementsEntity")
	private List<IssuesMovementsBuyEntity> issuesMovementsBuys = new ArrayList<>();

}