package project.stock.track.modules.controller.dollarprice;

import java.text.ParseException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import lib.base.backend.exception.data.BusinessException;
import lib.base.backend.pojo.rest.security.UserRequestPojo;
import lib.base.backend.utils.RestUtil;
import project.stock.track.app.beans.pojos.petition.data.UpdateDollarPriceDataPojo;
import project.stock.track.app.vo.catalogs.CatalogsUri;
import project.stock.track.modules.business.dollarprice.UpdateDollarPriceBusiness;

@RestController
public class UpdateDollarPriceController {
	
	UpdateDollarPriceBusiness updateDollarPriceBusiness;
	
	public UpdateDollarPriceController(UpdateDollarPriceBusiness updateDollarPriceBusiness) {
		this.updateDollarPriceBusiness = updateDollarPriceBusiness;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping(CatalogsUri.API_SERVICE_DOLLAR_UPDATE)
	public ResponseEntity updateDollarPrice(UserRequestPojo requestPojo) throws ParseException, BusinessException {
		
		UpdateDollarPriceDataPojo dataPojo = updateDollarPriceBusiness.executeUpdateDollarPriceHistorical(requestPojo);
		return new RestUtil().buildResponseSuccess(dataPojo, "Dollar price historical updated");
	}

}
