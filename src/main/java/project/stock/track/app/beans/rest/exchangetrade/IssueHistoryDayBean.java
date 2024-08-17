package project.stock.track.app.beans.rest.exchangetrade;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class IssueHistoryDayBean {

	private String open;
	
	private String close;
	
	private String high;
	
	private String low;
	
	private String volume;
}
