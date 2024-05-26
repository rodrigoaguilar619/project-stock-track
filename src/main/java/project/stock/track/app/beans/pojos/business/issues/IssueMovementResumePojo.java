package project.stock.track.app.beans.pojos.business.issues;

import java.math.BigDecimal;
import java.util.List;

import project.stock.track.app.beans.pojos.entity.IssueMovementBuyEntityPojo;

public class IssueMovementResumePojo {

	private Integer idIssueMovement;
	
	private Integer idIssue;
	
	private String issue;
	
	private Integer idStatus;
	
	private Integer idBroker;
	
	private Integer idSector;
	
	private String descriptionBroker;
	
	private String descriptionCurrency;
	
	private String descriptionStatus;
	
	private String descriptionSector;
	
	private BigDecimal currentPrice;
	
	private Long currentPriceDate;
	
	private String issuePerformance;
	
	private String alert;
	
	private String fairValue;
	
	private BigDecimal priceMovement;
	
	private List<IssueMovementBuyEntityPojo> issueMovementBuysList;
	
	private IssueMovementTransactionPojo issueMovementTransactionNotSold;
	
	private IssueMovementTransactionPojo issueMovementTransactionSold;

	public Integer getIdIssueMovement() {
		return idIssueMovement;
	}

	public void setIdIssueMovement(Integer idIssueMovement) {
		this.idIssueMovement = idIssueMovement;
	}

	public Integer getIdIssue() {
		return idIssue;
	}

	public void setIdIssue(Integer idIssue) {
		this.idIssue = idIssue;
	}

	public Integer getIdStatus() {
		return idStatus;
	}

	public void setIdStatus(Integer idStatus) {
		this.idStatus = idStatus;
	}

	public BigDecimal getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(BigDecimal currentPrice) {
		this.currentPrice = currentPrice;
	}

	public Long getCurrentPriceDate() {
		return currentPriceDate;
	}

	public void setCurrentPriceDate(Long currentPriceDate) {
		this.currentPriceDate = currentPriceDate;
	}

	public String getIssuePerformance() {
		return issuePerformance;
	}

	public void setIssuePerformance(String issuePerformance) {
		this.issuePerformance = issuePerformance;
	}

	public String getAlert() {
		return alert;
	}

	public void setAlert(String alert) {
		this.alert = alert;
	}

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

	public String getDescriptionCurrency() {
		return descriptionCurrency;
	}

	public void setDescriptionCurrency(String descriptionCurrency) {
		this.descriptionCurrency = descriptionCurrency;
	}

	public String getDescriptionStatus() {
		return descriptionStatus;
	}

	public void setDescriptionStatus(String descriptionStatus) {
		this.descriptionStatus = descriptionStatus;
	}

	public Integer getIdSector() {
		return idSector;
	}

	public void setIdSector(Integer idSector) {
		this.idSector = idSector;
	}

	public String getDescriptionSector() {
		return descriptionSector;
	}

	public void setDescriptionSector(String descriptionSector) {
		this.descriptionSector = descriptionSector;
	}

	public String getFairValue() {
		return fairValue;
	}

	public void setFairValue(String fairValue) {
		this.fairValue = fairValue;
	}

	public BigDecimal getPriceMovement() {
		return priceMovement;
	}

	public void setPriceMovement(BigDecimal priceMovement) {
		this.priceMovement = priceMovement;
	}

	public String getIssue() {
		return issue;
	}

	public void setIssue(String issue) {
		this.issue = issue;
	}

	public List<IssueMovementBuyEntityPojo> getIssueMovementBuysList() {
		return issueMovementBuysList;
	}

	public void setIssueMovementBuysList(List<IssueMovementBuyEntityPojo> issueMovementBuysList) {
		this.issueMovementBuysList = issueMovementBuysList;
	}

	public IssueMovementTransactionPojo getIssueMovementTransactionNotSold() {
		return issueMovementTransactionNotSold;
	}

	public void setIssueMovementTransactionNotSold(IssueMovementTransactionPojo issueMovementTransactionNotSold) {
		this.issueMovementTransactionNotSold = issueMovementTransactionNotSold;
	}

	public IssueMovementTransactionPojo getIssueMovementTransactionSold() {
		return issueMovementTransactionSold;
	}

	public void setIssueMovementTransactionSold(IssueMovementTransactionPojo issueMovementTransactionSold) {
		this.issueMovementTransactionSold = issueMovementTransactionSold;
	}
	
}
