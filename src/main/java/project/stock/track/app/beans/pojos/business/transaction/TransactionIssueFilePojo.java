package project.stock.track.app.beans.pojos.business.transaction;

import java.math.BigDecimal;

public class TransactionIssueFilePojo {
	
	private String issue;
	
	private Long date;
	
	private String typeTransaction;
	
	private double titles;
	
	private BigDecimal price;
	
	private BigDecimal comissionPercentage;
	
	private BigDecimal taxesPercentage;
	
	private Integer typeCurrency;
	
	private int broker;

	public String getIssue() {
		return issue;
	}

	public void setIssue(String issue) {
		this.issue = issue;
	}

	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
	}

	public String getTypeTransaction() {
		return typeTransaction;
	}

	public void setTypeTransaction(String typeTransaction) {
		this.typeTransaction = typeTransaction;
	}

	public double getTitles() {
		return titles;
	}

	public void setTitles(double titles) {
		this.titles = titles;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getComissionPercentage() {
		return comissionPercentage;
	}

	public void setComissionPercentage(BigDecimal comissionPercentage) {
		this.comissionPercentage = comissionPercentage;
	}

	public BigDecimal getTaxesPercentage() {
		return taxesPercentage;
	}

	public void setTaxesPercentage(BigDecimal taxesPercentage) {
		this.taxesPercentage = taxesPercentage;
	}

	public Integer getTypeCurrency() {
		return typeCurrency;
	}

	public void setTypeCurrency(Integer typeCurrency) {
		this.typeCurrency = typeCurrency;
	}

	public int getBroker() {
		return broker;
	}

	public void setBroker(int broker) {
		this.broker = broker;
	}
	
}
