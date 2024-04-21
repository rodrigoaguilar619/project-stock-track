package project.stock.track.app.beans.pojos.business.issues;

public class IssuesManagerFiltersPojo {

	private Integer idStatusIssue;
	
	private Integer idSector;
	
	private Integer idTypeStock;
	
	private Integer idStatusQuick;
	
	private Integer idStatusTrading;
	
	private Boolean isSp500;
	
	private Boolean isInvest;

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

	public Boolean getIsSp500() {
		return isSp500;
	}

	public void setIsSp500(Boolean isSp500) {
		this.isSp500 = isSp500;
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

	public Boolean getIsInvest() {
		return isInvest;
	}

	public void setIsInvest(Boolean isInvest) {
		this.isInvest = isInvest;
	}
}
