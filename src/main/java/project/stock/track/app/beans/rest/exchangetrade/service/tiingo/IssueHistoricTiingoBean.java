package project.stock.track.app.beans.rest.exchangetrade.service.tiingo;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class IssueHistoricTiingoBean {

	private String name;
	
	private String uri;
	
	@JsonFormat(shape = JsonFormat.Shape.ARRAY)
	private List<ShareHistoryDayTiingoBean> history = new ArrayList<>();
}
