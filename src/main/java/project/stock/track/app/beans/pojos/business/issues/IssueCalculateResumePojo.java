package project.stock.track.app.beans.pojos.business.issues;

import java.math.BigDecimal;

public class IssueCalculateResumePojo {

	private BigDecimal currentIssuePrice;
	
	private Long currentIssueDate;
	
	private BigDecimal currentDollarPrice;
	
	private Long currentDollarDate;
	
	private BigDecimal currentDollarPriceAfterDeprecate;
	
	private BigDecimal issueSellEstimate;
	
	private BigDecimal commisionSell;
	
	private BigDecimal taxesPercentage;
	
	private BigDecimal dollarPriceDeprecatePercentage;
	
	
	public BigDecimal getCurrentIssuePrice() {
		return currentIssuePrice;
	}

	public void setCurrentIssuePrice(BigDecimal currentIssuePrice) {
		this.currentIssuePrice = currentIssuePrice;
	}

	public BigDecimal getCurrentDollarPrice() {
		return currentDollarPrice;
	}

	public void setCurrentDollarPrice(BigDecimal currentDollarPrice) {
		this.currentDollarPrice = currentDollarPrice;
	}

	public Long getCurrentIssueDate() {
		return currentIssueDate;
	}

	public void setCurrentIssueDate(Long currentIssueDate) {
		this.currentIssueDate = currentIssueDate;
	}

	public Long getCurrentDollarDate() {
		return currentDollarDate;
	}

	public void setCurrentDollarDate(Long currentDollarDate) {
		this.currentDollarDate = currentDollarDate;
	}
	
	public BigDecimal getCommisionSell() {
		return commisionSell;
	}

	public void setCommisionSell(BigDecimal commisionSell) {
		this.commisionSell = commisionSell;
	}

	public BigDecimal getTaxesPercentage() {
		return taxesPercentage;
	}

	public void setTaxesPercentage(BigDecimal taxesPercentage) {
		this.taxesPercentage = taxesPercentage;
	}

	public BigDecimal getDollarPriceDeprecatePercentage() {
		return dollarPriceDeprecatePercentage;
	}

	public void setDollarPriceDeprecatePercentage(BigDecimal dollarPriceDeprecatePercentage) {
		this.dollarPriceDeprecatePercentage = dollarPriceDeprecatePercentage;
	}

	public BigDecimal getCurrentDollarPriceAfterDeprecate() {
		return currentDollarPriceAfterDeprecate;
	}

	public void setCurrentDollarPriceAfterDeprecate(BigDecimal currentDollarPriceAfterDeprecate) {
		this.currentDollarPriceAfterDeprecate = currentDollarPriceAfterDeprecate;
	}

	public BigDecimal getIssueSellEstimate() {
		return issueSellEstimate;
	}

	public void setIssueSellEstimate(BigDecimal issueSellEstimate) {
		this.issueSellEstimate = issueSellEstimate;
	}

}
