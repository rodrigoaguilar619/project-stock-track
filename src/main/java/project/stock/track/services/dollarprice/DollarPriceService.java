package project.stock.track.services.dollarprice;

import java.text.ParseException;
import java.util.List;

import project.stock.track.app.beans.rest.dollarprice.DollarPriceBean;

public interface DollarPriceService {

	public DollarPriceBean getDollarPrice() throws ParseException;
	
	public List<DollarPriceBean> getDollarPriceHistorical(long dateStart, long dateEnd) throws ParseException;
}
