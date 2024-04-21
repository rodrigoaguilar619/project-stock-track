package project.stock.track.app.beans.pojos.petition.data;

import java.math.BigDecimal;

public class UpdateDollarPriceDataPojo {

	private Long date;
	
	private BigDecimal price;
	
	private boolean isNewRegister;

	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public boolean isNewRegister() {
		return isNewRegister;
	}

	public void setNewRegister(boolean isNewRegister) {
		this.isNewRegister = isNewRegister;
	}
	
}
