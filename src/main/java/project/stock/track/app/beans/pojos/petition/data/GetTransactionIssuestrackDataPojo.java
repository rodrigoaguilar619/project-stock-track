package project.stock.track.app.beans.pojos.petition.data;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import project.stock.track.app.beans.pojos.business.transaction.TransactionIssueTrackPojo;

@Getter @Setter
public class GetTransactionIssuestrackDataPojo {

	private List<TransactionIssueTrackPojo> transactionIssuesTrackList = new ArrayList<>();
}
