package project.stock.track.app.beans.pojos.business.historical;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class IssuesHistoricalProgressPojo {

	private String issue;
	private int currentProgress;
    private int totalProgress;
    
}
