package project.stock.track.app.beans.pojos.business.transaction;

import java.math.BigDecimal;
import java.util.Date;

public class TransactionIssueNotSoldPojo {

	private Integer idIssue;
	
	private String issue;
	
	private Date idDate;
	
	private Integer idBroker;
	
	private BigDecimal priceTotalBuy;
	
	private BigDecimal priceBuy;
	
	private BigDecimal commisionPercentage;
	
	private Long totalTitles;

	public Integer getIdIssue() {
		return idIssue;
	}

	public void setIdIssue(Integer idIssue) {
		this.idIssue = idIssue;
	}

	public Date getIdDate() {
		return idDate;
	}

	public void setIdDate(Date idDate) {
		this.idDate = idDate;
	}

	public Integer getIdBroker() {
		return idBroker;
	}

	public void setIdBroker(Integer idBroker) {
		this.idBroker = idBroker;
	}

	public BigDecimal getPriceTotalBuy() {
		return priceTotalBuy;
	}

	public void setPriceTotalBuy(BigDecimal priceTotalBuy) {
		this.priceTotalBuy = priceTotalBuy;
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

	public Long getTotalTitles() {
		return totalTitles;
	}

	public void setTotalTitles(Long totalTitles) {
		this.totalTitles = totalTitles;
	}

	public String getIssue() {
		return issue;
	}

	public void setIssue(String issue) {
		this.issue = issue;
	}
	
}
