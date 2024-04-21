package project.stock.track.services.dollarprice;

import java.text.ParseException;

import project.stock.track.app.beans.rest.dollarprice.DollarPriceBean;

public interface DollarPriceService {

	public DollarPriceBean getDollarPrice() throws ParseException;
}
