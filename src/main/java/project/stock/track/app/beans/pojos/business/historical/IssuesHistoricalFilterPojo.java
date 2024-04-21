package project.stock.track.app.beans.pojos.business.historical;

public class IssuesHistoricalFilterPojo {

	private Integer idStatusIssue;
	
	private Integer idSector;
	
	private Integer idTypeStock;
	
	private Integer idStatusQuick;
	
	private Integer idStatusTrading;
	
	private Boolean isSp500;
	
	private Boolean isInvest;
	
	private Integer fairValuePriceOverPercentage;
	
	public Integer getIdStatusIssue() {
		return idStatusIssue;
	}

	public void setIdStatusIssue(Integer idStatusIssue) {
		this.idStatusIssue = idStatusIssue;
	}

	public Integer getIdSector() {
		return idSector;
	}

	public void setIdSector(Integer idSector) {
		this.idSector = idSector;
	}

	public Integer getIdTypeStock() {
		return idTypeStock;
	}

	public void setIdTypeStock(Integer idTypeStock) {
		this.idTypeStock = idTypeStock;
	}

	public Integer getIdStatusQuick() {
		return idStatusQuick;
	}

	public void setIdStatusQuick(Integer idStatusQuick) {
		this.idStatusQuick = idStatusQuick;
	}

	public Integer getIdStatusTrading() {
		return idStatusTrading;
	}

	public void setIdStatusTrading(Integer idStatusTrading) {
		this.idStatusTrading = idStatusTrading;
	}

	public Boolean getIsSp500() {
		return isSp500;
	}

	public void setIsSp500(Boolean isSp500) {
		this.isSp500 = isSp500;
	}

	public Boolean getIsInvest() {
		return isInvest;
	}

	public void setIsInvest(Boolean isInvest) {
		this.isInvest = isInvest;
	}

	public Integer getFairValuePriceOverPercentage() {
		return fairValuePriceOverPercentage;
	}

	public void setFairValuePriceOverPercentage(Integer fairValuePriceOverPercentage) {
		this.fairValuePriceOverPercentage = fairValuePriceOverPercentage;
	}
}
