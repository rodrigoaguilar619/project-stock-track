package project.stock.track.app.beans.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
@Table(name = "catalog_issues")
public class CatalogIssuesEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	protected Integer id;
	
	protected String description;
	
	private String initials;
	
	@Column(name = "id_status_issue")
	private Integer idStatusIssue;
	
	@Column(name = "id_type_stock")
	private Integer idTypeStock;

	@Column(name = "id_sector")
	private Integer idSector;
	
	@Column(name = "id_index")
	private Integer idIndex;
	
	@Column(name = "historical_start_date")
	private LocalDateTime historicalStartDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_sector", insertable = false, updatable = false)
	private CatalogSectorEntity catalogSectorEntity;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_status_issue", insertable = false, updatable = false)
	private CatalogStatusIssueEntity catalogStatusIssueEntity;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_type_stock", insertable = false, updatable = false)
	private CatalogTypeStockEntity catalogTypeStockEntity;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_index", insertable = false, updatable = false)
	private CatalogIndexEntity catalogIndexEntity;
	
	@OneToMany(mappedBy = "catalogIssuesEntity", fetch = FetchType.LAZY)
	private List<IssuesHistoricalEntity> issuesHistoricalEntities = new ArrayList<>();
	
	@OneToMany(mappedBy = "catalogIssuesEntity", fetch = FetchType.LAZY)
	private List<IssuesHistoricalFairValueEntity> issuesHistoricalFairValueEntities = new ArrayList<>();
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "catalogIssuesEntity")
	IssuesLastPriceTmpEntity tempIssuesLastPriceEntity;
	
	public CatalogIssuesEntity() {
	}
	
	public CatalogIssuesEntity(Integer id) {
		this.id = id;
	}

}
