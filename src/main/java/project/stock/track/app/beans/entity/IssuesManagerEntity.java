package project.stock.track.app.beans.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
@Table(name = "issues_manager")
public class IssuesManagerEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private IssuesManagerEntityPk id;
	
	@Column(name = "id_status_issue_quick")
	private Integer idStatusIssueQuick;
	
	@Column(name = "id_status_issue_trading")
	private Integer idStatusIssueTrading;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_issue", insertable = false, updatable = false)
	private CatalogIssuesEntity catalogIssueEntity;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_status_issue_quick", insertable = false, updatable = false)
	private CatalogStatusIssueEntity catalogStatusIssueQuickEntity;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_status_issue_trading", insertable = false, updatable = false)
	private CatalogStatusIssueEntity catalogStatusIssueTradingEntity;
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "issuesManagerEntity")
	private IssuesManagerTrackPropertiesEntity issuesManagerTrackPropertiesEntity;
	
	@OneToMany(mappedBy = "issuesManagerEntity", fetch = FetchType.LAZY)
	private List<TransactionIssueEntity> transactionIssueEntities = new ArrayList<>();
	
	public IssuesManagerEntity() {
		super();
	}

	public IssuesManagerEntity(Integer idIssue, Integer iduser) {
		super();
		IssuesManagerEntityPk pk = new IssuesManagerEntityPk(idIssue, iduser);
		this.id = pk;
	}

}
