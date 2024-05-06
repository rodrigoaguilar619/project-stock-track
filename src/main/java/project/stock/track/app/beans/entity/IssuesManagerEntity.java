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

@Entity(name = "issues_manager")
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

	public IssuesManagerEntityPk getId() {
		return id;
	}

	public void setId(IssuesManagerEntityPk id) {
		this.id = id;
	}

	public Integer getIdStatusIssueQuick() {
		return idStatusIssueQuick;
	}

	public void setIdStatusIssueQuick(Integer idStatusIssueQuick) {
		this.idStatusIssueQuick = idStatusIssueQuick;
	}
	
	public Integer getIdStatusIssueTrading() {
		return idStatusIssueTrading;
	}

	public void setIdStatusIssueTrading(Integer idStatusIssueTrading) {
		this.idStatusIssueTrading = idStatusIssueTrading;
	}

	public CatalogIssuesEntity getCatalogIssueEntity() {
		return catalogIssueEntity;
	}

	public void setCatalogIssueEntity(CatalogIssuesEntity catalogIssueEntity) {
		this.catalogIssueEntity = catalogIssueEntity;
	}

	public CatalogStatusIssueEntity getCatalogStatusIssueQuickEntity() {
		return catalogStatusIssueQuickEntity;
	}

	public void setCatalogStatusIssueQuickEntity(CatalogStatusIssueEntity catalogStatusIssueQuickEntity) {
		this.catalogStatusIssueQuickEntity = catalogStatusIssueQuickEntity;
	}

	public CatalogStatusIssueEntity getCatalogStatusIssueTradingEntity() {
		return catalogStatusIssueTradingEntity;
	}

	public void setCatalogStatusIssueTradingEntity(CatalogStatusIssueEntity catalogStatusIssueTradingEntity) {
		this.catalogStatusIssueTradingEntity = catalogStatusIssueTradingEntity;
	}

	public IssuesManagerTrackPropertiesEntity getIssuesManagerTrackPropertiesEntity() {
		return issuesManagerTrackPropertiesEntity;
	}

	public void setIssuesManagerTrackPropertiesEntity(IssuesManagerTrackPropertiesEntity issuesManagerTrackPropertiesEntity) {
		this.issuesManagerTrackPropertiesEntity = issuesManagerTrackPropertiesEntity;
	}

	public List<TransactionIssueEntity> getTransactionIssueEntities() {
		return transactionIssueEntities;
	}

	public void setTransactionIssueEntities(List<TransactionIssueEntity> transactionIssueEntities) {
		this.transactionIssueEntities = transactionIssueEntities;
	}

}
