package project.stock.track.app.beans.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@EqualsAndHashCode
@Embeddable
public class IssuesHistoricalEntityId implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public IssuesHistoricalEntityId(Integer idIssue, LocalDateTime idDate) {
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
	private LocalDateTime idDate;
	
}
