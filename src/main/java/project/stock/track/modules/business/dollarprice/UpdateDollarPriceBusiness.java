package project.stock.track.modules.business.dollarprice;

import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lib.base.backend.persistance.GenericPersistence;
import lombok.RequiredArgsConstructor;
import project.stock.track.app.beans.entity.DollarHistoricalPriceEntity;
import project.stock.track.app.beans.pojos.petition.data.UpdateDollarPriceDataPojo;
import project.stock.track.app.beans.rest.dollarprice.DollarPriceBean;
import project.stock.track.app.repository.DollarHistoricalPriceRepositoryImpl;
import project.stock.track.app.utils.DateFinantialUtil;
import project.stock.track.modules.business.MainBusiness;
import project.stock.track.services.dollarprice.DollarPriceService;

@RequiredArgsConstructor
@Component
public class UpdateDollarPriceBusiness extends MainBusiness {
	
	@SuppressWarnings("rawtypes")
	private final GenericPersistence genericPersistance;
	private final DollarPriceService dollarPriceService;
	@Qualifier("dollarPriceServiceCurrentLayer") private final DollarPriceService dollarPriceServiceCurrentLayer;
	private final DollarHistoricalPriceRepositoryImpl dollarHistoricalPriceRepository;
	
	private DateFinantialUtil dateFinantialUtil = new DateFinantialUtil();

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
	
	@SuppressWarnings("unchecked")
	@Transactional(rollbackFor = Exception.class)
	public UpdateDollarPriceDataPojo executeUpdateDollarPriceHistorical() throws ParseException {
		
		Boolean isNewRegister = true;
		Date dateCurrent = dateFinantialUtil.getLastBusinessDay(new Date());
		Date dateStart = null;
		Date dateEnd = null;
		
		DollarHistoricalPriceEntity dollarHistoricalPriceEntity = dollarHistoricalPriceRepository.findLastRecord();
		
		if (dollarHistoricalPriceEntity == null) {
			
			LocalDate localDate = LocalDate.of(2020, 1, 1);
			dateStart = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		}
		else
			dateStart = dollarHistoricalPriceEntity.getIdDate();
		
		if (dollarHistoricalPriceEntity != null) {
			Calendar calendarStart = Calendar.getInstance();
			calendarStart.setTime(dateStart);
			calendarStart.add(Calendar.DAY_OF_MONTH, 1);
			dateStart = calendarStart.getTime();
		}
		
		Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.setTime(dateStart);
        calendarEnd.add(Calendar.YEAR, 1);
		dateEnd = calendarEnd.getTime();
		
		if(dateUtil.compareDatesNotTime(dateEnd, dateCurrent) > 0)
			dateEnd = dateCurrent;
		
		if(dateUtil.compareDatesNotTime(dateStart, dateEnd) < 0) {
			List<DollarPriceBean> dollarPriceBeans = dollarPriceServiceCurrentLayer.getDollarPriceHistorical(dateStart.getTime(), dateEnd.getTime());
			
			for (DollarPriceBean dollarPriceBean : dollarPriceBeans) {
				DollarHistoricalPriceEntity dollarHistoricalPriceEntityNew = new DollarHistoricalPriceEntity();
				dollarHistoricalPriceEntityNew.setIdDate(new Date(dollarPriceBean.getDate()));
				dollarHistoricalPriceEntityNew.setPrice(new BigDecimal(dollarPriceBean.getPrice()));
				
				genericPersistance.save(dollarHistoricalPriceEntityNew);
			}
			
			genericPersistance.flush();
		}
		else
			isNewRegister = false;
		
		dollarHistoricalPriceEntity = dollarHistoricalPriceRepository.findLastRecord();
		
		UpdateDollarPriceDataPojo dataPojo = new UpdateDollarPriceDataPojo();
		dataPojo.setNewRegister(isNewRegister);
		dataPojo.setDate(dollarHistoricalPriceEntity.getIdDate().getTime());
		dataPojo.setPrice(dollarHistoricalPriceEntity.getPrice());
		
		return dataPojo;
	}
}
