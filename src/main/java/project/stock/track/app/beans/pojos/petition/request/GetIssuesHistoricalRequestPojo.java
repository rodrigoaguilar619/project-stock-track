package project.stock.track.app.beans.pojos.petition.request;

import lib.base.backend.pojo.datatable.DataTablePojo;
import lib.base.backend.pojo.rest.security.UserRequestPojo;
import lombok.Getter;
import lombok.Setter;
import project.stock.track.app.beans.pojos.business.historical.IssuesHistoricalFilterPojo;

@Getter @Setter
public class GetIssuesHistoricalRequestPojo extends UserRequestPojo {

	private DataTablePojo<IssuesHistoricalFilterPojo> dataTableConfig;
	
	private Long totalRowsGlobal;
}
