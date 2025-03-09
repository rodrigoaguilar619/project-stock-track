package project.stock.track.modules.business.dollarprice;

import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lib.base.backend.exception.data.BusinessException;
import lib.base.backend.persistance.GenericPersistence;
import lib.base.backend.pojo.rest.security.UserRequestPojo;
import lombok.RequiredArgsConstructor;
import project.stock.track.app.beans.entity.DollarHistoricalPriceEntity;
import project.stock.track.app.beans.pojos.petition.data.UpdateDollarPriceDataPojo;
import project.stock.track.app.beans.rest.dollarprice.DollarPriceBean;
import project.stock.track.app.repository.DollarHistoricalPriceRepositoryImpl;
import project.stock.track.app.utils.DateFinantialUtil;
import project.stock.track.config.helpers.ValidationRolHelper;
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
	private final ValidationRolHelper validationRolHelper;
	
	private DateFinantialUtil dateFinantialUtil = new DateFinantialUtil();

	@SuppressWarnings({ "unchecked" })
	@Transactional(rollbackFor = Exception.class)
	public UpdateDollarPriceDataPojo executeUpdateDollarPrice() throws ParseException {
		
		UpdateDollarPriceDataPojo updateDollarPriceResponsePojo = new UpdateDollarPriceDataPojo();
		
		DollarPriceBean dollarPriceBean = dollarPriceService.getDollarPrice();
		
		boolean flagDollarPriceExist = true;
		
		DollarHistoricalPriceEntity dollarHistoricalPriceEntity = (DollarHistoricalPriceEntity) genericPersistance.findById(DollarHistoricalPriceEntity.class, dateUtil.getLocalDate(dollarPriceBean.getDate()));
		
		if (dollarHistoricalPriceEntity == null) {
			
			dollarHistoricalPriceEntity = new DollarHistoricalPriceEntity();
			flagDollarPriceExist = false;
			updateDollarPriceResponsePojo.setNewRegister(true);
		}
		else {
			updateDollarPriceResponsePojo.setNewRegister(false);
		}
		
		dollarHistoricalPriceEntity.setIdDate(dateUtil.getLocalDate(dollarPriceBean.getDate()));
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
	public UpdateDollarPriceDataPojo executeUpdateDollarPriceHistorical(UserRequestPojo requestPojo) throws ParseException, BusinessException {
		
		validationRolHelper.validateOperationUserAsAdmin(requestPojo.getUserName());
		
		Boolean isNewRegister = true;
		LocalDate dateCurrent = dateFinantialUtil.getLastBusinessDay(LocalDateTime.now()).toLocalDate();
		LocalDate dateStart = null;
		LocalDate dateEnd = null;
		
		DollarHistoricalPriceEntity dollarHistoricalPriceEntity = dollarHistoricalPriceRepository.findLastRecord();
		
		if (dollarHistoricalPriceEntity == null) {
			dateStart = LocalDate.of(2020, 1, 1);
		}
		else
			dateStart = dollarHistoricalPriceEntity.getIdDate();
		
		if (dollarHistoricalPriceEntity != null) {
			dateStart = dateStart.plusDays(1);
		}
		
		dateEnd = dateStart.plusYears(1);	
		
		if(dateEnd.isAfter(dateCurrent))
			dateEnd = dateCurrent;
		
		if(dateStart.isBefore(dateEnd)) {
			List<DollarPriceBean> dollarPriceBeans = dollarPriceServiceCurrentLayer.getDollarPriceHistorical(dateStart, dateEnd);
			
			for (DollarPriceBean dollarPriceBean : dollarPriceBeans) {
				DollarHistoricalPriceEntity dollarHistoricalPriceEntityNew = new DollarHistoricalPriceEntity();
				dollarHistoricalPriceEntityNew.setIdDate(dateUtil.getLocalDate(dollarPriceBean.getDate()));
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
		dataPojo.setDate(dateUtil.getMillis(dollarHistoricalPriceEntity.getIdDate()));
		dataPojo.setPrice(dollarHistoricalPriceEntity.getPrice());
		
		return dataPojo;
	}
}
