package project.stock.track.app.beans.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity(name = "dollar_historical_price")
public class DollarHistoricalPriceEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id_date")
	private Date idDate;
	
	@Column(name = "price")
	private BigDecimal price;

	public Date getIdDate() {
		return idDate;
	}

	public void setIdDate(Date idDate) {
		this.idDate = idDate;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

}
