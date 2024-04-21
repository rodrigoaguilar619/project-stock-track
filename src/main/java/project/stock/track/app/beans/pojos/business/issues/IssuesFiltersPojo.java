package project.stock.track.app.beans.pojos.business.issues;

public class IssuesFiltersPojo {

	private Integer idStatusIssue;
	
	private Integer idSector;
	
	private Integer idTypeStock;
	
	private Boolean isSp500;

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
}
