package project.stock.track.services.dollarprice;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

import project.stock.track.app.beans.rest.dollarprice.DollarPriceBean;

public interface DollarPriceService {

	public DollarPriceBean getDollarPrice() throws ParseException;
	
	public List<DollarPriceBean> getDollarPriceHistorical(LocalDate dateStart, LocalDate dateEnd) throws ParseException;
}
