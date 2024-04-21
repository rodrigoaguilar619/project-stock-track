package project.stock.track.app.beans.pojos.petition.request;

import org.springframework.web.multipart.MultipartFile;

import lib.base.backend.pojo.rest.security.UserRequestPojo;

public class LoadTransactionIssuesFileRequestPojo extends UserRequestPojo {

	MultipartFile fileTransactionIssues;
	
	Integer idBroker;

	public MultipartFile getFileTransactionIssues() {
		return fileTransactionIssues;
	}

	public void setFileTransactionIssues(MultipartFile fileTransactionIssues) {
		this.fileTransactionIssues = fileTransactionIssues;
	}

	public Integer getIdBroker() {
		return idBroker;
	}

	public void setIdBroker(Integer idBroker) {
		this.idBroker = idBroker;
	}
}
