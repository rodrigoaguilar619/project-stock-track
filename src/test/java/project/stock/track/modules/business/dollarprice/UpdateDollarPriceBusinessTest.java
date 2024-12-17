package project.stock.track.modules.business.dollarprice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import lib.base.backend.persistance.GenericPersistence;
import project.stock.track.ProjectUnitTest;
import project.stock.track.app.beans.entity.DollarHistoricalPriceEntity;
import project.stock.track.app.beans.pojos.petition.data.UpdateDollarPriceDataPojo;
import project.stock.track.app.beans.rest.dollarprice.DollarPriceBean;
import project.stock.track.services.dollarprice.DollarPriceService;

@SuppressWarnings("unchecked")
class UpdateDollarPriceBusinessTest extends ProjectUnitTest {

	@InjectMocks
	private UpdateDollarPriceBusiness updateDollarPriceBusiness;
	
	@Mock
    private DollarPriceService dollarPriceService;

    @SuppressWarnings("rawtypes")
	@Mock
    private GenericPersistence genericPersistance;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

	@Test
    void testExecuteUpdateDollarPrice_NewEntry() throws ParseException {

        DollarPriceBean mockDollarPriceBean = new DollarPriceBean();
        mockDollarPriceBean.setDate(1661749200000L);
        mockDollarPriceBean.setPrice("20.50");
        
        DollarHistoricalPriceEntity dollarHistoricalPriceEntity = new DollarHistoricalPriceEntity();
        dollarHistoricalPriceEntity.setIdDate(LocalDate.of(2022, 8, 29));
        dollarHistoricalPriceEntity.setPrice(new BigDecimal("19.50"));

        when(dollarPriceService.getDollarPrice()).thenReturn(mockDollarPriceBean);
        when(genericPersistance.findById(DollarHistoricalPriceEntity.class, mockDollarPriceBean.getDate())).thenReturn(dollarHistoricalPriceEntity);
        
        UpdateDollarPriceDataPojo result = updateDollarPriceBusiness.executeUpdateDollarPrice();

        verify(genericPersistance, times(1)).save(any(DollarHistoricalPriceEntity.class));
        assertEquals(true, result.isNewRegister());
        assertEquals(1661749200000L, result.getDate());
        assertEquals(new BigDecimal("20.50"), result.getPrice());
    }

	@Test
    void testExecuteUpdateDollarPrice_ExistingEntry() throws ParseException {
        // Mock the dollar price service response
        DollarPriceBean mockDollarPriceBean = new DollarPriceBean();
        mockDollarPriceBean.setDate(1661749200000L);
        mockDollarPriceBean.setPrice("20.50");

        when(dollarPriceService.getDollarPrice()).thenReturn(mockDollarPriceBean);

        DollarHistoricalPriceEntity dollarHistoricalPriceEntity = new DollarHistoricalPriceEntity();
        dollarHistoricalPriceEntity.setIdDate(LocalDate.of(2022, 8, 29));
        dollarHistoricalPriceEntity.setPrice(new BigDecimal("19.50"));

        when(genericPersistance.findById(DollarHistoricalPriceEntity.class, LocalDate.of(2022, 8, 29))).thenReturn(dollarHistoricalPriceEntity);
        when(genericPersistance.update(any(DollarHistoricalPriceEntity.class))).thenReturn(1);
        UpdateDollarPriceDataPojo result = updateDollarPriceBusiness.executeUpdateDollarPrice();

        verify(genericPersistance, times(1)).update(any(DollarHistoricalPriceEntity.class));
        assertEquals(false, result.isNewRegister());
        assertEquals(1661749200000L, result.getDate());
        assertEquals(new BigDecimal("20.50"), result.getPrice());
    }

}
