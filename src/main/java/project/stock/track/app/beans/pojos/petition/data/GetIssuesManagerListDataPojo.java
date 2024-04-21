package project.stock.track.app.beans.pojos.petition.data;

import java.util.ArrayList;
import java.util.List;

import project.stock.track.app.beans.pojos.business.issues.IssueManagerResumePojo;

public class GetIssuesManagerListDataPojo {

	List<IssueManagerResumePojo> issuesManagerList = new ArrayList<>();
	
	
	public List<IssueManagerResumePojo> getIssuesManagerList() {
		return issuesManagerList;
	}
	
	public void setIssuesManager(List<IssueManagerResumePojo> issuesManagerList) {
		this.issuesManagerList = issuesManagerList;
	}
	
}
