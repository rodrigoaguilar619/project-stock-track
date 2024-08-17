package project.stock.track.app.beans.pojos.petition.request;

import lib.base.backend.pojo.rest.security.UserRequestPojo;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GetIssueRequestPojo extends UserRequestPojo {

	private Integer idIssue;
}
