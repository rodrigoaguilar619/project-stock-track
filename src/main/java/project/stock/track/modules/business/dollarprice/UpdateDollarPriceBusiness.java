package project.stock.track.modules.business.dollarprice;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lib.base.backend.persistance.GenericPersistence;
import lombok.RequiredArgsConstructor;
import project.stock.track.app.beans.entity.DollarHistoricalPriceEntity;
import project.stock.track.app.beans.pojos.petition.data.UpdateDollarPriceDataPojo;
import project.stock.track.app.beans.rest.dollarprice.DollarPriceBean;
import project.stock.track.modules.business.MainBusiness;
import project.stock.track.services.dollarprice.DollarPriceService;

@RequiredArgsConstructor
@Component
public class UpdateDollarPriceBusiness extends MainBusiness {
	
	@SuppressWarnings("rawtypes")
	private final GenericPersistence genericPersistance;
	private final DollarPriceService dollarPriceService;

	@SuppressWarnings({ "unchecked" })
	@Transactional(rollbackFor = Exception.class)
	public UpdateDollarPriceDataPojo executeUpdateDollarPrice() throws ParseException {
		
		UpdateDollarPriceDataPojo updateDollarPriceResponsePojo = new UpdateDollarPriceDataPojo();
		
		DollarPriceBean dollarPriceBean = dollarPriceService.getDollarPrice();
		
		boolean flagDollarPriceExist = true;
		
		DollarHistoricalPriceEntity dollarHistoricalPriceEntity = (DollarHistoricalPriceEntity) genericPersistance.findById(DollarHistoricalPriceEntity.class, new Date(dollarPriceBean.getDate()));
		
		if (dollarHistoricalPriceEntity == null) {
			
			dollarHistoricalPriceEntity = new DollarHistoricalPriceEntity();
			flagDollarPriceExist = false;
			updateDollarPriceResponsePojo.setNewRegister(true);
		}
		else {
			updateDollarPriceResponsePojo.setNewRegister(false);
		}
		
		dollarHistoricalPriceEntity.setIdDate(new Date(dollarPriceBean.getDate()));
		dollarHistoricalPriceEntity.setPrice(new BigDecimal(dollarPriceBean.getPrice()));
		
		if (!flagDollarPriceExist)
			genericPersistance.save(dollarHistoricalPriceEntity);
		else
			genericPersistance.update(dollarHistoricalPriceEntity);
		
		updateDollarPriceResponsePojo.setDate(dollarPriceBean.getDate());
		updateDollarPriceResponsePojo.setPrice(new BigDecimal(dollarPriceBean.getPrice()));
		
		return updateDollarPriceResponsePojo;
		
	}
}
