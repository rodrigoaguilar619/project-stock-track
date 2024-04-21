package project.stock.track.app.beans.pojos.petition.data;

import java.util.ArrayList;
import java.util.List;

import project.stock.track.app.beans.pojos.business.transaction.TransactionIssueTrackPojo;

public class GetTransactionIssuestrackDataPojo {

	List<TransactionIssueTrackPojo> transactionIssuesTrackList = new ArrayList<>();

	public List<TransactionIssueTrackPojo> getTransactionIssuesTrackList() {
		return transactionIssuesTrackList;
	}

	public void setTransactionIssuesTrackList(List<TransactionIssueTrackPojo> transactionIssuesTrackList) {
		this.transactionIssuesTrackList = transactionIssuesTrackList;
	}
}
