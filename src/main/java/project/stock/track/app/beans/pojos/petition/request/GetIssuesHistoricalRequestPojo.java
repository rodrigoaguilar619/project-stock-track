package project.stock.track.app.beans.pojos.petition.request;

import lib.base.backend.pojo.datatable.DataTablePojo;
import lib.base.backend.pojo.rest.security.UserRequestPojo;
import project.stock.track.app.beans.pojos.business.historical.IssuesHistoricalFilterPojo;

public class GetIssuesHistoricalRequestPojo extends UserRequestPojo {

	DataTablePojo<IssuesHistoricalFilterPojo> dataTableConfig;
	
	Long totalRowsGlobal;

	public DataTablePojo<IssuesHistoricalFilterPojo> getDataTableConfig() {
		return dataTableConfig;
	}

	public void setDataTableConfig(DataTablePojo<IssuesHistoricalFilterPojo> dataTableConfig) {
		this.dataTableConfig = dataTableConfig;
	}

	public Long getTotalRowsGlobal() {
		return totalRowsGlobal;
	}

	public void setTotalRowsGlobal(Long totalRowsGlobal) {
		this.totalRowsGlobal = totalRowsGlobal;
	}

}
