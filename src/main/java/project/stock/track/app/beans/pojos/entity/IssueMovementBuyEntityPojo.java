package project.stock.track.app.beans.pojos.entity;

import java.math.BigDecimal;

public class IssueMovementBuyEntityPojo {

	private Integer buyTransactionNumber;
	
	private BigDecimal buyPrice;
	
	private Long buyDate;
	
	private BigDecimal sellPrice;
	
	private Long sellDate;
	
	private BigDecimal totalShares;

	public Integer getBuyTransactionNumber() {
		return buyTransactionNumber;
	}

	public void setBuyTransactionNumber(Integer buyTransactionNumber) {
		this.buyTransactionNumber = buyTransactionNumber;
	}

	public BigDecimal getBuyPrice() {
		return buyPrice;
	}

	public void setBuyPrice(BigDecimal buyPrice) {
		this.buyPrice = buyPrice;
	}

	public Long getBuyDate() {
		return buyDate;
	}

	public void setBuyDate(Long buyDate) {
		this.buyDate = buyDate;
	}

	public BigDecimal getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(BigDecimal sellPrice) {
		this.sellPrice = sellPrice;
	}

	public Long getSellDate() {
		return sellDate;
	}

	public void setSellDate(Long sellDate) {
		this.sellDate = sellDate;
	}

	public BigDecimal getTotalShares() {
		return totalShares;
	}

	public void setTotalShares(BigDecimal totalShares) {
		this.totalShares = totalShares;
	}
}
