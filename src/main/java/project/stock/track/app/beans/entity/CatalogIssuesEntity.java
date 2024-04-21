package project.stock.track.app.beans.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity(name = "catalog_issues")
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
	
	@Column(name = "is_sp_500")
	private Boolean isSp500;
	
	@Column(name = "historical_start_date")
	private Date historicalStartDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_sector", insertable = false, updatable = false)
	private CatalogSectorEntity catalogSectorEntity;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_status_issue", insertable = false, updatable = false)
	private CatalogStatusIssueEntity catalogStatusIssueEntity;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_type_stock", insertable = false, updatable = false)
	private CatalogTypeStockEntity catalogTypeStockEntity;
	
	@OneToMany(mappedBy = "catalogIssuesEntity", fetch = FetchType.LAZY)
	private List<IssuesHistoricalEntity> issuesHistoricalEntities = new ArrayList<>();
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "catalogIssuesEntity")
	IssuesLastPriceTmpEntity tempIssuesLastPriceEntity;
	
	public CatalogIssuesEntity() {
	}
	
	public CatalogIssuesEntity(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getInitials() {
		return initials;
	}

	public void setInitials(String initials) {
		this.initials = initials;
	}

	public Integer getIdStatusIssue() {
		return idStatusIssue;
	}

	public void setIdStatusIssue(Integer idStatusIssue) {
		this.idStatusIssue = idStatusIssue;
	}

	public Integer getIdTypeStock() {
		return idTypeStock;
	}

	public void setIdTypeStock(Integer idTypeStock) {
		this.idTypeStock = idTypeStock;
	}

	public Integer getIdSector() {
		return idSector;
	}

	public void setIdSector(Integer idSector) {
		this.idSector = idSector;
	}
	
	public Boolean getIsSp500() {
		return isSp500;
	}

	public void setIsSp500(Boolean isSp500) {
		this.isSp500 = isSp500;
	}

	public Date getHistoricalStartDate() {
		return historicalStartDate;
	}

	public void setHistoricalStartDate(Date historicalStartDate) {
		this.historicalStartDate = historicalStartDate;
	}

	public CatalogSectorEntity getCatalogSectorEntity() {
		return catalogSectorEntity;
	}

	public void setCatalogSectorEntity(CatalogSectorEntity catalogSectorEntity) {
		this.catalogSectorEntity = catalogSectorEntity;
	}

	public CatalogStatusIssueEntity getCatalogStatusIssueEntity() {
		return catalogStatusIssueEntity;
	}

	public void setCatalogStatusIssueEntity(CatalogStatusIssueEntity catalogStatusIssueEntity) {
		this.catalogStatusIssueEntity = catalogStatusIssueEntity;
	}

	public CatalogTypeStockEntity getCatalogTypeStockEntity() {
		return catalogTypeStockEntity;
	}

	public void setCatalogTypeStockEntity(CatalogTypeStockEntity catalogTypeStockEntity) {
		this.catalogTypeStockEntity = catalogTypeStockEntity;
	}

	public List<IssuesHistoricalEntity> getIssuesHistoricalEntities() {
		return issuesHistoricalEntities;
	}

	public void setIssuesHistoricalEntities(List<IssuesHistoricalEntity> issuesHistoricalEntities) {
		this.issuesHistoricalEntities = issuesHistoricalEntities;
	}
	
	public IssuesLastPriceTmpEntity getTempIssuesLastPriceEntity() {
		return tempIssuesLastPriceEntity;
	}

	public void setTempIssuesLastPriceEntity(IssuesLastPriceTmpEntity tempIssuesLastPriceEntity) {
		this.tempIssuesLastPriceEntity = tempIssuesLastPriceEntity;
	}

}
