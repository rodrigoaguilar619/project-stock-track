package project.stock.track.app.beans.pojos.business.transaction;

import java.math.BigDecimal;

public class TransactionIssuePojo {
	
	private int id;
	
	private Integer idIssue;
	
	private String issue;
	
	private Long buyDate;
	
	private BigDecimal priceBuy;
	
	private BigDecimal commisionPercentage;
	
	private BigDecimal priceTotalBuy;
	
	private BigDecimal priceSell;
	
	private BigDecimal priceTotalSell;
	
	private BigDecimal sellGainLossPercentage;
	
	private BigDecimal sellGainLossTotal;
	
	private BigDecimal sellCommisionPercentage;
	
	private BigDecimal sellTaxesPercentage;
	
	private Long sellDate;
	
	private Integer typeCurrency;
	
	private String descriptionTypeCurrency;

	public Long getBuyDate() {
		return buyDate;
	}

	public void setBuyDate(Long buyDate) {
		this.buyDate = buyDate;
	}

	public BigDecimal getPriceBuy() {
		return priceBuy;
	}

	public void setPriceBuy(BigDecimal priceBuy) {
		this.priceBuy = priceBuy;
	}

	public BigDecimal getCommisionPercentage() {
		return commisionPercentage;
	}

	public void setCommisionPercentage(BigDecimal commisionPercentage) {
		this.commisionPercentage = commisionPercentage;
	}

	public BigDecimal getPriceTotalBuy() {
		return priceTotalBuy;
	}

	public void setPriceTotalBuy(BigDecimal priceTotalBuy) {
		this.priceTotalBuy = priceTotalBuy;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BigDecimal getPriceSell() {
		return priceSell;
	}

	public void setPriceSell(BigDecimal priceSell) {
		this.priceSell = priceSell;
	}

	public BigDecimal getPriceTotalSell() {
		return priceTotalSell;
	}

	public void setPriceTotalSell(BigDecimal priceTotalSell) {
		this.priceTotalSell = priceTotalSell;
	}

	public BigDecimal getSellGainLossPercentage() {
		return sellGainLossPercentage;
	}

	public void setSellGainLossPercentage(BigDecimal sellGainLossPercentage) {
		this.sellGainLossPercentage = sellGainLossPercentage;
	}

	public BigDecimal getSellGainLossTotal() {
		return sellGainLossTotal;
	}

	public void setSellGainLossTotal(BigDecimal sellGainLossTotal) {
		this.sellGainLossTotal = sellGainLossTotal;
	}

	public Long getSellDate() {
		return sellDate;
	}

	public void setSellDate(Long sellDate) {
		this.sellDate = sellDate;
	}

	public BigDecimal getSellCommisionPercentage() {
		return sellCommisionPercentage;
	}

	public void setSellCommisionPercentage(BigDecimal sellCommisionPercentage) {
		this.sellCommisionPercentage = sellCommisionPercentage;
	}

	public BigDecimal getSellTaxesPercentage() {
		return sellTaxesPercentage;
	}

	public void setSellTaxesPercentage(BigDecimal sellTaxesPercentage) {
		this.sellTaxesPercentage = sellTaxesPercentage;
	}

	public Integer getIdIssue() {
		return idIssue;
	}

	public void setIdIssue(Integer idIssue) {
		this.idIssue = idIssue;
	}
	
	public Integer getTypeCurrency() {
		return typeCurrency;
	}

	public void setTypeCurrency(Integer typeCurrency) {
		this.typeCurrency = typeCurrency;
	}

	public String getIssue() {
		return issue;
	}

	public void setIssue(String issue) {
		this.issue = issue;
	}

	public String getDescriptionTypeCurrency() {
		return descriptionTypeCurrency;
	}

	public void setDescriptionTypeCurrency(String descriptionTypeCurrency) {
		this.descriptionTypeCurrency = descriptionTypeCurrency;
	}

}
