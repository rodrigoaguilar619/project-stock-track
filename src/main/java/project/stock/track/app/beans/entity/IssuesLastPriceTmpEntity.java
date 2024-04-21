package project.stock.track.app.beans.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity(name = "issues_last_price_tmp")
public class IssuesLastPriceTmpEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id_issue")
	private Integer idIssue;
	
	@Column(name = "last")
	private BigDecimal last;
	
	@Column(name = "open")
	private BigDecimal open;
	
	@Column(name = "volume")
	private BigDecimal volume;
	
	@Column(name = "prevClose")
	private BigDecimal prevClose;
	
	@Column(name = "high")
	private BigDecimal high;
	
	@Column(name = "timestamp")
	private Date timestamp;
	
	@Column(name = "last_sale_timestamp")
	private Date lastSaleTimestamp;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_issue")
	private CatalogIssuesEntity catalogIssuesEntity;

	public Integer getIdIssue() {
		return idIssue;
	}

	public void setIdIssue(Integer idIssue) {
		this.idIssue = idIssue;
	}

	public BigDecimal getLast() {
		return last;
	}

	public void setLast(BigDecimal last) {
		this.last = last;
	}

	public BigDecimal getOpen() {
		return open;
	}

	public void setOpen(BigDecimal open) {
		this.open = open;
	}

	public BigDecimal getVolume() {
		return volume;
	}

	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}

	public BigDecimal getPrevClose() {
		return prevClose;
	}

	public void setPrevClose(BigDecimal prevClose) {
		this.prevClose = prevClose;
	}

	public BigDecimal getHigh() {
		return high;
	}

	public void setHigh(BigDecimal high) {
		this.high = high;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public Date getLastSaleTimestamp() {
		return lastSaleTimestamp;
	}

	public void setLastSaleTimestamp(Date lastSaleTimestamp) {
		this.lastSaleTimestamp = lastSaleTimestamp;
	}

	public CatalogIssuesEntity getCatalogIssuesEntity() {
		return catalogIssuesEntity;
	}

	public void setCatalogIssuesEntity(CatalogIssuesEntity catalogIssuesEntity) {
		this.catalogIssuesEntity = catalogIssuesEntity;
	}
}
