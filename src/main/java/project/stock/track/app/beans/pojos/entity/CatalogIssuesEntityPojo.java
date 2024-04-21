package project.stock.track.app.beans.pojos.entity;

public class CatalogIssuesEntityPojo {

	private Integer idIssue;
	
	private String description;
	
	private String initials;
	
	private Boolean isSp500;
	
	private Long historicalStartDate;
	
	private Integer idSector;
	
	private Integer idTypeStock;
	
	private Integer idStatusIssue;

	public Integer getIdIssue() {
		return idIssue;
	}

	public void setIdIssue(Integer idIssue) {
		this.idIssue = idIssue;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getInitials() {
		return initials;
	}

	public void setInitials(String initials) {
		this.initials = initials;
	}

	public Boolean getIsSp500() {
		return isSp500;
	}

	public void setIsSp500(Boolean isSp500) {
		this.isSp500 = isSp500;
	}

	public Long getHistoricalStartDate() {
		return historicalStartDate;
	}

	public void setHistoricalStartDate(Long historicalStartDate) {
		this.historicalStartDate = historicalStartDate;
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

	public Integer getIdStatusIssue() {
		return idStatusIssue;
	}

	public void setIdStatusIssue(Integer idStatusIssue) {
		this.idStatusIssue = idStatusIssue;
	}
}
