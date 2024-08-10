package project.stock.track.app.beans.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@EqualsAndHashCode
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
}