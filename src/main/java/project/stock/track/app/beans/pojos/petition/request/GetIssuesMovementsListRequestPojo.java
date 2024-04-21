package project.stock.track.app.beans.pojos.petition.request;

import lib.base.backend.pojo.datatable.DataTablePojo;
import lib.base.backend.pojo.rest.security.UserRequestPojo;
import project.stock.track.app.beans.pojos.business.issues.IssuesMovementsFiltersPojo;

public class GetIssuesMovementsListRequestPojo extends UserRequestPojo {
	
	DataTablePojo<IssuesMovementsFiltersPojo> dataTableConfig;

	public DataTablePojo<IssuesMovementsFiltersPojo> getDataTableConfig() {
		return dataTableConfig;
	}

	public void setDataTableConfig(DataTablePojo<IssuesMovementsFiltersPojo> dataTableConfig) {
		this.dataTableConfig = dataTableConfig;
	}
}
