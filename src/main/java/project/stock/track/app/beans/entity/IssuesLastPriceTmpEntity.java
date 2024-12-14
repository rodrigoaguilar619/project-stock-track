package project.stock.track.app.beans.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
@Table(name = "issues_last_price_tmp")
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
	private LocalDateTime timestamp;
	
	@Column(name = "last_sale_timestamp")
	private LocalDateTime lastSaleTimestamp;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_issue")
	private CatalogIssuesEntity catalogIssuesEntity;

}
