package project.stock.track.modules.controller.dollarprice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import lib.base.backend.exception.data.BusinessException;
import lib.base.backend.pojo.rest.GenericResponsePojo;
import lib.base.backend.pojo.rest.security.UserRequestPojo;
import lib.base.backend.test.assessment.Assessment;
import project.stock.track.ProjectIntegrationTest;
import project.stock.track.app.beans.pojos.petition.data.UpdateDollarPriceDataPojo;
import project.stock.track.app.beans.rest.dollarprice.service.currentlayer.DollarPriceCurrentLayerBean;
import project.stock.track.app.beans.rest.dollarprice.service.currentlayer.QuoteBean;

@SuppressWarnings("unchecked")
class UpdateDollarPriceControllerTest extends ProjectIntegrationTest {
	
	@Autowired
	@InjectMocks
	UpdateDollarPriceController updateDollarPriceController;
	
	@MockBean
	RestTemplate restTemplate;

	@Test
	void testUpdateDollarPrice() throws ParseException, BusinessException {
		
		UserRequestPojo userRequestPojo = new UserRequestPojo();
		userRequestPojo.setUserName(userName);
		
		QuoteBean quoteBean = new QuoteBean();
		quoteBean.setMxn(new BigDecimal("17.0000"));
		
		Map<String, QuoteBean> quotes = new LinkedHashMap<String, QuoteBean>(); 
		quotes.put("2024-01-01", quoteBean);
		
		DollarPriceCurrentLayerBean dollarPriceCurrentLayerBean = new DollarPriceCurrentLayerBean();
		dollarPriceCurrentLayerBean.setQuotes(quotes);
		
		when(restTemplate.getForObject(anyString(), any(Class.class))).thenReturn(dollarPriceCurrentLayerBean);
		
		ResponseEntity<GenericResponsePojo<UpdateDollarPriceDataPojo>> response = updateDollarPriceController.updateDollarPrice(userRequestPojo);
		
		Assessment.assertResponseData(response);
		assertNotNull(response.getBody().getData().getPrice());
		assertEquals(dollarPriceCurrentLayerBean.getQuotes().get("2024-01-01").getMxn(), response.getBody().getData().getPrice());
	}

}
