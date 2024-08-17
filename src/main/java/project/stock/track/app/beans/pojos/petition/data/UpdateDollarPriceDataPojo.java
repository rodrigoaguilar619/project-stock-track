package project.stock.track.app.beans.pojos.petition.data;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateDollarPriceDataPojo {

	private Long date;
	
	private BigDecimal price;
	
	private boolean isNewRegister;
}
