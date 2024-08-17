package project.stock.track.app.beans.pojos.petition.data;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import project.stock.track.app.beans.pojos.entity.CatalogIssuesEntityDesPojo;

@Getter @Setter
public class GetIssuesListDataPojo {

	private List<CatalogIssuesEntityDesPojo> issues = new ArrayList<>();
}
