package project.stock.track.services.exchangetrade;

import java.util.Map;

import lib.base.backend.exception.data.BusinessException;
import project.stock.track.app.beans.pojos.services.IssueHistoricQueryPojo;
import project.stock.track.app.beans.pojos.services.IssuesLastPriceQueryPojo;
import project.stock.track.app.beans.rest.exchangetrade.service.IssueHistoricMainBean;
import project.stock.track.app.beans.rest.exchangetrade.service.IssueIexMainBean;

public interface IssueTrackService {

	public IssueHistoricMainBean getIssueHistoric(String token, IssueHistoricQueryPojo issueHistoricQueryPojo) throws BusinessException;
	
	public IssueHistoricMainBean getCryptoHistoric(String token, IssueHistoricQueryPojo issueHistoricQueryPojo );
	
	public Map<String, IssueIexMainBean> getIssuesLastPrice(String token, IssuesLastPriceQueryPojo issuesLastPriceQueryPojo );
}
