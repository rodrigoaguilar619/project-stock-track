package project.stock.track.modules.controller.dollarprice;

import java.text.ParseException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import lib.base.backend.utils.RestUtil;
import project.stock.track.app.beans.pojos.petition.data.UpdateDollarPriceDataPojo;
import project.stock.track.app.vo.catalogs.CatalogsUri;
import project.stock.track.modules.business.dollarprice.UpdateDollarPriceBusiness;

@RestController
public class UpdateDollarPriceController {
	
	UpdateDollarPriceBusiness updateDollarPriceBusiness;
	
	@Autowired
	public UpdateDollarPriceController(UpdateDollarPriceBusiness updateDollarPriceBusiness) {
		this.updateDollarPriceBusiness = updateDollarPriceBusiness;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping(CatalogsUri.API_SERVICE_DOLLAR_UPDATE)
	public ResponseEntity updateDollarPrice(HttpServletResponse httpResponse) throws ParseException {
		
		UpdateDollarPriceDataPojo dataPojo = updateDollarPriceBusiness.executeUpdateDollarPrice();
		return new RestUtil().buildResponseSuccess(dataPojo, "Dollar price updated");
	}

}
