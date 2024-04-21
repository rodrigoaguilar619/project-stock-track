package project.stock.track.app.beans.pojos.business.issues;

public class IssuesElementMultiplePojo {

	private String description;
	
	private String initials;
	
	private Boolean isSp500;
	
	private Integer idSector;

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

	public Integer getIdSector() {
		return idSector;
	}

	public void setIdSector(Integer idSector) {
		this.idSector = idSector;
	}
}
