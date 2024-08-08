package project.stock.track.modules.controller.dollarprice;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import lib.base.backend.pojo.rest.GenericResponsePojo;
import lib.base.backend.test.assessment.Assessment;
import project.stock.track.ProjectIntegrationTest;
import project.stock.track.app.beans.pojos.petition.data.UpdateDollarPriceDataPojo;
import project.stock.track.app.beans.rest.dollarprice.service.bancomexico.BmxBean;
import project.stock.track.app.beans.rest.dollarprice.service.bancomexico.DatosBean;
import project.stock.track.app.beans.rest.dollarprice.service.bancomexico.DollarPriceBancoMexicoBean;
import project.stock.track.app.beans.rest.dollarprice.service.bancomexico.SerieBean;

@SuppressWarnings("unchecked")
class UpdateDollarPriceControllerTest extends ProjectIntegrationTest {
	
	@Autowired
	@InjectMocks
	UpdateDollarPriceController updateDollarPriceController;
	
	@MockBean
	RestTemplate restTemplate;

	@Test
	void testUpdateDollarPrice() throws ParseException {
		
		DatosBean datosBean = new DatosBean();
		datosBean.setDato("12.90");
		datosBean.setFecha("01/01/2024");
		
		SerieBean serieBean = new SerieBean();
		serieBean.setDatos(Arrays.asList(datosBean));
		
		BmxBean bmxBean = new BmxBean();
		bmxBean.setSeries(Arrays.asList(serieBean));
		
		DollarPriceBancoMexicoBean dollarPriceBancoMexicoBean = new DollarPriceBancoMexicoBean();
		dollarPriceBancoMexicoBean.setBmx(bmxBean);
		
		when(restTemplate.getForObject(anyString(), any(Class.class))).thenReturn(dollarPriceBancoMexicoBean);
		
		ResponseEntity<GenericResponsePojo<UpdateDollarPriceDataPojo>> response = updateDollarPriceController.updateDollarPrice();
		
		Assessment.assertResponseData(response);
		assertNotNull(response.getBody().getData().getPrice());
		assertEquals(new BigDecimal(datosBean.getDato()), response.getBody().getData().getPrice());
	}

}
