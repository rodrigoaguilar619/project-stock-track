package project.stock.track.app.beans.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity(name = "issues_historical")
public class IssuesHistoricalEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private IssuesHistoricalEntityId issuesHistoricalEntityId;
	
	@Column(name = "open")
	private BigDecimal open;
	
	@Column(name = "close")
	private BigDecimal close;
	
	@Column(name = "high")
	private BigDecimal high;
	
	@Column(name = "low")
	private BigDecimal low;
	
	@Column(name = "volume")
	private BigDecimal volume;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_issue", insertable = false, updatable= false)
	CatalogIssuesEntity catalogIssuesEntity;

	public IssuesHistoricalEntityId getIssuesHistoricalEntityId() {
		return issuesHistoricalEntityId;
	}

	public void setIssuesHistoricalEntityId(IssuesHistoricalEntityId issuesHistoricalEntityId) {
		this.issuesHistoricalEntityId = issuesHistoricalEntityId;
	}

	public BigDecimal getOpen() {
		return open;
	}

	public void setOpen(BigDecimal open) {
		this.open = open;
	}

	public BigDecimal getClose() {
		return close;
	}

	public void setClose(BigDecimal close) {
		this.close = close;
	}

	public BigDecimal getHigh() {
		return high;
	}

	public void setHigh(BigDecimal high) {
		this.high = high;
	}

	public BigDecimal getLow() {
		return low;
	}

	public void setLow(BigDecimal low) {
		this.low = low;
	}

	public BigDecimal getVolume() {
		return volume;
	}

	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}

	public CatalogIssuesEntity getCatalogIssuesEntity() {
		return catalogIssuesEntity;
	}

	public void setCatalogIssuesEntity(CatalogIssuesEntity catalogIssuesEntity) {
		this.catalogIssuesEntity = catalogIssuesEntity;
	}

}
