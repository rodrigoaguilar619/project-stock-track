package project.stock.track.modules.controller.issues;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import lib.base.backend.utils.RestUtil;
import project.stock.track.app.beans.pojos.petition.data.GetTempIssuesLastPriceDataPojo;
import project.stock.track.app.vo.catalogs.CatalogsUri;
import project.stock.track.modules.business.issues.IssuesLastPriceBusiness;

@RestController
public class IssuesLastPriceController {
	
	IssuesLastPriceBusiness issuesLastPriceBusiness;
	
	@Autowired
	public IssuesLastPriceController(IssuesLastPriceBusiness issuesLastPriceBusiness) {
		this.issuesLastPriceBusiness = issuesLastPriceBusiness;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping(path = CatalogsUri.API_ISSUES_LAST_PRICES_TEMP_GET, consumes = "application/json", produces = "application/json")
	public ResponseEntity getIssuesLastPrice() {
		
		GetTempIssuesLastPriceDataPojo dataPojo = issuesLastPriceBusiness.executeGetIssuesLastPrice();
		return new RestUtil().buildResponseSuccess(dataPojo, "Issues last price getted");
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping(path = CatalogsUri.API_ISSUES_LAST_PRICES_TEMP_UPDATE, consumes = "application/json", produces = "application/json")
	public ResponseEntity updateTempLastPrices() {
		
		issuesLastPriceBusiness.executeStoreIssuesLastPrice();
		return new RestUtil().buildResponseSuccess("Success", "Issues last prices updated");
	}
}
