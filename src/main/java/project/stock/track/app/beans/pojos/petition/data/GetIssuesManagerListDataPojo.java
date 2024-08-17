package project.stock.track.app.beans.pojos.petition.data;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import project.stock.track.app.beans.pojos.business.issues.IssueManagerResumePojo;

@Getter @Setter
public class GetIssuesManagerListDataPojo {

	List<IssueManagerResumePojo> issuesManagerList = new ArrayList<>();
}
