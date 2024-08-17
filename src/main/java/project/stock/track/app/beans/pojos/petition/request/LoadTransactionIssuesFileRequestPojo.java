package project.stock.track.app.beans.pojos.petition.request;

import org.springframework.web.multipart.MultipartFile;

import lib.base.backend.pojo.rest.security.UserRequestPojo;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoadTransactionIssuesFileRequestPojo extends UserRequestPojo {

	private MultipartFile fileTransactionIssues;
	
	private Integer idBroker;
}
