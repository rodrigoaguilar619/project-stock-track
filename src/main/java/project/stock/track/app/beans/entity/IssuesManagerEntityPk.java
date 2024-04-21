package project.stock.track.app.beans.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the movements_issue_buy database table.
 * 
 */
@Embeddable
public class IssuesManagerEntityPk implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Column(name="id_issue")
	private int idIssue;

	@Column(name="id_user")
	private int idUser;

	public IssuesManagerEntityPk() {
		super();
	}

	public IssuesManagerEntityPk(int idIssue, int idUser) {
		super();
		this.idIssue = idIssue;
		this.idUser = idUser;
	}

	public int getIdIssue() {
		return idIssue;
	}

	public void setIdIssue(int idIssue) {
		this.idIssue = idIssue;
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idIssue;
		result = prime * result + idUser;
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
		IssuesManagerEntityPk other = (IssuesManagerEntityPk) obj;
		if (idIssue != other.idIssue)
			return false;
		return idUser == other.idUser;
	}
}