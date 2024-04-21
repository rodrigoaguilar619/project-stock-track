package project.stock.track.app.utils;

import java.util.Calendar;
import java.util.Date;

public class DateFinantialUtil {

	public boolean isWeekend(Date date) {
		
		Calendar c1 = Calendar.getInstance();
	    c1.setTime(date);
	    
	    return c1.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || c1.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
	}
	
	public Date getLastBusinessDay(Date date) {
		
		Calendar c1 = Calendar.getInstance();
	    c1.setTime(date);
	    
	    while(c1.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || c1.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
	    	c1.add(Calendar.DATE, -1);
	    }
	    
	    return c1.getTime();
	}
}
