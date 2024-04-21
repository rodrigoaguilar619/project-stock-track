package project.stock.track.app.beans.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class IssuesHistoricalEntityId implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public IssuesHistoricalEntityId(Integer idIssue, Date idDate) {
		super();
		this.idIssue = idIssue;
		this.idDate = idDate;
	}

	public IssuesHistoricalEntityId() {
		super();
	}

	@Column(name = "id_issue")
	private Integer idIssue;
	
	@Column(name = "id_date")
	private Date idDate;

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idDate == null) ? 0 : idDate.hashCode());
		result = prime * result + ((idIssue == null) ? 0 : idIssue.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IssuesHistoricalEntityId other = (IssuesHistoricalEntityId) obj;
		if (idDate == null) {
			if (other.idDate != null)
				return false;
		} else if (!idDate.equals(other.idDate))
			return false;
		if (idIssue == null) {
			if (other.idIssue != null)
				return false;
		} else if (!idIssue.equals(other.idIssue))
			return false;
		return true;
	}
	
}
