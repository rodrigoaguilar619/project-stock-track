package project.stock.track.app.beans.pojos.petition.data;

import java.util.List;

import project.stock.track.app.beans.pojos.entity.CatalogIssuesEntityDesPojo;

public class GetIssuesListDataPojo {

	private List<CatalogIssuesEntityDesPojo> issues;

	public List<CatalogIssuesEntityDesPojo> getIssues() {
		return issues;
	}

	public void setIssues(List<CatalogIssuesEntityDesPojo> issues) {
		this.issues = issues;
	}
	
}
