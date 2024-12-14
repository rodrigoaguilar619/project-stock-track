package project.stock.track.app.utils;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class DateFinantialUtil {

	public boolean isWeekend(LocalDateTime dateTime) {
	    DayOfWeek dayOfWeek = dateTime.getDayOfWeek();
	    return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
	}
	
	public LocalDateTime getLastBusinessDay(LocalDateTime dateTime) {
	    while (dateTime.getDayOfWeek() == DayOfWeek.SATURDAY || dateTime.getDayOfWeek() == DayOfWeek.SUNDAY) {
	        dateTime = dateTime.minusDays(1);
	    }
	    return dateTime;
	}
}
