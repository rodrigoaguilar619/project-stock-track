package project.stock.track.app.beans.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
@Table(name = "issues_historical_fair_value")
public class IssuesHistoricalFairValueEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private IssuesHistoricalFairValueEntityId issuesHistoricalFairValueEntityId;
	
	@Column(name = "fair_value")
	private BigDecimal fairValue;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_issue", insertable = false, updatable= false)
	CatalogIssuesEntity catalogIssuesEntity;

}
