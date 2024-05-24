package project.stock.track.app.beans.pojos.tuple;

import java.math.BigDecimal;

public class IssueTransactionResumeTuplePojo {

	private Integer idBroker;
	
	private String descriptionBroker;
	
	private Integer idTypeCurrency;
	
	private String descriptionTypeCurrency;
	
	private BigDecimal totalShares;
	
	private BigDecimal priceBuy;
	
	private BigDecimal sumPriceBuy;
	
	private Long buyDate;
	
	private Long sellDate;
	
	private BigDecimal priceSell;
	
	private BigDecimal sumPriceSell;
	
	private BigDecimal sellGainLossPercentage;
	
	private BigDecimal sellGainLossTotal;

	public Integer getIdBroker() {
		return idBroker;
	}

	public void setIdBroker(Integer idBroker) {
		this.idBroker = idBroker;
	}

	public String getDescriptionBroker() {
		return descriptionBroker;
	}

	public void setDescriptionBroker(String descriptionBroker) {
		this.descriptionBroker = descriptionBroker;
	}

	public Integer getIdTypeCurrency() {
		return idTypeCurrency;
	}

	public void setIdTypeCurrency(Integer idTypeCurrency) {
		this.idTypeCurrency = idTypeCurrency;
	}

	public String getDescriptionTypeCurrency() {
		return descriptionTypeCurrency;
	}

	public void setDescriptionTypeCurrency(String descriptionTypeCurrency) {
		this.descriptionTypeCurrency = descriptionTypeCurrency;
	}

	public BigDecimal getTotalShares() {
		return totalShares;
	}

	public void setTotalShares(BigDecimal totalShares) {
		this.totalShares = totalShares;
	}

	public BigDecimal getPriceBuy() {
		return priceBuy;
	}

	public void setPriceBuy(BigDecimal priceBuy) {
		this.priceBuy = priceBuy;
	}

	public BigDecimal getSumPriceBuy() {
		return sumPriceBuy;
	}

	public void setSumPriceBuy(BigDecimal sumPriceBuy) {
		this.sumPriceBuy = sumPriceBuy;
	}

	public Long getBuyDate() {
		return buyDate;
	}

	public void setBuyDate(Long buyDate) {
		this.buyDate = buyDate;
	}

	public Long getSellDate() {
		return sellDate;
	}

	public void setSellDate(Long sellDate) {
		this.sellDate = sellDate;
	}

	public BigDecimal getPriceSell() {
		return priceSell;
	}

	public void setPriceSell(BigDecimal priceSell) {
		this.priceSell = priceSell;
	}

	public BigDecimal getSumPriceSell() {
		return sumPriceSell;
	}

	public void setSumPriceSell(BigDecimal sumPriceSell) {
		this.sumPriceSell = sumPriceSell;
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
	
	
}
