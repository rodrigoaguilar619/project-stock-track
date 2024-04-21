package project.stock.track.app.beans.pojos.business.issues;

import java.math.BigDecimal;

public class IssueManagerTrackResumePojo {

	private Integer idIssue;

	private Integer idStatusIssueQuick;
	
	private Integer idStatusIssueTrading;
	
	private Boolean isInvest;
	
	private BigDecimal trackBuyPrice;
	
	private BigDecimal trackSellPrice;
	
	private BigDecimal fairValue;
	
	public Integer getIdIssue() {
		return idIssue;
	}

	public void setIdIssue(Integer idIssue) {
		this.idIssue = idIssue;
	}

	public Integer getIdStatusIssueQuick() {
		return idStatusIssueQuick;
	}
	
	public void setIdStatusIssueQuick(Integer idStatusIssueQuick) {
		this.idStatusIssueQuick = idStatusIssueQuick;
	}
	
	public Integer getIdStatusIssueTrading() {
		return idStatusIssueTrading;
	}
	
	public void setIdStatusIssueTrading(Integer idStatusIssueTrading) {
		this.idStatusIssueTrading = idStatusIssueTrading;
	}
	
	public Boolean getIsInvest() {
		return isInvest;
	}
	
	public void setIsInvest(Boolean isInvest) {
		this.isInvest = isInvest;
	}
	
	public BigDecimal getTrackBuyPrice() {
		return trackBuyPrice;
	}
	
	public void setTrackBuyPrice(BigDecimal trackBuyPrice) {
		this.trackBuyPrice = trackBuyPrice;
	}
	
	public BigDecimal getTrackSellPrice() {
		return trackSellPrice;
	}
	
	public void setTrackSellPrice(BigDecimal trackSellPrice) {
		this.trackSellPrice = trackSellPrice;
	}
	
	public BigDecimal getFairValue() {
		return fairValue;
	}
	
	public void setFairValue(BigDecimal fairValue) {
		this.fairValue = fairValue;
	}
}
